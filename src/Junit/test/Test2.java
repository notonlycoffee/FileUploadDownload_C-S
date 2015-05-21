package Junit.test;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test2 extends JFrame implements ActionListener {

	private JButton b0, b1, b2, b3;
	private Panel cardPanel = new Panel();
	private Panel controlpaPanel = new Panel();
	// ���忨Ƭ���ֶ���
	private CardLayout card = new CardLayout();
	// �����ַ����飬Ϊ��Ƭ����
	private String cardName[] = { "0", "1", "2", "3" };
	// ���幹�캯��
	public Test2() {
		super();
		// ����cardPanel������Ϊ��Ƭ����
		cardPanel.setLayout(card);
		// ѭ������cardPanel�����������4����ť
		// ��ΪcardPanel������Ϊ��Ƭ���֣���˳�ʼʱ��ʾ������ӵ����
		for (int i = 0; i < 4; i++) {
			// �������ӵ�ÿ����ť��Ӧ����һ����Ƭ��
			cardPanel.add(cardName[i], new JButton("��ť" + i));
		}

		// ʵ������ť����

		b0 = new JButton("0");
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");

		// Ϊ��ť����ע�������

		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		controlpaPanel.setLayout(new GridLayout(4, 1));
		
		controlpaPanel.add(b0);
		controlpaPanel.add(b1);
		controlpaPanel.add(b2);
		controlpaPanel.add(b3);

		// ������������Ϊ��ǰ������������

//		Container container = getContentPane();

		// �� cardPanel�������ڴ��ڱ߽粼�ֵ��м䣬����Ĭ��Ϊ�߽粼��
		this.add(cardPanel, BorderLayout.CENTER);
		// ��controlpaPanel�������ڴ��ڱ߽粼�ֵ��ϱߣ�
		this.add(controlpaPanel, BorderLayout.WEST);
		
		
		
		this.setSize(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	// ʵ�ְ�ť�ļ�������ʱ�Ĵ���
	public void actionPerformed(ActionEvent e) {
		// �û�����b0��ťʱִ�е����
		if (e.getSource() == b0) {
			// ͨ��show()�����еĿ�Ƭ���ƣ���ʾ�����е������
			card.show(cardPanel, cardName[0]);
		}
		if (e.getSource() == b1) {
			card.show(cardPanel, cardName[1]);
		}
		if (e.getSource() == b2) {
			card.show(cardPanel, cardName[2]);
		}
		if (e.getSource() == b3) {
			card.show(cardPanel, cardName[3]);
		}
	}
	public static void main(String[] args) {
		Test2 kapian = new Test2();
	}

}
