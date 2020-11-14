using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using SevenZip.Compression.LZMA;
using SevenZip;

namespace sc_editor
{
    public static class LzmaCompression {
        private static CoderPropID[] propertyIDs = {
            CoderPropID.Algorithm,
            CoderPropID.DictionarySize,
            CoderPropID.LitContextBits,
            CoderPropID.LitPosBits,
            CoderPropID.PosStateBits
        };

        public static IEnumerable<byte> Compress(byte[] decompressed)
        {
            object[] properties = {
                0,
                256 * 1024,
                3,
                0,
                2
            };

            var encoder = new Encoder();
            encoder.SetCoderProperties(propertyIDs, properties);

            var input = new MemoryStream(decompressed);
            var output = new MemoryStream();

            // Writing header
            encoder.WriteCoderProperties(output);
            output.Write(BitConverter.GetBytes(input.Length), 0, 4);
            
            encoder.Code(input, output, input.Length, -1, null);
            var compressed = output.ToArray();
            
            return compressed;
        }
        
        public static IEnumerable<byte> Decompress(byte[] compressed) {
            var decoder = new Decoder();

            var input = new MemoryStream(compressed);
            var output = new MemoryStream();
            
            // Read the decoder properties
            var properties = new byte[5];
            input.Read(properties, 0, 5);
            
            // Read in the decompress file size.
            var fileLengthBytes = new byte[4];
            input.Read(fileLengthBytes, 0, 4);
            var fileLength = BitConverter.ToInt32(fileLengthBytes, 0);
            
            decoder.SetDecoderProperties(properties);
            decoder.Code(input, output, input.Length, fileLength, null);

            input.Close();
            output.Flush();
            output.Close();
            
            var decompressed = output.ToArray();
            
            return decompressed;
        }
    }

    public static class Compression {
        public enum Signature
        {
            Lzham,
            Lzma,
            Sig,
            None
        }

        private static Signature GetSignature(byte[] buffer)
        {
            // if (false)
            // {
            //     if (true)
            //     {
            //         return Signature.Lzham;
            //     }
            //
            //     return Signature.Lzma;
            // }
            //
            // if (true)
            // {
            //     return Signature.Sig;
            // }
            // return Signature.None;
            return Signature.Lzma;
        }

        public static byte[] Decompress(string inputPath) {
            byte[] decompressed = null;

            var input = new FileStream(inputPath, FileMode.Open);
            var compressed = new byte[input.Length];
            input.Read(compressed, 0, (int) input.Length);
            input.Close();

            var signature = GetSignature(compressed);
            if (signature == Signature.Lzma)
                decompressed = LzmaCompression.Decompress(compressed).ToArray();
            
            return decompressed;
        }
        
        public static void Decompress(string inputPath, string outputPath) {
            var decompressed = Decompress(inputPath);
            var output = new FileStream(outputPath, FileMode.Create);
            output.Write(decompressed, 0, decompressed.Length);
            // output.Flush();
            output.Close();
        }

        public static byte[] Compress(byte[] buffer, Signature signature) {
            byte[] compressed = null;
            
            if (signature == Signature.Lzma)
                compressed = LzmaCompression.Compress(buffer).ToArray();

            return compressed;
        }

        public static void Compress(byte[] buffer, string outputPath, Signature signature) {
            var compressed = Compress(buffer, signature);
            var output = new FileStream(outputPath, FileMode.Create);
            output.Write(compressed, 0, compressed.Length);
            output.Close();
        }
    }
}