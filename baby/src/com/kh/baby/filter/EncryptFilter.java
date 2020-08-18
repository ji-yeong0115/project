package com.kh.baby.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.kh.baby.wrapper.EncryptWrapper;

@WebFilter(urlPatterns= {"/member/login.do", "/member/signUpGeneral.do", "/member/signUpSeller.do", "/member/updatePwd.do", "/member/updateStatus.do" ,"/member/updateNewPwd.do", "/mypage/replacePwd.do"})
public class EncryptFilter implements Filter {

    public EncryptFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hReqeust = (HttpServletRequest)request;
		
		EncryptWrapper encWrapper = new EncryptWrapper(hReqeust);
		
		chain.doFilter(encWrapper, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}