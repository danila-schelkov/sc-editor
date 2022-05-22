package org.sevenzip.compression.RangeCoder;
import java.io.IOException;
import java.util.Arrays;

public class Encoder {
	private static final int kTopMask = -(1 << 24);

	private static final int kNumBitModelTotalBits = 11;
	private static final int kBitModelTotal = (1 << kNumBitModelTotalBits);
	private static final int kNumMoveBits = 5;

	private java.io.OutputStream stream;

	private long low;
	private int range;
	private int _cacheSize;
	private int _cache;

	private long _position;
	
	public void setStream(java.io.OutputStream stream) {
		this.stream = stream;
	}
	
	public void ReleaseStream() {
		stream = null;
	}
	
	public void init() {
		_position = 0;
		low = 0;
		range = -1;
		_cacheSize = 1;
		_cache = 0;
	}
	
	public void flushData() throws IOException {
		for (int i = 0; i < 5; i++)
			shiftLow();
	}
	
	public void flushStream() throws IOException {
		stream.flush();
	}
	
	public void shiftLow() throws IOException {
		int LowHi = (int)(low >>> 32);
		if (LowHi != 0 || low < 0xFF000000L) {
			_position += _cacheSize;
			int temp = _cache;
			do {
				stream.write(temp + LowHi);
				temp = 0xFF;
			}
			while(--_cacheSize != 0);
			_cache = (((int) low) >>> 24);
		}
		_cacheSize++;
		low = (low & 0xFFFFFF) << 8;
	}
	
	public void encodeDirectBits(int v, int numTotalBits) throws IOException {
		for (int i = numTotalBits - 1; i >= 0; i--) {
			range >>>= 1;
			if (((v >>> i) & 1) == 1)
				low += range;
			if ((range & Encoder.kTopMask) == 0) {
				range <<= 8;
				shiftLow();
			}
		}
	}
	
	
	public long getProcessedSizeAdd() {
		return _cacheSize + _position + 4;
	}
	
	
	
	static final int kNumMoveReducingBits = 2;
	public static final int kNumBitPriceShiftBits = 6;
	
	public static void initBitModels(short []probs) {
		Arrays.fill(probs, (short) (kBitModelTotal >>> 1));
	}
	
	public void encode(short []probs, int index, int symbol) throws IOException {
		int prob = probs[index];
		int newBound = (range >>> kNumBitModelTotalBits) * prob;
		if (symbol == 0) {
			range = newBound;
			probs[index] = (short)(prob + ((kBitModelTotal - prob) >>> kNumMoveBits));
		}
		else {
			low += (newBound & 0xFFFFFFFFL);
			range -= newBound;
			probs[index] = (short)(prob - ((prob) >>> kNumMoveBits));
		}
		if ((range & kTopMask) == 0) {
			range <<= 8;
			shiftLow();
		}
	}
	
	private static final int[] PROB_PRICES = new int[kBitModelTotal >>> kNumMoveReducingBits];
	
	static {
		int kNumBits = (kNumBitModelTotalBits - kNumMoveReducingBits);
		for (int i = kNumBits - 1; i >= 0; i--) {
			int start = 1 << (kNumBits - i - 1);
			int end = 1 << (kNumBits - i);
			for (int j = start; j < end; j++)
				PROB_PRICES[j] = (i << kNumBitPriceShiftBits) +
						(((end - j) << kNumBitPriceShiftBits) >>> (kNumBits - i - 1));
		}
	}
	
	static public int getPrice(int prob, int symbol) {
		return PROB_PRICES[(((prob - symbol) ^ ((-symbol))) & (kBitModelTotal - 1)) >>> kNumMoveReducingBits];
	}
	static public int getPrice0(int Prob) { 
		return PROB_PRICES[Prob >>> kNumMoveReducingBits]; 
	}
	static public int getPrice1(int prob) { 
		return PROB_PRICES[(kBitModelTotal - prob) >>> kNumMoveReducingBits]; 
	}
}
