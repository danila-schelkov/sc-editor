using System.IO;

namespace sc_editor.SupercellSWF
{
    public class TextField : SWFObject
    {
        private string _fontName;

        public TextField(SupercellSWF swf) : base(swf) { }

        public override void Load(BinaryReader br, short tag)
        {
            Id = br.ReadShort();
            _fontName = br.ReadAscii();
            br.ReadInt32();
            
            br.ReadBoolean();  // if then *((_BYTE *)v3 + 29) |= 4u;
            br.ReadBoolean();  // if then *((_BYTE *)v3 + 29) |= 8u;
            br.ReadBoolean();  // if then *((_BYTE *)v3 + 29) |= 0x10u;
            br.ReadBoolean();

            br.ReadUnsignedChar();
            br.ReadUnsignedChar();
            br.ReadShort();
            br.ReadShort();
            br.ReadShort();
            br.ReadShort();
            
            br.ReadBoolean();  // if then *((_BYTE *)v3 + 29) |= 2u;
            
            br.ReadAscii();

            if (tag == 7) return;
            
            br.ReadBoolean();  // if then *((_BYTE *)v3 + 29) |= 1u;
            switch (tag)
            {
                case 25:
                    br.ReadInt32();
                    return;
                case 21:
                    // *(_BYTE *)(v3 + 29) |= 0x20u;
                    br.ReadInt32();
                    return;
                case 20:
                    // *(_BYTE *)(v3 + 29) |= 0x20u;
                    return;
            }
            
            if (tag >= 33)
            {
                br.ReadInt32();
                br.ReadShort();
                br.ReadShort();
                // *(_BYTE *)(v3 + 29) |= 0x20u;
                if ((uint) (tag - 43) <= 1)
                {
                    br.ReadShort();  // v11
                    // v12 = ((unsigned __int64)(-40656255836343LL * v11) >> 32) + 0x7FFF * v11;
                    // *(_WORD *)(v3 + 34) = (v12 >> 8) + (v12 >> 31);
                }

                if (tag == 44 && br.ReadBoolean())
                {
                    // *(_BYTE *)(v3 + 29) |= 0x40;
                }
            }
        }

        public override string GetDataTypeName()
        {
            return "TextField";
        }
    }
}