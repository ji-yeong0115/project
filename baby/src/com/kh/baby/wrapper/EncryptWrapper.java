package com.kh.baby.wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncryptWrapper extends HttpServletRequestWrapper {

	public EncryptWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String encPwd = null;
		
		switch(name) {
		case "inputPassword" : case "memberPwd" : case "pwd1" : 
		case "currentPwd" : case "newPwd1" : 
			encPwd = getSha512(super.getParameter(name));
			break;
		default :
			encPwd = super.getParameter(name);
		}
		return encPwd;
	}
	
	/** SHA-512 해시 함수를 이용한 암호화 메소드
	 * @param memberPwd
	 * @return encPwd
	 */
	
	public static String getSha512(String memberPwd) {
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] bytes = memberPwd.getBytes(Charset.forName("UTF-8"));
		
		md.update(bytes);
		
		String encPwd = Base64.getEncoder().encodeToString(md.digest());

		return encPwd;
	}

}
