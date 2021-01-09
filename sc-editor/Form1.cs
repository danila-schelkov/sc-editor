using System;
using System.Diagnostics;
using System.Windows.Forms;
using sc_editor.SupercellSWF;

namespace sc_editor
{
    public partial class Form1 : Form
    {
        private SupercellSWF.SupercellSWF _decoder;

        public Form1()
        {
            InitializeComponent();

            _decoder = new SupercellSWF.SupercellSWF();
        }

        #region File Menu
        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            var dialog = new OpenFileDialog();
            var result = dialog.ShowDialog();

            if (result != DialogResult.OK) return;
            var path = dialog.FileName;
            
            // TODO: сделать диалоговое окно по типу "хотите сохранить изменения в файле?"
            _decoder = new SupercellSWF.SupercellSWF();
            treeView1.Nodes.Clear();
            
            _decoder.Load(path);
            
            treeView1.Populate(_decoder.GetExports(), "Exports");
            treeView1.Populate(_decoder.GetTextures());
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            var dialog = new SaveFileDialog();
            var result = dialog.ShowDialog();

            if (result != DialogResult.OK) return;
            
            var path = dialog.FileName;
            // Compression.Compress(
            //     _decompressed, 
            //     path, 
            //     Compression.Signature.Sc
            // );
        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            treeView1.Nodes.Clear();
            Application.Exit();
        }
        #endregion

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            pictureBox1.Image = null;
            
            var node = treeView1.SelectedNode;
            var data = node.Tag;
            
            if (data != null)
            {
                Debug.WriteLine(node.Text);
                
                pictureBox1.Image = ((SWFObject) data).Render();
                pictureBox1.Refresh();
            }
        }
    }
}