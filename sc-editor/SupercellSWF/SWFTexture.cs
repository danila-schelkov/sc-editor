using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.IO;

namespace sc_editor.SupercellSWF
{
    public class SWFTexture : SWFObject
    {
        public Size Size;
        
        private byte _pixelType;
        private Bitmap _bitmap;

        private const int _chunkSize = 32;

        public SWFTexture(SupercellSWF swf) : base(swf)
        {
            Id = (ushort) SWF.GetTextures().Count;
        }

        public void Load(BinaryReader br, byte tag, bool hasTexture)
        {
            _pixelType = br.ReadUnsignedChar();
            Debug.WriteLine($"Pixel Type: {_pixelType}");
            Size = new Size
            {
                Width = br.ReadShort(), 
                Height = br.ReadShort()
            };
            
            if (!hasTexture) return;
            LoadTexture(br, tag);
        }

        private void LoadTexture(BinaryReader br, byte tag)
        {
            _bitmap = new Bitmap(Size.Width, Size.Height, PixelFormat.Format32bppArgb);
            int alpha;
            int red;
            int green;
            int blue;
            
            switch (_pixelType)
            {
                // SWFTexture::loadTexture<unsigned char>
                case 0:
                    for (var y = 0; y < Size.Height; y++)
                    {
                        for (var x = 0; x < Size.Width; x++)
                        {
                            red = br.ReadUnsignedChar();
                            green = br.ReadUnsignedChar();
                            blue = br.ReadUnsignedChar();
                            alpha = br.ReadUnsignedChar();
                            
                            _bitmap.SetPixel(x, y, Color.FromArgb((alpha << 24) | (red << 16) | (green << 8) | blue));
                        }
                    }
                    break;
                // SWFTexture::loadTexture<short> 
                case 2:
                    for (var y = 0; y < Size.Height; y++)
                    {
                        for (var x = 0; x < Size.Width; x++)
                        {
                            var pixel = br.ReadShort();
                            red = ((pixel >> 12) & 0xF) << 4;
                            green = ((pixel >> 8) & 0xF) << 4;
                            blue = ((pixel >> 4) & 0xF) << 4;
                            alpha = ((pixel) & 0xF) << 4;
                            
                            _bitmap.SetPixel(x, y, Color.FromArgb(alpha, red, green, blue));
                        }
                    }
                    break;
                case 3:
                    for (var y = 0; y < Size.Height; y++)
                    {
                        for (var x = 0; x < Size.Width; x++)
                        {
                            var pixel = br.ReadShort();
                            red = ((pixel >> 11) & 0x1F) << 3;
                            green = ((pixel >> 6) & 0x1F) << 3;
                            blue = ((pixel >> 1) & 0x1F) << 3;
                            alpha = (pixel & 0xFF) << 7;
                            
                            _bitmap.SetPixel(x, y, Color.FromArgb(red, green, blue, alpha));
                        }
                    }
                    break;
                case 4:
                    for (var y = 0; y < Size.Height; y++)
                    {
                        for (var x = 0; x < Size.Width; x++)
                        {
                            var pixel = br.ReadShort();
                            red = ((pixel >> 11) & 0x1F) << 3;
                            green = ((pixel >> 5) & 0x3F) << 2;
                            blue = (pixel & 0x1F) << 3;
                            
                            _bitmap.SetPixel(x, y, Color.FromArgb(red, green, blue));
                        }
                    }
                    break;
                case 6:
                    for (var y = 0; y < Size.Height; y++)
                    {
                        for (var x = 0; x < Size.Width; x++)
                        {
                            alpha = br.ReadUnsignedChar();
                            var pixel = br.ReadUnsignedChar();
                            
                            _bitmap.SetPixel(x, y, Color.FromArgb(pixel, pixel, pixel, alpha));
                        }
                    }
                    break;
                case 10:
                    for (var y = 0; y < Size.Height; y++)
                    {
                        for (var x = 0; x < Size.Width; x++)
                        {
                            var pixel = br.ReadUnsignedChar();
                            
                            _bitmap.SetPixel(x, y, Color.FromArgb(pixel, pixel, pixel));
                        }
                    }
                    break;
                default:
                    Debug.WriteLine($"Unknown pixel type: {_pixelType}");
                    break;
            }

            if (tag != 28) return;
            
            var originalBitmap = (Bitmap) _bitmap.Clone();
            var pixelIndex = 0;

            var chunkYCount = Size.Height / _chunkSize;
            var chunkXCount = Size.Width / _chunkSize;
            var chunkYRest = Size.Height % _chunkSize;
            var chunkXRest = Size.Width % _chunkSize;

            for (var chunkY = 0; chunkY < chunkYCount; chunkY++)
            {
                for (var chunkX = 0; chunkX < chunkXCount; chunkX++)
                for (var y = 0; y < _chunkSize; y++)
                for (var x = 0; x < _chunkSize; x++)
                {
                    _bitmap.SetPixel(
                        x + chunkX * _chunkSize, 
                        y + chunkY * _chunkSize, 
                        originalBitmap.GetPixel(pixelIndex % Size.Width, pixelIndex / Size.Width)
                    );
                    pixelIndex++;
                }
                
                for (var y = 0; y < _chunkSize; y++)
                for (var restX = 0; restX < chunkXRest; restX++)
                {
                    _bitmap.SetPixel(
                        restX + Size.Width - chunkXRest,
                        y + chunkY * _chunkSize,
                        originalBitmap.GetPixel(pixelIndex % Size.Width, pixelIndex / Size.Width)
                    );
                    pixelIndex++;
                }
            }

            for (var chunkX = 0; chunkX < chunkXCount; chunkX++)
            for (var restY = 0; restY < chunkYRest; restY++)
            for (var x = 0; x < _chunkSize; x++)
            {
                _bitmap.SetPixel(
                    x + chunkX * _chunkSize,
                    restY + Size.Height - chunkYRest,
                    originalBitmap.GetPixel(pixelIndex % Size.Width, pixelIndex / Size.Width)
                );
                pixelIndex++;
            }
            
            for (var restY = 0; restY < chunkYRest; restY++)
            for (var restX = 0; restX < chunkXRest; restX++)
            {
                _bitmap.SetPixel(
                    restX + Size.Width - chunkXRest,
                    restY + Size.Height - chunkYRest,
                    originalBitmap.GetPixel(pixelIndex % Size.Width, pixelIndex / Size.Width)
                );
                pixelIndex++;
            }
        }

        // public void Save()
        // {
        //     var pixels = new List<Color>();
        //
        //     var chunkYCount = Size.Height / _chunkSize;
        //     var chunkXCount = Size.Width / _chunkSize;
        //     var chunkYRest = Size.Height % _chunkSize;
        //     var chunkXRest = Size.Width % _chunkSize;
        //
        //     for (var chunkY = 0; chunkY < chunkYCount; chunkY++)
        //     {
        //         for (var chunkX = 0; chunkX < chunkXCount; chunkX++)
        //         for (var y = 0; y < _chunkSize; y++)
        //         for (var x = 0; x < _chunkSize; x++) 
        //             pixels.Add(_bitmap.GetPixel(x + chunkX * _chunkSize, y + chunkY * _chunkSize));
        //         
        //         for (var y = 0; y < _chunkSize; y++)
        //         for (var restX = 0; restX < chunkXRest; restX++)
        //             pixels.Add(_bitmap.GetPixel(restX + Size.Width - chunkXRest, y + chunkY * _chunkSize));
        //     }
        //
        //     for (var chunkX = 0; chunkX < chunkXCount; chunkX++)
        //     for (var restY = 0; restY < chunkYRest; restY++)
        //     for (var x = 0; x < _chunkSize; x++) 
        //         pixels.Add(_bitmap.GetPixel(x + chunkX * _chunkSize, restY + Size.Height - chunkYRest));
        //     
        //     for (var restY = 0; restY < chunkYRest; restY++)
        //     for (var restX = 0; restX < chunkXRest; restX++)
        //         pixels.Add(_bitmap.GetPixel(restX + Size.Width - chunkXRest, restY + Size.Height - chunkYRest));
        // }

        public override Bitmap Render(Matrix matrix = null)
        {
            return _bitmap;
        }

        public override sbyte GetDataType()
        {
            return 1;
        }

        public override string GetDataTypeName()
        {
            return "Texture";
        }
    }
}