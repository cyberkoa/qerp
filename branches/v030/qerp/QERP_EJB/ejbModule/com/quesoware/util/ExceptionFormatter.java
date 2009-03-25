package com.quesoware.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionFormatter {

	static public String toAString(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, false);
		t.printStackTrace(printWriter);
		printWriter.close();
		String s = stringWriter.getBuffer().toString();
		return s;
	}

}
