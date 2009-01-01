package com.quesofttech.util;

public class StringUtil {

	static public boolean isEmpty(String s) {
		if ((s == null) || (s.trim().length() == 0)) {
			return true;
		}
		else {
			return false;
		}
	}
	
}