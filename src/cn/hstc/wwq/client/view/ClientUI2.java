package cn.hstc.wwq.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import Junit.test.TestListFile;

public class ClientUI2 extends JFrame {


	private JLabel banner = new JLabel("XXX�ļ�����ϵͳ", JLabel.CENTER);
	private JLabel welcomeword = new JLabel("��ӭʹ��XX�ļ�����ϵͳ", JLabel.CENTER);
	private JButton upfilebutton = new JButton("�ϴ��ļ�");
	private JButton checkfilebutton = new JButton("�鿴�ļ�");

	private JLabel successupload = new JLabel("�ϴ��ɹ�!!!", JLabel.CENTER);
	private JLabel failupload = new JLabel("�ϴ�ʧ��!!!", JLabel.CENTER);

	private JLabel successchange = new JLabel("�޸ĳɹ�!!!", JLabel.CENTER);
	private JLabel failchange = new JLabel("�޸�ʧ��!!!", JLabel.CENTER);

	private JLabel successdelete = new JLabel("ɾ���ɹ�!!!", JLabel.CENTER);
	private JLabel faildelete = new JLabel("ɾ��ʧ��!!!", JLabel.CENTER);
	
	private JLabel successdownload = new JLabel("���سɹ�!!!", JLabel.CENTER);
	private JLabel faildownload = new JLabel("����ʧ��!!!", JLabel.CENTER);

	private JPanel containsAll = new JPanel(); // ����Ĳ���,����banner,���,�ұߵĿ�Ƭ����
	private JPanel leftoperation = new JPanel(); // ��ߵĲ�������
	private JPanel cardPanel = new JPanel(); // ��Ƭ���ֵ�����,װ��5������

	private Border lineBorder = new LineBorder(Color.BLACK, 1);// ���ñ߿�

	private CardLayout card = new CardLayout();
	
	private JLabel jl = new JLabel("�ļ�����:");
	private JTextField jtf = new JTextField(5);//�ļ����������
	private JButton changeButton = new JButton("�޸�");
	private JButton deleteButton = new JButton("ɾ��");
	private JButton downloadButton = new JButton("����");

	private static final String DISPLAYWELCOME = "welcome"; // ��Ƭ�����ı�־
	private static final String UPFILE = "upfile";
	private static final String FILELIST = "filelist";
	private static final String CHANGEFILE = "changefile";
	private static final String SUCCESSUPLOAD = "successupload";
	private static final String FAILUPLOAD = "failupload";
	private static final String SUCCESSCHANGE = "successchange";
	private static final String FAILCHANGE = "failchange";
	private static final String SUCCESSDELETE = "successdelete";
	private static final String FAILDELETE = "faildelete";
	private static final String SUCCESSDOWNLOAD = "successdownload";
	private static final String FAILDOWNLOAD = "faildownload";
	
	private String filepath = "";

	// �ļ��ϴ���Ҫ�����
	private JLabel userNameLabel = new JLabel("�ϴ��û�");
	private JTextField userNameInput = new JTextField(10);
	private JLabel bank = new JLabel("      ");
	private JButton uploadButton = new JButton("�ϴ��ļ�");
	private JLabel descriptionLabel = new JLabel("�ļ�����");
	private JTextArea descriptionjtext = new JTextArea(8, 30);
	private JButton submitButton = new JButton("�ύ");
	
	
	
	//�ļ��޸���Ҫ�����
	private JLabel changeUserNameLabel = new JLabel("�ϴ��û�");
	private JTextField changeUserNameInput = new JTextField(10);
	private JLabel changeBank = new JLabel("      ");
	private JLabel chagenFileNameLabel = new JLabel("�ļ�����");
	private JTextField changeFileNameInput = new JTextField(10);
	private JLabel changeDescriptionLabel = new JLabel("�ļ�����");
	private JTextArea changeDescriptionjtext = new JTextArea(8, 30);
	private JButton changeSubmitButton = new JButton("�޸�");
	
	
	private Map filemap = new HashMap();
	
	public ClientUI2() throws Exception {

		// ����������ʽ
		banner.setFont(new Font("Dialog", 1, 50));
		welcomeword.setFont(new Font("Dialog", 1, 30));
		successupload.setFont(new Font("Dialog", 1, 50));
		failupload.setFont(new Font("Dialog", 1, 50));

		successchange.setFont(new Font("Dialog", 1, 50));
		failchange.setFont(new Font("Dialog", 1, 50));

		successdelete.setFont(new Font("Dialog", 1, 50));
		faildelete.setFont(new Font("Dialog", 1, 50));
		
		successdownload.setFont(new Font("Dialog", 1, 50));
		faildownload.setFont(new Font("Dialog", 1, 50));

		// ���ñ߿�
		leftoperation.setBorder(lineBorder);
		cardPanel.setBorder(lineBorder);
		banner.setBorder(new TitledBorder("by_wwq"));

		// ����ߵ�����������ʽ
		leftoperation.setPreferredSize(new Dimension(100, 450));
		leftoperation.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
		upfilebutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		checkfilebutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		changeSubmitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// ����߼������
		leftoperation.add(upfilebutton);
		leftoperation.add(checkfilebutton);

		// �ļ��ϴ�ҳ��
		JPanel upfilejpanel = new JPanel();
		JPanel p_file = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 50));
		p_file.setPreferredSize(new Dimension(600, 400));
		uploadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_file.add(userNameLabel);
		p_file.add(userNameInput);
		p_file.add(uploadButton);
		p_file.add(bank);
		p_file.add(descriptionLabel);
		p_file.add(descriptionjtext);
		p_file.add(submitButton);
		upfilejpanel.add(p_file);

		
		//�ļ��޸�ҳ��
		JPanel changefilejpanel = new JPanel();
		JPanel c_file = new JPanel(new FlowLayout(FlowLayout.LEFT, 130, 30));
		c_file.setPreferredSize(new Dimension(600, 400));
		
		c_file.add(changeUserNameLabel);
		c_file.add(changeUserNameInput);
		c_file.add(changeBank);
		c_file.add(changeBank);
		c_file.add(chagenFileNameLabel);
		c_file.add(changeFileNameInput);
		c_file.add(changeDescriptionLabel);
		c_file.add(changeDescriptionjtext);
		c_file.add(changeSubmitButton);
		changefilejpanel.add(c_file);
		
//		private JLabel changeUserNameLabel = new JLabel("�ϴ��û�");
//		private JTextField changeUserNameInput = new JTextField(10);
//		private JLabel changeBank = new JLabel("      ");
//		private JLabel chagenFileNameLabel = new JLabel("�ļ�����");
//		private JTextField changeFileNameInput = new JTextField(10);
//		private JLabel changeDescriptionLabel = new JLabel("�ļ�����");
//		private JTextArea changeDescriptionjtext = new JTextArea(8, 30);
//		private JButton changeSubmitButton = new JButton("�޸�");
		
		
		
		//�ļ��б�ҳ��
		JPanel filelist = new JPanel();
		JPanel filelistinside = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 59));
//		filelistinside.add(new JLabel("�ļ��б�"));
//		filelist.add(filelistinside);
		
		
		String url = "http://127.0.0.1:8080/fileoperation/servlet/ListFileServlet";
		filemap = getAllFile(url);
		JTable tbl = createTable(map2array(filemap));
		
		JScrollPane jp = new JScrollPane(tbl);
		jp.setPreferredSize(new Dimension(620,400));
		
		filelistinside = tableinpanel(jp);
		
		filelist.add(filelistinside);
		
		// �޸��ļ���Ϣ
		// JPanel changefilejpanel = new JPanel();
		// changefilejpanel.add(p_file);

		// ����Ƭ����������ʽ
		cardPanel.setLayout(card);
		cardPanel.setPreferredSize(new Dimension(630, 450));

		// ����Ƭ�����������
		cardPanel.add(this.DISPLAYWELCOME, welcomeword);
		cardPanel.add(this.UPFILE, upfilejpanel);
		cardPanel.add(this.SUCCESSUPLOAD, successupload);
		cardPanel.add(this.FAILUPLOAD, failupload);
	    cardPanel.add(this.FILELIST, filelist);
		
	    cardPanel.add(this.CHANGEFILE, changefilejpanel);
		
	    cardPanel.add(this.SUCCESSCHANGE, successchange);
		cardPanel.add(this.FAILCHANGE, failchange);
		cardPanel.add(this.SUCCESSDELETE, successdelete);
		cardPanel.add(this.FAILDELETE, faildelete);
		cardPanel.add(this.SUCCESSDOWNLOAD, successdownload);
		cardPanel.add(this.FAILDOWNLOAD, faildownload);

		// �������������Ĳ���,���Ҽ���ͷ��,�󲿺���������
		containsAll.setLayout(new BorderLayout());
		containsAll.add(banner, BorderLayout.NORTH);
		containsAll.add(leftoperation, BorderLayout.WEST);
		containsAll.add(cardPanel, BorderLayout.EAST);

		// ע�������
		upfilebutton.addActionListener(new ClickListener());
		uploadButton.addActionListener(new ClickListener());
		submitButton.addActionListener(new ClickListener());
		checkfilebutton.addActionListener(new ClickListener());
		changeButton.addActionListener(new ClickListener());
		deleteButton.addActionListener(new ClickListener());
		downloadButton.addActionListener(new ClickListener());
		changeSubmitButton.addActionListener(new ClickListener());
		
		this.add(containsAll);
		this.setTitle("�ļ��ϴ������");
		this.setSize(750, 610);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	// public JPanel createWordPage(JLabel wordlabel) {
	// JPanel displaypage = new JPanel();
	// displaypage.add(wordlabel);
	// return displaypage;
	// }

	public static void main(String[] args) throws Exception {
		ClientUI2 ui = new ClientUI2();
	}

	public JPanel tableinpanel(JScrollPane jp) {
		JPanel jpanel = new JPanel(new BorderLayout());
		
		JPanel operation = new JPanel(new FlowLayout());
		
//		private JLabel jl = new JLabel("�ļ�����:");
//		private JTextField jtf = new JTextField();//�ļ����������
//		private JButton changeButton = new JButton("�޸�");
//		private JButton deleteButton = new JButton("ɾ��");
		
		changeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		downloadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		operation.add(jl);
		operation.add(jtf);
		operation.add(changeButton);
		operation.add(deleteButton);
		operation.add(downloadButton);
		
		jpanel.add(operation, BorderLayout.NORTH);
		jpanel.add(jp, BorderLayout.CENTER);
		return jpanel;
				
	}
	
	class ClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == upfilebutton) {
				card.show(cardPanel, ClientUI2.UPFILE);
			}
			if (e.getSource() == uploadButton) {
				System.out.println("���ﴦ���ļ��ϴ�");

				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("��ѡ��Ҫ�ϴ����ļ�...");
				fc.setApproveButtonText("ȷ��");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(cardPanel)) {
					filepath = fc.getSelectedFile().getPath();
					System.out.println(filepath);
				}
			}
			if (e.getSource() == submitButton) {
				try {
				String username = userNameInput.getText().trim();
				String description = descriptionjtext.getText();

				System.out.println(username);
				System.out.println(description);
				System.out.println(filepath);
				
				String url = "http://127.0.0.1:8080/fileoperation/servlet/UpfileServlet";
				httpfileupload(filepath, username, description, url);
				card.show(cardPanel, ClientUI2.SUCCESSUPLOAD);
				
				
				} catch(Exception ex) {
					ex.printStackTrace();
					card.show(cardPanel, ClientUI2.FAILUPLOAD);
					throw new RuntimeException(ex);
				}
			}
			if(e.getSource() == checkfilebutton) {
				try {
					String url = "http://127.0.0.1:8080/fileoperation/servlet/ListFileServlet";
					Map filemap = new HashMap();
					filemap = ClientUI2.getAllFile(url);
					
					
					
					
					card.show(cardPanel, ClientUI2.FILELIST);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
			if( e.getSource() == changeButton ){
				//�������޸��ļ���Ϣ��ҳ��
				try {
					//�޸��ļ���Ϣ
					String filenum = jtf.getText().trim();
					int num = Integer.parseInt(filenum);
					if(num <= 0 || num > filemap.size()-1){
						JOptionPane.showMessageDialog(null, "�����ǹ涨���ļ������", "������ʾ",JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					//�����������ļ����޸�ҵ��
					//
					filenum = (filemap.size()-num) + "";
					Map map = (Map) filemap.get(filenum);
					
					String id = (String) map.get("id");
					System.out.println(id);
					//���ݻ���
					String username = (String) map.get("3");
					String filename = (String) map.get("0");
					String description = (String) map.get("2");
					
					changeUserNameInput.setText(username);
					changeFileNameInput.setText(filename);
					changeDescriptionjtext.setText(description);
					
					card.show(cardPanel, ClientUI2.CHANGEFILE);

				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "�����ǹ涨���ļ������", "������ʾ",JOptionPane.ERROR_MESSAGE);
					card.show(cardPanel, ClientUI2.FAILCHANGE);
				}
			}
			
			if(e.getSource() == changeSubmitButton) {
				try {
					System.out.println("��������޸��ļ��İ�ť");
					
					String username = changeUserNameInput.getText().trim();
							
					String filename = changeFileNameInput.getText().trim();
					
					String description = changeDescriptionjtext.getText().trim();
					
					String filenum = jtf.getText().trim();
					int num = Integer.parseInt(filenum);
					filenum = (filemap.size()-num) + "";
					Map map = (Map) filemap.get(filenum);
					
					String id = (String) map.get("id");
					
					System.out.println(id);
					chagenfile(username,filename,description,id);
					card.show(cardPanel, ClientUI2.SUCCESSCHANGE);
				} catch(Exception ex) {
					card.show(cardPanel, ClientUI2.FAILCHANGE);
				}
				
			}
			 
			if( e.getSource() == deleteButton ){
				//������ɾ���ļ���ҳ��
				try {
					String filenum = jtf.getText().trim();
					int num = Integer.parseInt(filenum);
					if(num <= 0 || num > filemap.size()-1) {
						JOptionPane.showMessageDialog(null, "�����ǹ涨���ļ������", "������ʾ",JOptionPane.ERROR_MESSAGE);
						return ;
					}
					//�����������ļ���ɾ��ҵ��
					filenum = (filemap.size()-num) + "";
					Map map = (Map) filemap.get(filenum);
					String id = (String) map.get("id");
					
					deletefile(id);
					card.show(cardPanel, ClientUI2.SUCCESSDELETE);
					
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "�����ǹ涨���ļ������", "������ʾ",JOptionPane.ERROR_MESSAGE);
					card.show(cardPanel, ClientUI2.FAILDELETE);
				}
			}
			
			if( e.getSource() == downloadButton) {
				//���ﴦ���ļ���������
				try {
					//�����ļ���Ϣ
					String filenum = jtf.getText().trim();
					int num = Integer.parseInt(filenum);
					if(num <= 0 || num > filemap.size()-1)
						JOptionPane.showMessageDialog(null, "�����ǹ涨���ļ������", "������ʾ",JOptionPane.ERROR_MESSAGE);
					
					//�����������ļ�������ҵ��
					filenum = (filemap.size()-num) + "";
					Map map = (Map) filemap.get(filenum);
					String id = (String) map.get("id");
					
					String destFileName = (String) map.get("0");
					
					destFileName = "C:\\\\" + destFileName;
					
					ClientUI2.downFile(id, destFileName);
					card.show(cardPanel, ClientUI2.SUCCESSDOWNLOAD);
					JOptionPane.showMessageDialog(null, "�ļ�����Ĭ��·��:c��Ŀ¼��"); 
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "�����ǹ涨���ļ������", "������ʾ",JOptionPane.ERROR_MESSAGE);
					card.show(cardPanel, ClientUI2.FAILDOWNLOAD);
				}
			}
		}

	public void chagenfile(String username, String filename,String description, String id) throws Exception {
		String url = "http://127.0.0.1:8080/fileoperation/servlet/ChagenFileServlet?id=";
		url = url + id.trim();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("filename", filename));
			nvps.add(new BasicNameValuePair("description", description));
			
			StringEntity entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
			
			httpPost.setEntity(entity);
			CloseableHttpResponse response2 = httpclient.execute(httpPost);
			try {
				System.out.println(response2.getStatusLine());
				HttpEntity entity2 = response2.getEntity();
				EntityUtils.consume(entity2);
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}
	}
	}
	
	
	public JTable map2table(Map map) {
		
		return null;
	}

	public void httpfileupload(String filepath, String username, String description, String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			FileBody file = new FileBody(new File(filepath));

			HttpEntity reqEntity = MultipartEntityBuilder
					.create()
					.addPart("file", file)
					.addPart("username",new StringBody(username, Charset.forName("UTF-8")))
					.addPart("description",new StringBody(description, Charset.forName("UTF-8")))
					.build();

			httppost.setEntity(reqEntity);

			System.out
					.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					System.out.println("Response content length: "
							+ resEntity.getContentLength());
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
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
			    map = TestListFile.parsehtml(html);//�Ի�õ�htmlҳ�����ɸѡ
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
		Map headMap = new LinkedHashMap();  //��ű�ͷ����Ϣ,����	�ļ�����	�ϴ�ʱ��	�ļ�����	�ϴ���	����
		html = html.replaceAll("(\r*)(\n*)(\t*)", "");
		Pattern p = Pattern.compile("(<table border=\"1\">)(.*)(</table>)");
		Matcher m = p.matcher(html);
		if( m.find()) {
			String str = m.group(2);
			//ȥ��<tr> <td> <td style....>
			str = str.replaceAll("<tr>|<td>|<td style=\"width:200px;\">", "");
			
			String [] strc = str.split("</table>");
//			System.out.println(strc.length);
			
			str = strc[0];
			//��ÿһ����� ÿһ������:			�ļ�����</td>�ϴ�ʱ��</td>�ļ�����</td>�ϴ���</td>����</td>
			String [] strarray = str.split("</tr>");  //strarray[0]��    �ļ�����</td>�ϴ�ʱ��</td>�ļ�����</td>�ϴ���</td>����</td>
//strarray[1]��ʼ��
//logo4-04.png</td>2015-05-19 09:08:14.0</td>��������µ�</td>���µ�</td><a href="servlet/DownLoadServlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">����</a><a href="servlet/ChagenFileServlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">�޸��ļ�</a><a href="servlet/DeleteFileservlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">ɾ��</a></td>
			//��ȡ��ͷ
			String [] tablehead = strarray[0].split("</td>");
//			�ļ�����
//			�ϴ�ʱ��
//			�ļ�����
//			�ϴ���
//			����
			headMap.put("filename", tablehead[0]);
			headMap.put("uptime", tablehead[1]);
			headMap.put("description", tablehead[2]);
			headMap.put("username", tablehead[3]);
			headMap.put("operation", tablehead[4]);
			map.put("tablehead", headMap);
			//�����ǲ����ļ��ľ�����Ϣ,���ļ��ľ����ļ���,�ϴ�ʱ���
			for(int i = 1 ; i < strarray.length; i++) {
				String [] content = strarray[i].split("</td>");
				Map filemap = new HashMap();
				for( int j = 0 ; j < content.length-1 ; j++) {
					filemap.put("" + j, content[j]);
				}
				System.out.println("*******************************************");
				System.out.println(content.length);
				System.out.println("*******************************************");
				String path = content[4];
				
				
				p = Pattern.compile("(.*)(=)(.*)(\">��)(.*)");
				m = p.matcher(path);
				if(m.find()) {
					String id = m.group(3);
					filemap.put("id", id);
				} else {
					System.out.println("n");
				}
				map.put(""+i, filemap);
			}
		}
		else {
			System.out.println("no");
		}
		return map;
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
//		System.out.println(map.size()-1);
		int length = map.size() -1;
		for( int i = 0 ; i < length ; i++) {
			
			tempMap = (Map) map.get("" + (i+1));
			for( int j = 1 ; j < 5; j++) {
				String s = (String) tempMap.get("" + (j-1));
//				System.out.println(s);
				filearray[i][j] = s;
			}
			filearray[length-1-i][0] = (String) map.keySet().toArray()[i+1];  //�ļ����
			
		}
		return filearray;
		
	}
	
	public static void deletefile(String id) throws Exception {
		String url = "http://127.0.0.1:8080/fileoperation/servlet/DeleteFileservlet?id=";
		url = url + id;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			
			try {
				System.out.println(response1.getStatusLine());
				HttpEntity entity1 = response1.getEntity();
				
				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}
	}
	
	public static void downFile(String id, String destFileName)throws Exception {
		// ����һ��httpclient����
		String url = "http://127.0.0.1:8080/fileoperation/servlet/DownLoadServlet?id=";
		url = url + id;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		File file = new File(destFileName);
		try {
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
				// ע�����������OutputStream.write(buff)�Ļ���ͼƬ��ʧ�棬��ҿ�������
			}
			fout.flush();
			fout.close();
		} finally {
			// �رյͲ�����
			in.close();
		}
		httpclient.close();
	}
	
	
	
	
	
	
	
	
	
	
}//�����������




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
	
