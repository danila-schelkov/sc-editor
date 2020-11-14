using System;
using System.Windows.Forms;

namespace sc_editor
{
    public partial class Form1 : Form
    {
        private byte[] _decompressed;
        
        public Form1()
        {
            InitializeComponent();
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            var dialog = new OpenFileDialog();
            var result = dialog.ShowDialog();

            if (result != DialogResult.OK) return;
            
            var path = dialog.FileName;
            Console.WriteLine(path);

            _decompressed = Compression.Decompress(path);
            Compression.Decompress(path, path);
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Compression.Compress(
                _decompressed, 
                @"C:\Users\Admin\PycharmProjects\sc-compression\examples\decompression\in\characters.new.sc", 
                Compression.Signature.Lzma
            );
        }
    }
}