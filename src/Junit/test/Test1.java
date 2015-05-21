package Junit.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test1 {

	// JDialog

	public static void main(String[] args) {
		JFrame jf = new JFrame();
		JButton jb = new JButton("Ã·Ωª");
		jf.add(jb);
		jb.addActionListener(new JbListener());
		
		jf.setSize(400,400);
		jf.setVisible(true);
		
	}

}

class JbListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("fuck");
		
	}
	
}
