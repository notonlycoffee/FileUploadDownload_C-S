package Junit.test;

public class TestString {

	public static void main(String[] args) {
		
		String str = "D:\\java\\TomCat\\apache-tomcat-7.0.57-windows-x64\\apache-tomcat-7.0.57\\webapps\\fileoperation\\WEB-INF\\upload\\7\\15";
		int i = str.lastIndexOf("\\");
		str = str.substring(0, i);
		System.out.println(str);
		i = str.lastIndexOf("\\");
		str = str.substring(0, i);
		System.out.println(str);
		
		
	}
}
