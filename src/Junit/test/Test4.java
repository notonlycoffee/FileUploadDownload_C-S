package Junit.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Test4 {

	public static void main(String[] args) {
		Map map = new HashMap();
		Map map1 = new HashMap();
		map1.put("1", "aaa");
		map.put("one", map1);
		
		
		Map map2 = (Map) map.get("one");
		String str = (String) map2.get("1");
		System.out.println(str);
		
	}
	
}
