package cn.com.jnpc.email;

import java.util.ResourceBundle;

public class EmailConfigReader {

	private static final ResourceBundle rs = ResourceBundle.getBundle("email");

	public static String getValue(String key) {
		return rs.getString(key);
	}

	public static String getHost() {
		return getValue("HOST");
	}

	public static String getPort() {
		return getValue("PORT");
	}

	public static String getFromAddress() {
		return getValue("fromaddress");
	}

	public static String getPassword() {
		return getValue("password");
	}

	public static String getUsername() {
		return getValue("username");
	}

	public static Boolean getDebug() {
		String debug = getValue("debug");
		return Boolean.parseBoolean(debug);
	}

	public static String getContact() {
		return getValue("contact");
	}

	public static String getPhone() {
		return getValue("phone");
	}

	public static String getEmail() {
		return getValue("email");
	}

	public static String getContact1() {
		return getValue("contact1");
	}

	public static String getPhone1() {
		return getValue("phone1");
	}

	public static String getEmail1() {
		return getValue("email1");
	}
}
