using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Linq;

namespace sc_editor.SupercellSWF
{
    public class Bind
    {
        public ushort Id { get; set; }
        public byte Blend { get; set; }
        public string Name { get; set; }

        public Bind() {}
    }

    public class MovieClipFrame : SWFObject
    {
        public MovieClipFrame(SupercellSWF swf) : base(swf) { }

        public override void Load(BinaryReader br, short tag)
        {
            Id = br.ReadShort();
            Name = br.ReadAscii();
        }
    }
    
    public class MovieClip : SWFObject
    {
        private byte _fps;
        private ushort _framesCount;

        private List<ushort[]> _transforms;

        private List<SWFObject> _movieClipFrames;
        private List<SWFObject> _children;
        private List<Bind> _binds;
        
        public MovieClip(SupercellSWF swf) : base(swf)
        {
            _movieClipFrames = new List<SWFObject>();
            _transforms = new List<ushort[]>();
            _binds = new List<Bind>();

            SWF = swf;
        }
        
        public override void Load(BinaryReader br, short tag)
        {
            Id = br.ReadShort();
            _fps = br.ReadUnsignedChar();
            _framesCount = br.ReadShort();

            switch (tag)
            {
                case 14:
                    // SupercellSWF::getTimelineOffset
                    Debug.WriteLine("TAG_MOVIE_CLIP_4 no longer supported");
                    break;
                case 3:
                    Debug.WriteLine("TAG_MOVIE_CLIP no longer supported");
                    break;
                default:
                    var transformsCount = br.ReadInt32();

                    for (var i = 0; i < transformsCount; i++)
                    {
                        _transforms.Add(br.ReadShortArray(3));
                    }
                    break;
            }

            var bindsCount = br.ReadShort();

            var idArray = br.ReadShortArray(bindsCount);
            for (var i = 0; i < bindsCount; i++)
            {
                var bind = new Bind();
                bind.Id = idArray[i];
                _binds.Add(bind);
            }
            if (tag == 12)
            {
                var blendArray = br.ReadByteArray(bindsCount);
                for (var i = 0; i < bindsCount; i++)
                {
                    _binds[i].Blend = blendArray[i];
                }
            }
            else
            {
                for (var i = 0; i < bindsCount; i++)
                {
                    _binds[i].Blend = 0;
                }
            }
            
            for (var i = 0; i < bindsCount; i++)
            {
                _binds[i].Name = br.ReadAscii();
            }

            while (true)
            {
                byte movieClipFrameTag;
                int movieClipFrameLength;
                
                while (true)
                {
                    movieClipFrameTag = br.ReadUnsignedChar();
                    movieClipFrameLength = br.ReadInt32();
                    if (movieClipFrameLength < 0)
                    {
                        Debug.WriteLine($"Negative tag length in MovieClip. Tag {movieClipFrameTag}");
                    }

                    if (movieClipFrameTag != 11)
                        break;

                    var movieClipFrame = new MovieClipFrame(SWF);
                    movieClipFrame.Load(br, movieClipFrameTag);

                    _movieClipFrames.Add(movieClipFrame);
                }

                if (movieClipFrameTag == 0)
                    break;
                if (movieClipFrameTag == 5)
                {
                    Debug.WriteLine("TAG_MOVIE_CLIP_FRAME no longer supported");
                }
                else
                {
                    Debug.WriteLine($"Unknown tag in MovieClip, {movieClipFrameTag}");
                }

                br.ReadChars(movieClipFrameLength);
            }
        }

        public override List<SWFObject> GetChildren()
        {
            if (_children != null) return _children;
            _children = new List<SWFObject>();
            
            foreach (var bind in _binds)
            {
                SWFObject bindObject = null;

                var shape = SWF.GetShapes().FindLast(
                    swfObject => swfObject.GetId() == bind.Id
                );
                var movieClip = SWF.GetMovieClips().FindLast(
                    swfObject => swfObject.GetId() == bind.Id
                );
                var textField = SWF.GetTextFields().FindLast(
                    swfObject => swfObject.GetId() == bind.Id
                );

                if (!(shape is null))
                    bindObject = shape;
                else if (!(movieClip is null))
                    bindObject = movieClip;
                // else if (!(textField is null))
                //     bindObject = textField;

                if (bindObject is null) continue;

                bindObject.SetName(bind.Name);

                _children.Add(bindObject);
            }

            return _children;
        }

        public override string GetDataTypeName()
        {
            return "Movieclip";
        }
    }
}