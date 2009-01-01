package com.quesofttech.util;

public class ClassUtil {

	static public String extractUnqualifiedName(Object obj) {
		Class cls = obj.getClass();
		return extractUnqualifiedName(cls);
	}

	static public String extractUnqualifiedName(Class cls) {
		String s = cls.getName();
		String[] s1 = s.split("\\.");
		return s1[s1.length - 1];
	}

}