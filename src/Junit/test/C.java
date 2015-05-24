package Junit.test;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

class C extends JFrame { 
	   public C() { 
	       JTable tbl = new JTable(new String[][]
	    		   {
		    		   { 
		               "文件1", 
		               "logo4-04.png", 
		               "2015-05-19 09:08:14", 
		               "这个是最新的",
		               "这个是用户名",
		               "这个是操作"
		               }
	    		   
	    		   }, 
	    		   
	               "文件编号 文件名 上传时间 文件描述 上传者 操作".split(" ")); 
	       tbl.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer()); 
	       
	       tbl.setEnabled(false);
	       add(new JScrollPane(tbl)); 
	       setSize(600, 400); 
	       setVisible(true); 
	       setDefaultCloseOperation(EXIT_ON_CLOSE); 
	   } 

	   public static void main(String[] args) { new C(); } 
	} 

	class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer { 
	   public TableCellTextAreaRenderer() { 
	       setLineWrap(true); 
	       setWrapStyleWord(true); 
	   } 

	   public Component getTableCellRendererComponent(JTable table, Object value, 
	           boolean isSelected, boolean hasFocus, int row, int column) { 
	       // 计算当下行的最佳高度 
	       int maxPreferredHeight = 0; 
	       for (int i = 0; i < table.getColumnCount(); i++) { 
	           setText("" + table.getValueAt(row, i)); 
	           setSize(table.getColumnModel().getColumn(column).getWidth(), 0); 
	           maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height); 
	       } 

	       if (table.getRowHeight(row) != maxPreferredHeight)  // 少了这行则处理器瞎忙 
	           table.setRowHeight(row, maxPreferredHeight); 

	       setText(value == null ? "" : value.toString()); 
	       return this; 
	   } 
	} 
