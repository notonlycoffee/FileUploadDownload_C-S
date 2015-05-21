package cn.hstc.wwq.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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

	private JPanel containsAll = new JPanel(); // 总体的布局,包括banner,左边,右边的卡片布局
	private JPanel leftoperation = new JPanel(); // 左边的操作布局
	private JPanel cardPanel = new JPanel(); // 卡片布局的容器,装入5个界面

	private Border lineBorder = new LineBorder(Color.BLACK, 1);// 设置边框

	private CardLayout card = new CardLayout();

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

	private String filepath = "";

	// 文件上传需要的组件
	private JLabel userNameLabel = new JLabel("上传用户");
	private JTextField userNameInput = new JTextField(10);
	private JLabel bank = new JLabel("      ");
	private JButton uploadButton = new JButton("上传文件");
	private JLabel descriptionLabel = new JLabel("文件描述");
	private JTextArea descriptionjtext = new JTextArea(8, 30);
	private JButton submitButton = new JButton("提交");

	public ClientUI2() {

		// 设置字体样式
		banner.setFont(new Font("Dialog", 1, 50));
		welcomeword.setFont(new Font("Dialog", 1, 30));
		successupload.setFont(new Font("Dialog", 1, 50));
		failupload.setFont(new Font("Dialog", 1, 50));

		successchange.setFont(new Font("Dialog", 1, 50));
		failchange.setFont(new Font("Dialog", 1, 50));

		successdelete.setFont(new Font("Dialog", 1, 50));
		faildelete.setFont(new Font("Dialog", 1, 50));

		// 设置边框
		leftoperation.setBorder(lineBorder);
		cardPanel.setBorder(lineBorder);
		banner.setBorder(new TitledBorder("by_wwq"));

		// 给左边的内容设置样式
		leftoperation.setPreferredSize(new Dimension(100, 450));
		leftoperation.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
		upfilebutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		checkfilebutton.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
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

		//文件列表页面
		JPanel filelist = new JPanel();
		JPanel filelistinside = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 59));
		filelistinside.add(new JLabel("文件列表"));
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
		// cardPanel.add(this.CHANGEFILE, );
		cardPanel.add(this.SUCCESSCHANGE, successchange);
		cardPanel.add(this.FAILCHANGE, failchange);
		cardPanel.add(this.SUCCESSDELETE, successdelete);
		cardPanel.add(this.FAILDELETE, faildelete);

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

	public static void main(String[] args) {
		ClientUI2 ui = new ClientUI2();
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
				card.show(cardPanel, ClientUI2.FILELIST);
			}
			

		}
	}
	
	public List listAllFile() {
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

}
