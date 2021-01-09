using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using SevenZip.Compression.LZMA;
using SevenZip;

namespace sc_editor
{
    public static class LzmaCompression {
        private static readonly CoderPropID[] PropertyIDs = {
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
            encoder.SetCoderProperties(PropertyIDs, properties);

            var input = new MemoryStream(decompressed);
            var output = new MemoryStream();

            // Writing header
            encoder.WriteCoderProperties(output);
            output.Write(BitConverter.GetBytes(input.Length), 0, 4);
            
            encoder.Code(input, output, input.Length, -1, null);
            var compressed = output.ToArray();
            output.Close();
            
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
            Sc,
            None
        }

        private static Signature GetSignature(byte[] buffer)
        {
            if (buffer.Take(2).ToArray().SequenceEqual(new byte[] {83, 67}))
            {
                if (buffer.Length >= 30 && buffer.Skip(26).Take(4).SequenceEqual(new byte[] {83, 67, 76, 90}))
                    return Signature.Lzham;
                
                return Signature.Sc;
            } 
            else if (buffer.Take(4).ToArray().SequenceEqual(new byte[] {83, 105, 103, 58}))
                return Signature.Sig;
            else if (buffer.Skip(1).Take(4).ToArray().SequenceEqual(new byte[] {0, 0, 4, 0}))
                return Signature.Lzma;
            return Signature.None;
        }

        private static byte[] Decompress(byte[] compressed)
        {
            byte[] decompressed;

            var signature = GetSignature(compressed);
            
            switch (signature)
            {
                case Signature.Lzham:
                    throw new NotImplementedException();
                case Signature.Lzma:
                    decompressed = LzmaCompression.Decompress(compressed).ToArray();
                    break;
                case Signature.Sc:
                    // Skip of MAGIC, unk int32, unk int32 and MD5
                    compressed = compressed.Skip(2).Skip(4).Skip(4).Skip(16).ToArray();
                    decompressed = LzmaCompression.Decompress(compressed).ToArray();
                    break;
                case Signature.Sig:
                    // Skip of MAGIC and SHA64
                    compressed = compressed.Skip(4).Skip(64).ToArray();
                    decompressed = LzmaCompression.Decompress(compressed).ToArray();
                    break;
                case Signature.None:
                    decompressed = compressed;
                    break;
                default:
                    throw new ArgumentOutOfRangeException();
            }
            
            return decompressed;
        }

        public static byte[] Decompress(string inputPath) {
            var input = new FileStream(inputPath, FileMode.Open);
            var compressed = new byte[input.Length];
            input.Read(compressed, 0, (int) input.Length);
            input.Close();

            return Decompress(compressed);
        }
        
        
        #region Decompress method, then can save decompressed data to file
        
        // public static void Decompress(string inputPath, string outputPath) {
            // var decompressed = Decompress(inputPath);
            // var output = new FileStream(outputPath, FileMode.Create);
            // output.Write(decompressed, 0, decompressed.Length);
            // output.Flush();
            // output.Close();
        // }
        
        #endregion

        private static byte[] Compress(byte[] buffer, Signature signature) {
            byte[] lzmaCompressed;
            byte[] compressed;

            switch (signature)
            {
                case Signature.Lzham:
                    throw new NotImplementedException();
                case Signature.Lzma:
                    compressed = LzmaCompression.Compress(buffer).ToArray();
                    break;
                case Signature.Sig:
                    lzmaCompressed = LzmaCompression.Compress(buffer).ToArray();
                    var sha64 = new byte[64];
                    
                    compressed = new byte[] {83, 105, 103, 58};  // File Magic
                    compressed = compressed.Concat(sha64).ToArray();  // SHA64
                    compressed = compressed.Concat(lzmaCompressed).ToArray();  // Compressed data
                    break;
                case Signature.Sc:
                    var md5 = MD5.Create();
                    
                    lzmaCompressed = LzmaCompression.Compress(buffer).ToArray();
                    var dataHash = md5.ComputeHash(lzmaCompressed);

                    compressed = new byte[] {83, 67};  // File Magic
                    compressed = compressed.Concat(BitConverter.GetBytes(1).Reverse()).ToArray();
                    compressed = compressed.Concat(BitConverter.GetBytes(16).Reverse()).ToArray();
                    compressed = compressed.Concat(dataHash).ToArray();  // md5 hash
                    compressed = compressed.Concat(lzmaCompressed).ToArray();  // Compressed data
                    break;
                case Signature.None:
                    compressed = buffer;
                    break;
                default:
                    throw new ArgumentOutOfRangeException(nameof(signature), signature, null);
            }

            return compressed;
        }
        
        #region Decompress method, then can save decompressed data to file
        
        public static void Compress(byte[] buffer, string outputPath, Signature signature) {
            var compressed = Compress(buffer, signature);
            var output = new FileStream(outputPath, FileMode.Create);
            output.Write(compressed, 0, compressed.Length);
            output.Flush();
            output.Close();
        }
        
        #endregion
    }
}