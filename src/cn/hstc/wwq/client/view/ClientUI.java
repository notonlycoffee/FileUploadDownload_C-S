package cn.hstc.wwq.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



public class ClientUI extends JFrame {

	
	private JLabel systemName = new JLabel("xxx文件管理系统",JLabel.CENTER);
	private JButton uploadFileButton = new JButton("上传文件");
	private JButton checkFileButton = new JButton("查看文件");
	private JLabel content = new JLabel("欢迎使用xxxx文件管理系统",JLabel.CENTER);
	
	
	private JLabel userNameLabel = new JLabel("上传用户");
	private JTextField userNameInput = new JTextField(10);
	private JLabel bank = new JLabel("      ");
	private JButton uploadButton = new JButton("上传文件");
	private JLabel descriptionLabel = new JLabel("文件描述");
	private JTextArea description = new JTextArea(8,30);
	private JButton submitButton = new JButton("提交");
	
	private String filepath = null;
	
	private JPanel p2 = new JPanel(); 
	private Border lineBorder = new LineBorder(Color.BLACK, 1);
	public ClientUI() {
		systemName.setBorder(new TitledBorder("by_wwq"));
		systemName.setFont(new Font("Dialog", 1, 50));
		
		content.setFont(new Font("Dialog", 1, 30));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 30)); //左边的侧栏
		uploadFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		checkFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p1.add(uploadFileButton);
		p1.add(checkFileButton);
		p1.setBorder(lineBorder);
		p1.setPreferredSize(new Dimension(100, 450));
		
		//右边主体内容
		p2.add(content);
		p2.setPreferredSize(new Dimension(600,450));
		p2.setBorder(lineBorder);
		
		//将p1 和 p2 加入到p3
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		p3.add(p1);
		p3.add(p2);
		
		
		this.setLayout(new BorderLayout());
		this.add(systemName, BorderLayout.NORTH);
		this.add(p3,BorderLayout.CENTER);
		
		uploadFileButton.addActionListener(new Listener());
		checkFileButton.addActionListener(new Listener());
		uploadButton.addActionListener(new Listener());
		submitButton.addActionListener(new Listener());
		
		this.setTitle("文件上传与管理");
		this.setSize(750, 610);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);


	}

	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
//			ipcontent = sevadjtf.getText().trim();
//			portcontent = sevpojtf.getText().trim();
//			requestcontent = reqword.getText().trim();
//			if (ipcontent.equals("10.10.10.64") && portcontent.equals("2000")) {
//				try {
//					toServer.writeUTF(requestcontent);
//					toServer.flush();
//
//					String fromServerContentString = fromServer.readUTF();
//					resword.setText(fromServerContentString);
//
//				} catch (IOException e1) {
//					throw new RuntimeException(e1);
//				}
//			}
			
			if(e.getSource() == uploadFileButton) {
				//这里是上传文件的
				
				content.hide();
				
				p2.setLayout(new BorderLayout());
				
				userNameLabel.setBorder(lineBorder);
				descriptionLabel.setBorder(lineBorder);
				
				JPanel p_file = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 30));
				
				p_file.setPreferredSize(new Dimension(200,400));
				
				uploadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
				p_file.add(userNameLabel);
				p_file.add(userNameInput);
				p_file.add(uploadButton);
				p_file.add(bank);
				p_file.add(descriptionLabel);
				p_file.add(description);
				p_file.add(submitButton);
				p2.add(p_file,BorderLayout.CENTER);
			} else if(e.getSource() == uploadButton) {
				
				System.out.println("这里处理文件上传");
				
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("请选择要上传的文件...");
				fc.setApproveButtonText("确定");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(p2)) {
					filepath=fc.getSelectedFile().getPath();
				}
				
			} else if(e.getSource() == submitButton) {
				
				try {
				HttpClient httpclient = new DefaultHttpClient();  
		        HttpPost post = new HttpPost("http://localhost:8080/day18_upload/servlet/ClientUpload");  
		        FileBody fileBody = new FileBody(new File(filepath));  
		        StringBody stringBody = new StringBody("文件的描述");  
		        MultipartEntity entity = new MultipartEntity();  
		        entity.addPart("file", fileBody);  
		        entity.addPart("desc", stringBody);  
		        post.setEntity(entity);  
		        HttpResponse response = httpclient.execute(post);  
		        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){    
		              
		            HttpEntity entitys = response.getEntity();  
		            if (entity != null) {  
		                System.out.println(entity.getContentLength());  
		                System.out.println(EntityUtils.toString(entitys));  
		            }  
		        }  
		        httpclient.getConnectionManager().shutdown();  
		      
				} catch(Exception ex) {
					throw new RuntimeException(ex);
				}
				
			} else if( e.getSource() == checkFileButton ) {
				System.out.println("查看文件");
			}
			
			
		}
	}
	
	public static void main(String[] args) {
		new ClientUI();

	}
}
