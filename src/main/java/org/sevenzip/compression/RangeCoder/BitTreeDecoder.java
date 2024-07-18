package org.sevenzip.compression.RangeCoder;

public class BitTreeDecoder {
	short[] models;
	int numBitLevels;
	
	public BitTreeDecoder(int numBitLevels) {
		this.numBitLevels = numBitLevels;
		models = new short[1 << numBitLevels];
	}
	
	public void init() {
		Decoder.initBitModels(models);
	}
	
	public int decode(Decoder rangeDecoder) throws java.io.IOException {
		int m = 1;
		for (int bitIndex = numBitLevels; bitIndex != 0; bitIndex--)
			m = (m << 1) + rangeDecoder.decodeBit(models, m);
		return m - (1 << numBitLevels);
	}
	
	public int reverseDecode(Decoder rangeDecoder) throws java.io.IOException {
		int m = 1;
		int symbol = 0;
		for (int bitIndex = 0; bitIndex < numBitLevels; bitIndex++) {
			int bit = rangeDecoder.decodeBit(models, m);
			m <<= 1;
			m += bit;
			symbol |= (bit << bitIndex);
		}
		return symbol;
	}
	
	public static int reverseDecode(short[] Models, int startIndex,
									Decoder rangeDecoder, int NumBitLevels) throws java.io.IOException {
		int m = 1;
		int symbol = 0;
		for (int bitIndex = 0; bitIndex < NumBitLevels; bitIndex++) {
			int bit = rangeDecoder.decodeBit(Models, startIndex + m);
			m <<= 1;
			m += bit;
			symbol |= (bit << bitIndex);
		}
		return symbol;
	}
}
