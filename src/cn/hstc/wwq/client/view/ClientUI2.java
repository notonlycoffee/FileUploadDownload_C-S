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


	private JLabel banner = new JLabel("XXX文件管理系统", JLabel.CENTER);
	private JLabel welcomeword = new JLabel("欢迎使用XX文件管理系统", JLabel.CENTER);
	private JButton upfilebutton = new JButton("上传文件");
	private JButton checkfilebutton = new JButton("查看文件");

	private JLabel successupload = new JLabel("上传成功!!!", JLabel.CENTER);
	private JLabel failupload = new JLabel("上传失败!!!", JLabel.CENTER);

	private JLabel successchange = new JLabel("修改成功!!!", JLabel.CENTER);
	private JLabel failchange = new JLabel("修改失败!!!", JLabel.CENTER);

	private JLabel successdelete = new JLabel("删除成功!!!", JLabel.CENTER);
	private JLabel faildelete = new JLabel("删除失败!!!", JLabel.CENTER);
	
	private JLabel successdownload = new JLabel("下载成功!!!", JLabel.CENTER);
	private JLabel faildownload = new JLabel("下载失败!!!", JLabel.CENTER);

	private JPanel containsAll = new JPanel(); // 总体的布局,包括banner,左边,右边的卡片布局
	private JPanel leftoperation = new JPanel(); // 左边的操作布局
	private JPanel cardPanel = new JPanel(); // 卡片布局的容器,装入5个界面

	private Border lineBorder = new LineBorder(Color.BLACK, 1);// 设置边框

	private CardLayout card = new CardLayout();
	
	private JLabel jl = new JLabel("文件编码:");
	private JTextField jtf = new JTextField(5);//文件编码输入框
	private JButton changeButton = new JButton("修改");
	private JButton deleteButton = new JButton("删除");
	private JButton downloadButton = new JButton("下载");

	private static final String DISPLAYWELCOME = "welcome"; // 卡片容器的标志
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

	// 文件上传需要的组件
	private JLabel userNameLabel = new JLabel("上传用户");
	private JTextField userNameInput = new JTextField(10);
	private JLabel bank = new JLabel("      ");
	private JButton uploadButton = new JButton("上传文件");
	private JLabel descriptionLabel = new JLabel("文件描述");
	private JTextArea descriptionjtext = new JTextArea(8, 30);
	private JButton submitButton = new JButton("提交");
	
	
	
	//文件修改需要的组件
	private JLabel changeUserNameLabel = new JLabel("上传用户");
	private JTextField changeUserNameInput = new JTextField(10);
	private JLabel changeBank = new JLabel("      ");
	private JLabel chagenFileNameLabel = new JLabel("文件名字");
	private JTextField changeFileNameInput = new JTextField(10);
	private JLabel changeDescriptionLabel = new JLabel("文件描述");
	private JTextArea changeDescriptionjtext = new JTextArea(8, 30);
	private JButton changeSubmitButton = new JButton("修改");
	
	
	private Map filemap = new HashMap();
	
	public ClientUI2() throws Exception {

		// 设置字体样式
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

		// 设置边框
		leftoperation.setBorder(lineBorder);
		cardPanel.setBorder(lineBorder);
		banner.setBorder(new TitledBorder("by_wwq"));

		// 给左边的内容设置样式
		leftoperation.setPreferredSize(new Dimension(100, 450));
		leftoperation.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
		upfilebutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		checkfilebutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		changeSubmitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// 给左边加入组件
		leftoperation.add(upfilebutton);
		leftoperation.add(checkfilebutton);

		// 文件上传页面
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

		
		//文件修改页面
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
		
//		private JLabel changeUserNameLabel = new JLabel("上传用户");
//		private JTextField changeUserNameInput = new JTextField(10);
//		private JLabel changeBank = new JLabel("      ");
//		private JLabel chagenFileNameLabel = new JLabel("文件名字");
//		private JTextField changeFileNameInput = new JTextField(10);
//		private JLabel changeDescriptionLabel = new JLabel("文件描述");
//		private JTextArea changeDescriptionjtext = new JTextArea(8, 30);
//		private JButton changeSubmitButton = new JButton("修改");
		
		
		
		//文件列表页面
		JPanel filelist = new JPanel();
		JPanel filelistinside = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 59));
//		filelistinside.add(new JLabel("文件列表"));
//		filelist.add(filelistinside);
		
		
		String url = "http://127.0.0.1:8080/fileoperation/servlet/ListFileServlet";
		filemap = getAllFile(url);
		JTable tbl = createTable(map2array(filemap));
		
		JScrollPane jp = new JScrollPane(tbl);
		jp.setPreferredSize(new Dimension(620,400));
		
		filelistinside = tableinpanel(jp);
		
		filelist.add(filelistinside);
		
		// 修改文件信息
		// JPanel changefilejpanel = new JPanel();
		// changefilejpanel.add(p_file);

		// 给卡片容器设置样式
		cardPanel.setLayout(card);
		cardPanel.setPreferredSize(new Dimension(630, 450));

		// 往卡片容器加入组件
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

		// 设置总体容器的布局,并且加入头部,左部和主体内容
		containsAll.setLayout(new BorderLayout());
		containsAll.add(banner, BorderLayout.NORTH);
		containsAll.add(leftoperation, BorderLayout.WEST);
		containsAll.add(cardPanel, BorderLayout.EAST);

		// 注册监听器
		upfilebutton.addActionListener(new ClickListener());
		uploadButton.addActionListener(new ClickListener());
		submitButton.addActionListener(new ClickListener());
		checkfilebutton.addActionListener(new ClickListener());
		changeButton.addActionListener(new ClickListener());
		deleteButton.addActionListener(new ClickListener());
		downloadButton.addActionListener(new ClickListener());
		changeSubmitButton.addActionListener(new ClickListener());
		
		this.add(containsAll);
		this.setTitle("文件上传与管理");
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
		
//		private JLabel jl = new JLabel("文件编码:");
//		private JTextField jtf = new JTextField();//文件编码输入框
//		private JButton changeButton = new JButton("修改");
//		private JButton deleteButton = new JButton("删除");
		
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
				System.out.println("这里处理文件上传");

				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("请选择要上传的文件...");
				fc.setApproveButtonText("确定");
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
				//这里是修改文件信息的页面
				try {
					//修改文件信息
					String filenum = jtf.getText().trim();
					int num = Integer.parseInt(filenum);
					if(num <= 0 || num > filemap.size()-1){
						JOptionPane.showMessageDialog(null, "必须是规定的文件编码号", "友情提示",JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					//接下来处理文件的修改业务
					//
					filenum = (filemap.size()-num) + "";
					Map map = (Map) filemap.get(filenum);
					
					String id = (String) map.get("id");
					System.out.println(id);
					//数据回显
					String username = (String) map.get("3");
					String filename = (String) map.get("0");
					String description = (String) map.get("2");
					
					changeUserNameInput.setText(username);
					changeFileNameInput.setText(filename);
					changeDescriptionjtext.setText(description);
					
					card.show(cardPanel, ClientUI2.CHANGEFILE);

				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "必须是规定的文件编码号", "友情提示",JOptionPane.ERROR_MESSAGE);
					card.show(cardPanel, ClientUI2.FAILCHANGE);
				}
			}
			
			if(e.getSource() == changeSubmitButton) {
				try {
					System.out.println("这里就是修改文件的按钮");
					
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
				//这里是删除文件的页面
				try {
					String filenum = jtf.getText().trim();
					int num = Integer.parseInt(filenum);
					if(num <= 0 || num > filemap.size()-1) {
						JOptionPane.showMessageDialog(null, "必须是规定的文件编码号", "友情提示",JOptionPane.ERROR_MESSAGE);
						return ;
					}
					//接下来处理文件的删除业务
					filenum = (filemap.size()-num) + "";
					Map map = (Map) filemap.get(filenum);
					String id = (String) map.get("id");
					
					deletefile(id);
					card.show(cardPanel, ClientUI2.SUCCESSDELETE);
					
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "必须是规定的文件编码号", "友情提示",JOptionPane.ERROR_MESSAGE);
					card.show(cardPanel, ClientUI2.FAILDELETE);
				}
			}
			
			if( e.getSource() == downloadButton) {
				//这里处理文件下载问题
				try {
					//下载文件信息
					String filenum = jtf.getText().trim();
					int num = Integer.parseInt(filenum);
					if(num <= 0 || num > filemap.size()-1)
						JOptionPane.showMessageDialog(null, "必须是规定的文件编码号", "友情提示",JOptionPane.ERROR_MESSAGE);
					
					//接下来处理文件的下载业务
					filenum = (filemap.size()-num) + "";
					Map map = (Map) filemap.get(filenum);
					String id = (String) map.get("id");
					
					String destFileName = (String) map.get("0");
					
					destFileName = "C:\\\\" + destFileName;
					
					ClientUI2.downFile(id, destFileName);
					card.show(cardPanel, ClientUI2.SUCCESSDOWNLOAD);
					JOptionPane.showMessageDialog(null, "文件下载默认路径:c盘目录下"); 
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "必须是规定的文件编码号", "友情提示",JOptionPane.ERROR_MESSAGE);
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
			    map = TestListFile.parsehtml(html);//对获得的html页面进行筛选
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
		Map map = new LinkedHashMap();  //最终的map集合,存放时是表头信息,以及每个文件的具体信息;
		Map headMap = new LinkedHashMap();  //存放表头的信息,包括	文件名字	上传时间	文件描述	上传者	操作
		html = html.replaceAll("(\r*)(\n*)(\t*)", "");
		Pattern p = Pattern.compile("(<table border=\"1\">)(.*)(</table>)");
		Matcher m = p.matcher(html);
		if( m.find()) {
			String str = m.group(2);
			//去除<tr> <td> <td style....>
			str = str.replaceAll("<tr>|<td>|<td style=\"width:200px;\">", "");
			
			String [] strc = str.split("</table>");
//			System.out.println(strc.length);
			
			str = strc[0];
			//对每一项分组 每一项大概是:			文件名字</td>上传时间</td>文件描述</td>上传者</td>操作</td>
			String [] strarray = str.split("</tr>");  //strarray[0]是    文件名字</td>上传时间</td>文件描述</td>上传者</td>操作</td>
//strarray[1]开始是
//logo4-04.png</td>2015-05-19 09:08:14.0</td>这个是最新的</td>最新的</td><a href="servlet/DownLoadServlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">下载</a><a href="servlet/ChagenFileServlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">修改文件</a><a href="servlet/DeleteFileservlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5">删除</a></td>
			//获取表头
			String [] tablehead = strarray[0].split("</td>");
//			文件名字
//			上传时间
//			文件描述
//			上传者
//			操作
			headMap.put("filename", tablehead[0]);
			headMap.put("uptime", tablehead[1]);
			headMap.put("description", tablehead[2]);
			headMap.put("username", tablehead[3]);
			headMap.put("operation", tablehead[4]);
			map.put("tablehead", headMap);
			//下面是操作文件的具体信息,是文件的具体文件名,上传时间等
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
				
				
				p = Pattern.compile("(.*)(=)(.*)(\">下)(.*)");
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
	    		   
	               "文件编号 文件名 上传时间 文件描述 上传者".split(" ")
	               
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
			filearray[length-1-i][0] = (String) map.keySet().toArray()[i+1];  //文件编号
			
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
		// 生成一个httpclient对象
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
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
			}
			fout.flush();
			fout.close();
		} finally {
			// 关闭低层流。
			in.close();
		}
		httpclient.close();
	}
	
	
	
	
	
	
	
	
	
	
}//类在这里结束




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
	
