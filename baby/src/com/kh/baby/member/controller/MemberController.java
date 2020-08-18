package com.kh.baby.member.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.baby.board.model.service.BoardService;
import com.kh.baby.member.model.service.MemberService;
import com.kh.baby.member.model.vo.General;
import com.kh.baby.member.model.vo.Member;
import com.kh.baby.member.model.vo.Seller;
import com.kh.baby.sendEmail.naverMailSend;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath + "/member").length());
		
		HttpSession session = request.getSession();

		String path = null;
		RequestDispatcher view = null;
		
		String status = null;
		String msg = null;
		String text = null;
		String errorMsg = "";
		
		try {
			BoardService service = new BoardService();
			
			// ====== 로그인 Servlet ======
			if(command.equals("/login.do")) {
				errorMsg = "로그인" + errorMsg;
				
				String memberId = request.getParameter("inputId");
				String memberPwd = request.getParameter("inputPassword");
				
				Member member = new Member(memberId, memberPwd);
				
				Member loginMember = new MemberService().loginMember(member);
				
				if(loginMember != null) {
					session.setAttribute("loginMember", loginMember);
					
					if(loginMember.getMemberGrade().equals("G")) {
						General loginGeneral = new MemberService().loginGeneral(loginMember.getMemberNo());
						session.setAttribute("loginGeneral", loginGeneral);
					} else if(loginMember.getMemberGrade().equals("S")) {
						Seller loginSeller = new MemberService().loginSeller(loginMember.getMemberNo());

						session.setAttribute("loginSeller", loginSeller);
					}
					
					session.setMaxInactiveInterval(1800);
					
					String saveId = request.getParameter("saveId");
					Cookie cookie = new Cookie("saveId", memberId);
					
					if(saveId != null) {
						cookie.setMaxAge(60 * 60 * 24 * 7);
					} else {
						cookie.setMaxAge(0);
					}
					
					cookie.setPath("/");

					response.addCookie(cookie);
				} else {
					msg = "로그인 실패!";
					status = "error";
					text = "아이디 또는 비밀번호를 확인해주세요.";

					session.setAttribute("msg", msg);
					session.setAttribute("status", status);
					session.setAttribute("text", text);
				}
				response.sendRedirect(request.getHeader("referer"));
			}
			
			// ====== 로그아웃 Servlet ======
			else if(command.equals("/logout.do")) {
				errorMsg = "로그아웃" + errorMsg;
				
				request.getSession().invalidate();
				
				response.sendRedirect(request.getHeader("referer"));
			}
			
			// ====== 회원가입 select 이동 Servlet ======
			else if(command.equals("/signUpSelect.do")) {
				errorMsg = "회원가입 선택 페이지 이동" + errorMsg;
				
				path = "/WEB-INF/views/member/signUpSelect.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ====== General 회원가입 이동 Servlet ======
			else if(command.equals("/signUpGeneralForm.do")) {
				errorMsg = "일반회원 회원가입 페이지 이동" + errorMsg;
				
				path = "/WEB-INF/views/member/signUpGeneralForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			// ====== General 회원가입 Servlet ======
			else if(command.equals("/signUpGeneral.do")) {
				errorMsg = "일반회원 회원가입" + errorMsg;
				
				String memberId = request.getParameter("id");
				String memberPwd = request.getParameter("pwd1");
				String memberName = request.getParameter("name");
				String memberNickname = request.getParameter("nickname");		
				String memberEmail = request.getParameter("email");
				String post = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				String memberAddress = address1 + "," + address2 + "," + post;
				
				String kidsName = request.getParameter("kidsName");
				String kidsGender = request.getParameter("kidsGender");
				String kidsBirth = request.getParameter("kidsBirth");
				
				String height = request.getParameter("kidsHeight");
				String weight = request.getParameter("kidsWeight");
				
				double kidsHeight = 0;
				double kidsWeight = 0;
				
				if(!height.equals("") && !weight.equals("")) {
					kidsHeight = Double.parseDouble(request.getParameter("kidsHeight")); 
					kidsWeight = Double.parseDouble(request.getParameter("kidsWeight"));
				}
				Member member = new Member(memberId, memberPwd, memberName, memberNickname, memberEmail, memberAddress, "G");
				General general = new General(kidsName, kidsGender, kidsBirth, kidsHeight, kidsWeight);

				int result = new MemberService().signUpGeneral(member, general);

				if(result > 0) {
					status = "success";
					msg = "회원가입 성공!";
					text = "회원가입을 환영합니다.";
				} else {
					status = "error";
					msg = "회원가입 실패!";
					text = "회원가입 중 문제가 발생했습니다. 문제가 지속될 경우 문의바랍니다.";
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				session.setAttribute("text", text);
				
				response.sendRedirect(request.getContextPath());
			}
			
			// ====== General 회원가입 아이디 중복체크 Servlet ======
			else if(command.equals("/idDupCheck.do")){
				
				String id = request.getParameter("id");

				int result = new MemberService().idDupCheck(id);
				
				response.getWriter().print(result);
			}

			// ====== General 회원가입 이메일 중복체크 Servlet ======
			else if(command.equals("/emailDupCheck.do")) {
				
				String email = request.getParameter("email");
				
				int result = new MemberService().emailDupCheck(email);
				
				response.getWriter().print(result);
			}
			
			// ====== General 회원가입 닉네임 중복체크 Servlet ======
			else if(command.equals("/nicknameDupCheck.do")) {
				
				String nickname = request.getParameter("nickname");
				
				int result = new MemberService().nicknameDupCheck(nickname);
				
				response.getWriter().print(result);
			}
			// ====== 사업자 회원가입 사업자번호 중복체크 Servlet ======
			else if(command.equals("/companyNoDupCheck.do")) {
				
				String companyNo = request.getParameter("companyNo");
				
				int result = new MemberService().companyNumberDupCheck(companyNo);
				
				response.getWriter().print(result);
			}
			
			// ====== 사업자 회원가입 이동 Servlet ======
			else if(command.equals("/signUpSellerForm.do")) {
				errorMsg = "일반회원 회원가입 페이지 이동" + errorMsg;
				
				path = "/WEB-INF/views/member/signUpSellerForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ====== 사업자 회원가입 Servlet ======
			else if(command.equals("/signUpSeller.do")) {
				errorMsg = "사업자 회원가입" + errorMsg;

				String memberId = request.getParameter("id");
				String memberPwd = request.getParameter("pwd1");
				String memberName = request.getParameter("name");
				String memberNickname = request.getParameter("nickname");		
				String memberEmail = request.getParameter("email");
				String post = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				String memberAddress = address1 + "," + address2 + "," + post;

				String phone1 = request.getParameter("phone1");
				String phone2 = request.getParameter("phone2");
				String phone3 = request.getParameter("phone3");
				
				
				String companyNo = request.getParameter("companyNo");
				String companyPhone = phone1 + "-" + phone2 + "-" + phone3;
				
				
				Member member = new Member(memberId, memberPwd, memberName, memberNickname, memberEmail, memberAddress, "S");
				Seller seller = new Seller(companyNo, companyPhone);
				
				int result = new MemberService().signUpSeller(member, seller);
				
				if(result > 0) {
					status = "success";
					msg = "회원가입 성공!";
					text = "회원가입을 환영합니다.";
				} else {
					status = "error";
					msg = "회원가입 실패!";
					text = "회원가입 중 문제가 발생했습니다. 문제가 지속될 경우 문의바랍니다.";
				} 
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				session.setAttribute("text", text);
				
				response.sendRedirect(request.getContextPath());
			}
			
			// ====== 회원탈퇴 이동 Servlet ======

			else if(command.equals("/signOutForm.do")) {


				errorMsg = "회원탈퇴 페이지 이동" + errorMsg;
				
				path = "/WEB-INF/views/member/signOutForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ====== 회원탈퇴  Servlet ======
			else if(command.equals("/updateStatus.do")) {
				
				String currentPwd = request.getParameter("currentPwd");
				
				String memberId = ((Member)session.getAttribute("loginMember")).getMemberId();
				
				Member member = new Member(memberId, currentPwd);
				
				int result = new MemberService().signOut(member, currentPwd);

				
				if(result > 0 ) {
					status = "success";
					msg = "회원탈퇴 성공!";				
					session.removeAttribute("loginMember");
				
				}else {
					status = "error";
					msg = "회원 탈퇴 실패!";
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(request.getContextPath());
			}
			

			// ====== 개인정보수정 이동 Servlet ======
			else if(command.equals("/updateMemberForm.do")) {
				errorMsg = "개인정보수정 페이지 이동" + errorMsg;
				
				path = "/WEB-INF/views/member/updateMemberForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			
			else if(command.equals("/updateMember.do")) {
				errorMsg = "회원정보 수정" + errorMsg;
				
				Member loginMember = (Member)session.getAttribute("loginMember");
				
				String post = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				String memberAddress = address1 + "," + address2 + "," + post;
				
				String memberNickname = request.getParameter("nickname");
				
				// 아이디, 닉네임 , 주소
				Member member = new Member(loginMember.getMemberId(), memberNickname, memberAddress);
				member.setMemberNo(loginMember.getMemberNo());
			
					int result = new MemberService().updateMember(member);
					

					
					if(result > 0 ) {
						status = "success";
						msg = "회원정보 수정 성공!";
						text = "회원정보를 성공적으로 수정했습니다.";
						
						// 회원 정보가 수정된 경우 DB뿐만 아니라 Session도 갱신이 필요함.
						member.setMemberName(loginMember.getMemberName());
						member.setMemberGrade(loginMember.getMemberGrade());
						member.setMemberEmail(loginMember.getMemberEmail());
						
						session.setAttribute("loginMember", member);
						
						
					}else {
						status = "error";
						msg = "회원정보 수정 실패!";
						text = "회원정보 수정중 문제가 발생했습니다. 문제가 지속 될 경우 문의바랍니다.";
					}
					
					session.setAttribute("status", status);
					session.setAttribute("msg", msg);
					session.setAttribute("text", text);
					
					response.sendRedirect(request.getContextPath());
				
			}
			
			else if(command.equals("/myCompanyInfoForm.do")) {
				errorMsg = "나의회사정보 페이지 이동" + errorMsg;
				
				path = "/WEB-INF/views/member/myCompanyInfo.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			else if(command.equals("/updateSellerForm.do")) {
				errorMsg = "나의회사정보 페이지 이동" + errorMsg;
				
				path = "/WEB-INF/views/member/updateSellerForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			else if(command.equals("/updateSeller.do")) {
				errorMsg = "회사정보 수정 " + errorMsg;
				
				Member loginMember = (Member)session.getAttribute("loginMember");
				
				String post = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				String memberAddress = address1 + "," + address2 + "," + post;
				
				String memberNickname = request.getParameter("nickname");
				
				String phone1 = request.getParameter("phone1");
				String phone2 = request.getParameter("phone2");
				String phone3 = request.getParameter("phone3");
				String companyPhone = phone1 + "-" + phone2 + "-" + phone3;
				
				// 아이디, 닉네임 , 주소
				Member member = new Member(loginMember.getMemberId(), memberNickname, memberAddress);
				member.setMemberNo(loginMember.getMemberNo());
				
				// 전화번호
				Seller seller = new Seller(companyPhone);
				
				int result = new MemberService().updateSeller(member, seller);
				
				if(result > 0 ) {
					status = "success";
					msg = "회원정보 수정 성공!";
					text = "회원정보를 성공적으로 수정했습니다.";
					
					// 회원 정보가 수정된 경우 DB뿐만 아니라 Session도 갱신이 필요함.
					member.setMemberName(loginMember.getMemberName());
					member.setMemberGrade(loginMember.getMemberGrade());
					member.setMemberEmail(loginMember.getMemberEmail());
					Seller loginSeller = new MemberService().loginSeller(loginMember.getMemberNo());
					seller.setCompanyNo(loginSeller.getCompanyNo());
					
					session.setAttribute("loginMember", member);
					session.setAttribute("loginSeller", seller);
					
				}else {
					status = "error";
					msg = "회원정보 수정 실패!";
					text = "회원정보 수정중 문제가 발생했습니다. 문제가 지속 될 경우 문의바랍니다.";
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				session.setAttribute("text", text);
				
				response.sendRedirect(request.getContextPath());
				
				
			}

			// ====== ID/PWD 찾기 화면 전환 ======
			else if(command.equals("/searchSelectForm.do")) {
				errorMsg = "ID/PWD 찾기 화면 전환";
				
				path = "/WEB-INF/views/member/searchSelect2.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ====== 비밀번호 찾기 화면 전환 ======
			else if(command.equals("/searchPwdForm.do")) {
				errorMsg = "비밀번호 찾기 화면 전환";
				
				path = "/WEB-INF/views/member/searchPwd2.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ====== email 여부 확인 ======
			else if(command.equals("/selectEmail.do")) {
				errorMsg = "이메일 여부 확인";
				
				String email = request.getParameter("inputEmail");
				
				int result = new MemberService().selectEmail(email);
				
				response.getWriter().print(result);
			}
			
			// ====== email로 인증번호 보내기 ======
			else if(command.equals("/sendEmail.do")) {
				errorMsg = "이메일로 인증번호 보내기";
				
				String email = request.getParameter("inputEmail");
				int code = naverMailSend.naverMailSend(email);
				
				response.getWriter().print(code);
			}
			
			// ====== 인증번호 확인 후 새로운 비밀번호 설정 화면 전환 ======
			else if(command.equals("/updatePwdForm.do")) {
				errorMsg = "새로운 비밀번호 설정 화면 전환";
				String inputEmail = request.getParameter("inputEmail");
				session.setAttribute("inputEmail", inputEmail);

				path = "/WEB-INF/views/member/updatePwdForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ====== 새로운 비밀번호 설정 ======
			else if(command.equals("/updateNewPwd.do")) {
				String memberEmail = (String)session.getAttribute("inputEmail");
				String memberPwd = request.getParameter("pwd1");
				
				Member member = new Member();
				member.setMemberEmail(memberEmail);
				member.setMemberPwd(memberPwd);
				
				int result = new MemberService().updatePwd(member);
				
				if(result > 0 ) {
					status = "success";
					msg = "비밀번호 수정 성공!";
				}else {
					status = "error";
					msg = "비밀번호 수정 실패!";
					text = "회원정보 수정중 문제가 발생했습니다. 문제가 지속 될 경우 문의바랍니다.";
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				session.setAttribute("text", text);
				
				response.sendRedirect(request.getContextPath());
			}
			
			// ====== 아이디  찾기 ======
			else if(command.equals("/searchId.do")) {
				errorMsg = "아이디 찾기";
				
				String memberName = request.getParameter("inputName");
				String memberEmail = request.getParameter("inputEmail");
				
				Member member = new Member();
				member.setMemberName(memberName);
				member.setMemberEmail(memberEmail);
				
				String memberId = new MemberService().searchId(member);
				
				if(!memberId.equals("")) {
					String temp = "";
					for(int i=0; i<memberId.length()-4; i++) {
						temp += "*";
					}
					
					String searchId = memberId.substring(0, 2) + temp + memberId.substring(memberId.length()-2, memberId.length());
					status = "success";
					msg = "아이디 중 일부를 알려드립니다.";
					text = "ID : " + searchId;
				} else {
					status = "error";
					msg = "해당 정보의 아이디를 찾을 수 없습니다.";
					text = "";
				}
				
				session.setAttribute("msg", msg);
				session.setAttribute("status", status);
				session.setAttribute("text", text);
				response.sendRedirect(request.getContextPath());
			}
			
			// ====== 만 나이 계산 ======
			else if(command.equals("/calAge.do")) {
				int result = 0;
				
				int year = Integer.parseInt(request.getParameter("year"));
				int month = Integer.parseInt(request.getParameter("month"));
				int day = Integer.parseInt(request.getParameter("day"));
				
				Date date = new Date(); 
				
				SimpleDateFormat Tyear = new SimpleDateFormat("yyyy");
				SimpleDateFormat Tmd = new SimpleDateFormat("MMdd");
				
				int md = Integer.parseInt(month +""+ day);
				int ITyear = Integer.parseInt(Tyear.format(date));
				int ITmd = Integer.parseInt(Tmd.format(date));
				
				if(md < ITmd) {
					result = ITyear - year;
				} else {
					result = ITyear - year - 1;
				}
				System.out.println(result);
				response.getWriter().print(result);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			
			request.setAttribute("errorMsg", errorMsg + " 과정에서 오류가 발생했습니다.");
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

