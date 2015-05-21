package Junit.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test3 {
	public static void main(String[] args) throws Exception, IOException {
		Map map = new HashMap();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(
					"http://127.0.0.1:8080/fileoperation/servlet/ListFileServlet");

			CloseableHttpResponse response1 = httpclient.execute(httpGet);

			try {
//				System.out.println(response1.getStatusLine());
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
			    map = Test3.parsehtml(html);
			    
			    Map maphead = (Map) map.get("tablehead");
			    String value = (String) maphead.get("filename");
			    System.out.println(value);

				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}
	}

	public static Map parsehtml(String html) {
		Map map = new HashMap();
		Map headMap = new HashMap();
		html = html.replaceAll("(\r)*(\n)*(\t)", "");
//		System.out.println(html);
		Pattern p = Pattern.compile("(<table border=\"1\">)(.*)(</table>)");
		Matcher m = p.matcher(html);
		if( m.find()) {
			String str = m.group(2);
//			System.out.println("********************************************************************************************************");
//			System.out.println(str);
			
			//去除<tr> <td> <td style....>
			str = str.replaceAll("<tr>|<td>|<td style=\"width:200px;\">", "");
			
//			System.out.println(str);
			
			//对每一项分组 每一项大概是:			文件名字</td>上传时间</td>文件描述</td>上传者</td>操作</td>
			String [] strarray = str.split("</tr>");
			
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
			
			for(int i = 1 ; i < strarray.length; i++) {
				String [] content = strarray[i].split("</td>");
				Map strmap = new HashMap();
				for( int j = 0 ; j < content.length ; j++) {
					//System.out.println(content[j]);
					strmap.put("" + j, content[j]);
					//System.out.println(i+j);
				}
				
				map.put(""+i, strmap);
				
				//System.out.println("*****************************************");
			}
			
			
			
//			@Test
//			public void test5() {
//				Map map = new LinkedHashMap();
//				map.put("1", "aaa");
//				map.put("2", "bbb");
//				map.put("3", "ccc");
//				
//				for(Object obj:map.keySet()) {
//					String key = (String)obj;
//					String value = (String)map.get(key);
//					System.out.println(key + " = " + value);
//				}
//			}
	
			
			
			
			
			Map ma = (Map) map.get("1");
			String value = (String) ma.get("4");
			System.out.println(value);
			
		}
		else {
			System.out.println("no");
		}
		return map;
    }

}
