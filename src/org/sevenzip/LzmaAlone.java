package org.sevenzip;

public class LzmaAlone {
	static public class CommandLine {
		public static final int kEncode = 0;
		public static final int kDecode = 1;
		public static final int kBenchmak = 2;
		
		public int Command = -1;
		public int NumBenchmarkPasses = 10;
		
		public int dictionarySize = 1 << 23;
		public boolean dictionarySizeIsDefined = false;
		
		public int lc = 3;
		public int lp = 0;
		public int pb = 2;
		
		public int fb = 128;
		public boolean fbIsDefined = false;
		
		public boolean eos = false;
		
		public int algorithm = 2;
		public int MatchFinder = 1;
		
		public String InFile;
		public String OutFile;
		
		boolean parseSwitch(String s) {
			if (s.startsWith("d")) {
				dictionarySize = 1 << Integer.parseInt(s.substring(1));
				dictionarySizeIsDefined = true;
			}
			else if (s.startsWith("fb")) {
				fb = Integer.parseInt(s.substring(2));
				fbIsDefined = true;
			}
			else if (s.startsWith("a"))
				algorithm = Integer.parseInt(s.substring(1));
			else if (s.startsWith("lc"))
				lc = Integer.parseInt(s.substring(2));
			else if (s.startsWith("lp"))
				lp = Integer.parseInt(s.substring(2));
			else if (s.startsWith("pb"))
				pb = Integer.parseInt(s.substring(2));
			else if (s.startsWith("eos"))
				eos = true;
			else if (s.startsWith("mf")) {
				String mfs = s.substring(2);
				switch (mfs) {
					case "bt2":
						MatchFinder = 0;
						break;
					case "bt4":
						MatchFinder = 1;
						break;
					case "bt4b":
						MatchFinder = 2;
						break;
					default:
						return false;
				}
			}
			else
				return false;
			return true;
		}
		
		public boolean parse(String[] args) {
			int pos = 0;
			boolean switchMode = true;
			for (String arg : args) {
				if (arg.length() == 0) return false;

				if (switchMode) {
					if (arg.compareTo("--") == 0) {
						switchMode = false;
						continue;
					}
					if (arg.charAt(0) == '-') {
						String sw = arg.substring(1).toLowerCase();
						if (sw.length() == 0)
							return false;
						try {
							if (!parseSwitch(sw))
								return false;
						} catch (NumberFormatException e) {
							return false;
						}
						continue;
					}
				}

				if (pos == 0) {
					if (arg.equalsIgnoreCase("e"))
						Command = kEncode;
					else if (arg.equalsIgnoreCase("d"))
						Command = kDecode;
					else if (arg.equalsIgnoreCase("b"))
						Command = kBenchmak;
					else
						return false;
				} else if (pos == 1) {
					if (Command == kBenchmak) {
						try {
							NumBenchmarkPasses = Integer.parseInt(arg);
							if (NumBenchmarkPasses < 1)
								return false;
						} catch (NumberFormatException e) {
							return false;
						}
					} else
						InFile = arg;
				} else if (pos == 2)
					OutFile = arg;
				else
					return false;
				pos++;
			}

			return true;
		}
	}
	
	
	static void printHelp() {
		System.out.println(
				"\nUsage:  LZMA <e|d> [<switches>...] inputFile outputFile\n" +
				"  e: encode file\n" +
				"  d: decode file\n" +
				"  b: Benchmark\n" +
				"<Switches>\n" +
				// "  -a{N}:  set compression mode - [0, 1], default: 1 (max)\n" +
				"  -d{N}:  set dictionary - [0,28], default: 23 (8MB)\n" +
				"  -fb{N}: set number of fast bytes - [5, 273], default: 128\n" +
				"  -lc{N}: set number of literal context bits - [0, 8], default: 3\n" +
				"  -lp{N}: set number of literal pos bits - [0, 4], default: 0\n" +
				"  -pb{N}: set number of pos bits - [0, 4], default: 2\n" +
				"  -mf{MF_ID}: set Match Finder: [bt2, bt4], default: bt4\n" +
				"  -eos:   write End Of Stream marker\n"
				);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("\nLZMA (Java) 4.61  2008-11-23\n");
		
		if (args.length < 1) {
			printHelp();
			return;
		}
		
		CommandLine params = new CommandLine();
		if (!params.parse(args)) {
			System.out.println("\nIncorrect command");
			return;
		}
		
		if (params.Command == CommandLine.kBenchmak) {
			int dictionary = (1 << 21);
			if (params.dictionarySizeIsDefined)
				dictionary = params.dictionarySize;
			if (params.MatchFinder > 1)
				throw new Exception("Unsupported match finder");
			LzmaBench.LzmaBenchmark(params.NumBenchmarkPasses, dictionary);
		}
		else if (params.Command == CommandLine.kEncode || params.Command == CommandLine.kDecode) {
			java.io.File inFile = new java.io.File(params.InFile);
			java.io.File outFile = new java.io.File(params.OutFile);
			
			java.io.BufferedInputStream inStream  = new java.io.BufferedInputStream(new java.io.FileInputStream(inFile));
			java.io.BufferedOutputStream outStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(outFile));
			
			boolean eos = params.eos;
            if (params.Command == CommandLine.kEncode) {
				org.sevenzip.compression.LZMA.Encoder encoder = new org.sevenzip.compression.LZMA.Encoder();
				if (!encoder.setAlgorithm(params.algorithm))
					throw new Exception("Incorrect compression mode");
				if (encoder.setDictionarySize(params.dictionarySize))
					throw new Exception("Incorrect dictionary size");
				if (!encoder.setNumFastBytes(params.fb))
					throw new Exception("Incorrect -fb value");
				if (!encoder.setMatchFinder(params.MatchFinder))
					throw new Exception("Incorrect -mf value");
				if (!encoder.setLcLpPb(params.lc, params.lp, params.pb))
					throw new Exception("Incorrect -lc or -lp or -pb value");
				encoder.setEndMarkerMode(eos);
				encoder.writeCoderProperties(outStream);
				long fileSize;
				if (eos)
					fileSize = -1;
				else
					fileSize = inFile.length();
				for (int i = 0; i < 8; i++)
					outStream.write((int)(fileSize >>> (8 * i)) & 0xFF);
				encoder.code(inStream, outStream, null);
			}
			else {
				int propertiesSize = 5;
				byte[] properties = new byte[propertiesSize];
				if (inStream.read(properties, 0, propertiesSize) != propertiesSize)
					throw new Exception("input .lzma file is too short");
				org.sevenzip.compression.LZMA.Decoder decoder = new org.sevenzip.compression.LZMA.Decoder();
				if (!decoder.setDecoderProperties(properties))
					throw new Exception("Incorrect stream properties");
				long outSize = 0;
				for (int i = 0; i < 8; i++) {
					int v = inStream.read();
					if (v < 0)
						throw new Exception("Can't read stream size");
					outSize |= ((long)v) << (8 * i);
				}
				if (!decoder.code(inStream, outStream, outSize))
					throw new Exception("Error in data stream");
			}
			outStream.flush();
			outStream.close();
			inStream.close();
		}
		else
			throw new Exception("Incorrect command");
	}
}
