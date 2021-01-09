using System.Drawing;

namespace sc_editor.SupercellSWF
{
    public class ColorTransform
    {
        private float _alpha;
        private float R { get; set; }
        private float G { get; set; }
        private float B { get; set; }

        public ColorTransform(float r, float g, float b)
        {
            R = r;
            G = g;
            B = b;
        }

        public void SetAlpha(float alpha)
        {
            if (alpha <= 1.0)
                _alpha = alpha;
        }
    }
}