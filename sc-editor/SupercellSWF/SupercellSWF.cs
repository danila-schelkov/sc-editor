using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing.Drawing2D;
using System.IO;
using System.Linq;
using OpenTK;

namespace sc_editor.SupercellSWF
{
    public class SupercellSWF
    {
        private string FileName { get; set; }
        
        private List<SWFObject> _shapes;
        private List<SWFObject> _movieClips;
        private List<SWFObject> _textures;
        private List<SWFObject> _textFields;
        private List<Matrix> _matrices;
        private List<ColorTransform> _colorTransforms;
        
        private List<SWFObject> _exports;
        
        public SupercellSWF()
        {
            _shapes = new List<SWFObject>();
            _movieClips = new List<SWFObject>();
            _textures = new List<SWFObject>();
            _textFields = new List<SWFObject>();
            _matrices = new List<Matrix>();
            _colorTransforms = new List<ColorTransform>();
            
            _exports = new List<SWFObject>();
        }

        private MovieClip SetOriginalMovieClip(ushort id, string name)
        {
            MovieClip result;
            
            var matches = _movieClips.Where(movieClip => movieClip.GetId() == id).ToArray();

            if (matches.Length < 1)
            {
                var debugError = $"Unable to find some MovieClip id from {FileName}";
                if (name != string.Empty)
                    debugError += $" needed by export name {name}";
                Debug.WriteLine(debugError);
                result = null;
            }
            else
            {
                result = (MovieClip) matches[0];
            }

            return result;
        }

        public void Load(string filePath)
        {
            FileName = Path.GetFileName(filePath);
            var directory = Path.GetDirectoryName(filePath);
            var fileName = Path.GetFileNameWithoutExtension(filePath);

            if (fileName != null && fileName.EndsWith("_tex"))
            {
                LoadInternal(filePath, true);
                return;
            }
            
            var texturePath = $"{directory}\\{fileName}_tex.sc";
            LoadInternal(filePath, false);
            LoadInternal(texturePath, true);
        }

        private void LoadInternal(string filePath, bool isTexture)
        {
            var decompressed = Compression.Decompress(filePath);
            var br = new BinaryReader(new MemoryStream(decompressed));

            if (!isTexture)
            {
                var shapesCount = br.ReadShort();
                var movieClipsCount = br.ReadShort();
                var texturesCount = br.ReadShort();
                var textFieldsCount = br.ReadShort();
                var matricesCount = br.ReadShort();
                var colorTransformsCount = br.ReadShort();

                // unused bytes
                br.ReadSByte();
                br.ReadShort();
                br.ReadShort();
                
                var exportsCount = br.ReadShort();
                
                for (var i = 0; i < exportsCount; i++)
                {
                    var export = new Export(this);
                    export.SetId(br.ReadShort());
                    _exports.Add(export);
                }
                
                for (var i = 0; i < exportsCount; i++)
                {
                    ((Export) _exports[i]).SetName(br.ReadAscii());
                }
            }

            _textures = new List<SWFObject>();
            LoadTags(br);

            if (isTexture) return;

            for (var exportIndex = 0; exportIndex < _exports.Count; exportIndex++)
            {
                var export = _exports[exportIndex];
                
                var id = export.GetId();
                var name = export.GetName();
                
                var movieClip = SetOriginalMovieClip(id, name);
                movieClip?.SetName(name);

                _exports[exportIndex] = movieClip;
            }
        }
        
        private void LoadTags(BinaryReader br)
        {
            var hasTexture = true;
            while (true)
            {
                var dataTag = br.ReadUnsignedChar();
                var dataLength = br.ReadInt32();

                switch (dataTag)
                {
                    case 0:
                        return;
                    case 1:
                    case 16:
                    case 19: // Maybe load сaсhed textures
                    case 24:
                        var texture = new SWFTexture(this);
                        texture.Load(br, hasTexture);
                        _textures.Add(texture);
                        continue;
                    case 2:
                    case 18:
                        var shape = new Shape(this);
                        shape.Load(br, dataTag);
                        _shapes.Add(shape);
                        continue;
                    case 3:
                    case 10:
                    case 12:
                    case 14:
                        var movieClip = new MovieClip(this);
                        movieClip.Load(br, dataTag);
                        _movieClips.Add(movieClip);
                        continue;
                    case 7:
                    case 15:
                    case 20:
                    case 21:
                    case 25:
                    case 33:
                    case 44:
                        var textField = new TextField(this);
                        textField.Load(br, dataTag);
                        _textFields.Add(textField);
                        continue;
                    case 8:
                        _matrices.Add(br.ReadMatrix2x3());
                        continue;
                    // case 9:
                    //     br.ReadColorTransform();
                    //     continue;
                    case 13:
                        var unkCount = br.ReadInt32();
                        br.ReadShortArray(unkCount);
                        continue;
                    case 23:
                        continue;
                    case 26:
                        hasTexture = false;
                        continue;
                    default:
                        if (dataLength >= 1)
                        {
                            for (var i = 0; i < dataLength; i++)
                                br.ReadUnsignedChar();
                        }
                        continue;
                }
            }
        }

        public List<SWFObject> GetShapes()
        {
            return _shapes;
        }

        public List<SWFObject> GetMovieClips()
        {
            return _movieClips;
        }

        public List<SWFObject> GetTextFields()
        {
            return _textFields;
        }

        public List<SWFObject> GetExports()
        {
            return _exports;
        }

        public List<SWFObject> GetTextures()
        {
            return _textures;
        }

        public SWFTexture GetTexture(int textureIndex)
        {
            return (SWFTexture) _textures[textureIndex];
        }
        
        public Matrix GetMatrix(int matrixIndex)
        {
            return _matrices[matrixIndex];
        }
        
        public ColorTransform GetColorTransform(int colorTransformIndex)
        {
            return _colorTransforms[colorTransformIndex];
        }
        
        public string GetFileName()
        {
            return FileName;
        }
    }
}