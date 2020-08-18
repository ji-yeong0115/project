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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.baby.member.model.vo.Member;

@WebFilter(urlPatterns= {"/mypage/*","/member/myCompanyInfoForm.do", "/member/updateMemberForm.do", "/member/updatePwd.do", "/member/updateSellerForm.do","/member/signOutForm.do", "/mypage/replacePwd.do", "/adminPage/*"})
public class LoginFilter implements Filter {

    public LoginFilter() { }
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("LoginFilter 작동");

	} 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		
		HttpServletResponse res = (HttpServletResponse)response;

		HttpSession session = req.getSession();
		
		Member member = (Member)session.getAttribute("loginMember");
		if(member == null) {
			session.setAttribute("status", "error");
			session.setAttribute("msg", "로그인 후 이용 바랍니다.");
			res.sendRedirect(req.getContextPath());
		}else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {}
}
