package cn.hstc.wwq.jtable;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CreateTable extends JFrame {

	public CreateTable() {
		Object[][] cellData = { { "row1-col1", "row1-col2", "", "", "" },
				{ "row2-col1", "row2-col2", "", "", "" } };
		String[] columnNames = { "�ļ���", "�ϴ�ʱ��", "�ļ�����", "�ϴ���", "����" };
		JTable table = new JTable(cellData, columnNames);
		table.setEnabled(false);

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumn("�ļ���").setCellRenderer(render);
		table.getColumn("�ϴ�ʱ��").setCellRenderer(render);
		table.getColumn("�ļ�����").setCellRenderer(render);
		table.getColumn("�ϴ���").setCellRenderer(render);
		table.getColumn("����").setCellRenderer(render);
		
		table.setRowHeight(40);
		
		JPanel jContentPane = new JPanel();
		jContentPane.setLayout(null);
		JScrollPane js = new JScrollPane(table);
		js.setSize(new Dimension(179, 144));
		jContentPane.add(js, null);
		
		this.add(jContentPane);
		
		
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		CreateTable c = new CreateTable();
	}
}
