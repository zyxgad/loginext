
package com.github.zyxgad.loginext.util;

import java.util.regex.Pattern;

public final class Regexp{
	private static Pattern USERNAME = Pattern.compile("^[A-Za-z_-][0-9A-Za-z_-]{3,31}$");
	public static boolean testUsername(String username){
		return USERNAME.matcher(username).matches();
	}
}