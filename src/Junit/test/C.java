package Junit.test;

import java.awt.Component;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

class C extends JFrame {
	   public C() throws Exception {
		    Map filemap = new HashMap();
			String url = "http://127.0.0.1:8080/fileoperation/servlet/ListFileServlet";
			filemap = getAllFile(url);
			JTable tbl = createTable(map2array(filemap));
	       JScrollPane jp = new JScrollPane(tbl);
	       add(jp);
	       setSize(600, 400); 
	       setVisible(true); 
	       setDefaultCloseOperation(EXIT_ON_CLOSE); 
	   } 
	   
	   public static JTable createTable(String [] [] str) {
		   JTable tbl = new JTable(str, 
	    		   
	               "�ļ���� �ļ��� �ϴ�ʱ�� �ļ����� �ϴ���".split(" ")
	               
	    		   ); 
	       tbl.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer()); 
	       
	       tbl.setEnabled(false);
	       
	       return tbl;
	   }
	   
	   
	   public static String[][] map2array(Map map) {
			
			Map tempMap = new HashMap();
			String [][] filearray = new String [map.size()-1][5];
//			System.out.println(map.size()-1);
			int length = map.size() -1;
			for( int i = 0 ; i < length ; i++) {
				
				tempMap = (Map) map.get("" + (i+1));
				for( int j = 1 ; j < 5; j++) {
					String s = (String) tempMap.get("" + (j-1));
//					System.out.println(s);
					filearray[i][j] = s;
				}
				filearray[length-1-i][0] = (String) map.keySet().toArray()[i+1];  //�ļ����
				
			}
			return filearray;
			
		}

	   public static void main(String[] args) throws Exception { 
		   new C(); 
	   } 
	  
	   public static Map getAllFile(String url) throws Exception {
			Map map = new LinkedHashMap();
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			try {
				HttpGet httpGet = new HttpGet(url);

				CloseableHttpResponse response1 = httpclient.execute(httpGet);

				try {
					HttpEntity entity1 = response1.getEntity();
					
					
					StringBuffer sb = new StringBuffer();
					InputStream in = entity1.getContent();
					InputStreamReader reader = new InputStreamReader(in, "UTF-8");
					int len = 0;
					char[] buff = new char[1024];
					while ((len = reader.read(buff)) > -1) {
						sb.append(buff);
						
					}
					
					String html = sb.toString();
					
					//System.out.println(html);
					
				    map = TestListFile.parsehtml(html);//�Ի�õ�htmlҳ�����ɸѡ
				    
//				    Map maphead = (Map) map.get("tablehead");
//				    String value = (String) maphead.get("filename");
//				    System.out.println(value);

					EntityUtils.consume(entity1);
				} finally {
					response1.close();
				}
			} finally {
				httpclient.close();
			}
			return map;
		}

		public static Map parsehtml(String html) {
			Map map = new LinkedHashMap();  //���յ�map����,���ʱ�Ǳ�ͷ��Ϣ,�Լ�ÿ���ļ��ľ�����Ϣ;
			Map headMap = new HashMap();  //��ű�ͷ����Ϣ,����	�ļ�����	�ϴ�ʱ��	�ļ�����	�ϴ���	����
			
			html = html.replaceAll("(\r*)(\n*)(\t*)", "");
//			System.out.println(html);  //����滻���س����к������      \n ����; \r �س�;  \t ����;
			Pattern p = Pattern.compile("(<table border=\"1\">)(.*)(</table>)");
			Matcher m = p.matcher(html);
			if( m.find()) {
				String str = m.group(2);
//				System.out.println("********************************************************************************************************");
//				System.out.println(str);
//				System.out.println("********************************************************************************************************");
				
				
				//ȥ��<tr> <td> <td style....>
				str = str.replaceAll("<tr>|<td>|<td style=\"width:200px;\">", "");
//				System.out.println("===========================================================================================================================");
//				System.out.println(str);
//				System.out.println("===========================================================================================================================");
				
				
				//��ÿһ����� ÿһ������:			�ļ�����</td>�ϴ�ʱ��</td>�ļ�����</td>�ϴ���</td>����</td>
				String [] strarray = str.split("</tr>");  //strarray[0]��    �ļ�����</td>�ϴ�ʱ��</td>�ļ�����</td>�ϴ���</td>����</td>
	//strarray[1]��ʼ��
	//logo4-04.png</td>2015-05-19 09:08:14.0</td>��������µ�</td>���µ�</td><a href="servlet/DownLoadServlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">����</a><a href="servlet/ChagenFileServlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">�޸��ļ�</a><a href="servlet/DeleteFileservlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">ɾ��</a></td>

				/*  ��ʾstrarray���������Ԫ��
				System.out.println(strarray.length);
				
				for( String s : strarray) {
					System.out.println("***************************************************************");
					System.out.println(s);
					System.out.println("***************************************************************");
				}
				*/
				//��ȡ��ͷ
				String [] tablehead = strarray[0].split("</td>");
//				�ļ�����
//				�ϴ�ʱ��
//				�ļ�����
//				�ϴ���
//				����
				
				/*
				//  ��ʾ��ͷ������   	�ļ�����	�ϴ�ʱ��	�ļ�����	�ϴ���	����
				for(String s: tablehead) {
					System.out.println("*******************************************************************");
					System.out.println(s);
					System.out.println("*******************************************************************");
				}
				*/
				headMap.put("filename", tablehead[0]);
				headMap.put("uptime", tablehead[1]);
				headMap.put("description", tablehead[2]);
				headMap.put("username", tablehead[3]);
				headMap.put("operation", tablehead[4]);
				map.put("tablehead", headMap);
				
				
				//�����ǲ����ļ��ľ�����Ϣ,���ļ��ľ����ļ���,�ϴ�ʱ���
				for(int i = 1 ; i < strarray.length; i++) {
					String [] content = strarray[i].split("</td>");
					
//					for(String s: content) {
//						System.out.println("*******************************************************************");
//						System.out.println(s);
//					}
					
					Map filemap = new HashMap();
					for( int j = 0 ; j < content.length-1 ; j++) {
						//System.out.println(content[j]);
						filemap.put("" + j, content[j]);
						//System.out.println(i+j);
					}
					
					String path = content[4];
	//<a href="servlet/DownLoadServlet?id=af07fca6-f874-441e-b01f-211241730939">����</a><a href="servlet/ChagenFileServlet?id=af07fca6-f874-441e-b01f-211241730939">�޸��ļ�</a><a href="servlet/DeleteFileservlet?id=af07fca6-f874-441e-b01f-211241730939">ɾ��</a>

					p = Pattern.compile("(.*)(=)(.*)(\">��)(.*)");
					m = p.matcher(path);
					if(m.find()) {
						String id = m.group(3);
						filemap.put("id", id);
					} else {
						System.out.println("n");
					}
					
//					String value = (String) filemap.get("0");
//					System.out.println(value);
//					
//					value = (String) filemap.get("1");
//					System.out.println(value);
//					
//					value = (String) filemap.get("2");
//					System.out.println(value);
//					
//					value = (String) filemap.get("3");
//					System.out.println(value);
//					
//					value = (String) filemap.get("4");
//					System.out.println(value);
					
					
					map.put(""+i, filemap);
					//System.out.println("*****************************************");
				}
				
				
				
//				@Test
//				public void test5() {
//					Map map = new LinkedHashMap();
//					map.put("1", "aaa");
//					map.put("2", "bbb");
//					map.put("3", "ccc");
//					
//					for(Object obj:map.keySet()) {
//						String key = (String)obj;
//						String value = (String)map.get(key);
//						System.out.println(key + " = " + value);
//					}
//				}
		
				
				
				/*
				//map��������˴洢ȫ��������,�鿴�洢�������Ƿ���ȷ
				Map ma = (Map) map.get("tablehead");
				String value = (String) ma.get("filename");
				System.out.println(value);
				
				ma = (Map) map.get("1");
				value = (String) ma.get("id");
				System.out.println(value);
				value = (String) ma.get("0");
				System.out.println(value);
				*/
				
				
			}
			else {
				System.out.println("no");
			}
			return map;
	    }

	} 

	class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer { 
	   public TableCellTextAreaRenderer() { 
	       setLineWrap(true); 
	       setWrapStyleWord(true); 
	   } 

	   public Component getTableCellRendererComponent(JTable table, Object value, 
	           boolean isSelected, boolean hasFocus, int row, int column) { 
	       // ���㵱���е���Ѹ߶� 
	       int maxPreferredHeight = 0; 
	       for (int i = 0; i < table.getColumnCount(); i++) { 
	           setText("" + table.getValueAt(row, i)); 
	           setSize(table.getColumnModel().getColumn(column).getWidth(), 0); 
	           maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height); 
	       } 

	       if (table.getRowHeight(row) != maxPreferredHeight)  // ��������������Ϲæ 
	           table.setRowHeight(row, maxPreferredHeight); 

	       setText(value == null ? "" : value.toString()); 
	       return this; 
	   } 
	}
	
	
