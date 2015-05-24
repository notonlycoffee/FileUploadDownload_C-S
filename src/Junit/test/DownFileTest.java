package Junit.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DownFileTest {

	
	//这个是下载文件的代码..很重要的哦
	public static void main(String[] args) throws Exception {
		String url = "http://127.0.0.1:8080/fileoperation/servlet/DownLoadServlet?id=4ebd5c0a-9476-464d-af8d-0dc169ac24c5";
		String destFileName = "c:\\1.png";
		getFile(url, destFileName);
	}
	
	public static void getFile(String id, String destFileName)throws Exception {
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

}
