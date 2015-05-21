package Junit.test;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class TestFileChoose extends JFrame {

	JFileChooser jfc = new JFileChooser();
	
	public TestFileChoose() {
		
		String path = null;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("请选择要上传的文件...");
		fc.setApproveButtonText("确定");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) {
		    path=fc.getSelectedFile().getPath();
		}
		
		this.setTitle("UDP服务器");
		this.setSize(650, 510);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		TestFileChoose ui = new TestFileChoose();
	}
	
}
