package Junit.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class DeleteFile {
	
	
	public static void main(String[] args) throws Exception {
		try {
			String id = "7ab8497d-dcf0-4f92-a958-058d68e30f03";
			deletefile(id);
			System.out.println("delete success");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("delete fail");
		}
		
		
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
}
