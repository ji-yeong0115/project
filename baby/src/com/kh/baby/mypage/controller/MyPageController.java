package com.kh.baby.mypage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.baby.common.MyFileRenamePolicy;
import com.kh.baby.member.model.service.MemberService;
import com.kh.baby.member.model.vo.General;
import com.kh.baby.member.model.vo.Member;
import com.kh.baby.mypage.model.service.MyPageService;
import com.kh.baby.mypage.model.vo.Board;
import com.kh.baby.mypage.model.vo.Diary;
import com.kh.baby.mypage.model.vo.DiaryAttachment;
import com.kh.baby.mypage.model.vo.Grow;
import com.kh.baby.mypage.model.vo.Page;
import com.kh.baby.mypage.model.vo.PageInfo;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/mypage/*")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath + "/mypage").length());
		HttpSession session = request.getSession();
		String path = null;
		RequestDispatcher view = null;
		
		String status = null;
		String msg = null;
		String text = null;
		String errorMsg = "";
		
		try {
			MyPageService myPageService = new MyPageService();
			
			if(command.equals("/babyInfoForm.do")) {
				General loginGeneral = (General)session.getAttribute("loginGeneral");
				int month = getMonthsDifference(loginGeneral.getKidsBirth());
				Grow grow = myPageService.calGrow(month, loginGeneral.getKidsGender());
				
				request.setAttribute("grow", grow);
				path = "/WEB-INF/views/mypage/babyInfo_view.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);	
				
			}else if(command.equals("/updateBabyForm.do")) {
				path = "/WEB-INF/views/mypage/babyInfo_update.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);	
			}
			
			// ====== 육아일기 화면 전환 Form ======
			else if(command.equals("/babyDiaryForm.do")) {
				errorMsg = "육아일기 조회";
				
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				String currentPage = request.getParameter("cp");
				
				// 페이징 처리
				PageInfo pInfo = myPageService.getPageInfo(currentPage, memberNo);
				
				// 일기 내용 조회
				List<Diary> dList = myPageService.selectDiary(pInfo);
				
				List<DiaryAttachment> fList = new ArrayList<DiaryAttachment>();
				// 내용 조회 성공 시 이미지 조회 및 List 넘기기
				if(!dList.isEmpty()) {
					DiaryAttachment file = null;
					
					for(int i=0; i<dList.size(); i++) {
						int diaryNo = dList.get(i).getDiaryNo();
						file = myPageService.selectFile(diaryNo);
						fList.add(file);
					}
					
				}
				
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("fList", fList);
				request.setAttribute("dList", dList);
				
				path = "/WEB-INF/views/mypage/mypage_diary.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			// ====== 육아일기 등록 ======
			else if(command.equals("/insertDiary.do")) {
				errorMsg = "육아일기 등록";
				
				int maxSize = 1024 * 1024 * 10;
				
				String root = request.getSession().getServletContext().getRealPath("/");
				System.out.println(root);
				String filePath = root + "resources\\diaryImages";
				
				MultipartRequest mRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				// 육아일기 내용 얻어옴
				String diaryContent = mRequest.getParameter("diaryContent");
				
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				
				// 전달받은 데이터 Diary vo에 저장
				Diary diary = new Diary(diaryContent, memberNo);
				
				DiaryAttachment dFile = null;
				
				Enumeration<String> files = mRequest.getFileNames();
				
				if(files.hasMoreElements()) {
					String name = files.nextElement();
					
					if(mRequest.getFilesystemName(name) != null) {
						dFile = new DiaryAttachment();
						
						dFile.setFileOriginName(mRequest.getOriginalFileName(name));
						dFile.setFileChangeName(mRequest.getFilesystemName(name));
						
						dFile.setFileLevel(0);
						
						dFile.setFilePath(filePath);
					}
				}
				
				int result = myPageService.insertDiary(diary, dFile);
				
				if(result > 0) {
					status = "success";
					msg = "육아일기 등록 성공";
					path = "babyDiaryForm.do";
				} else {
					status = "error";
					msg = "육아일기 등록 실패";
					path = request.getHeader("referer");
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
			}
			
			// ====== 육아일기 삭제 ======
			else if(command.equals("/deleteDiary.do")) {
				errorMsg = "육아일기 삭제";
				
				int diaryNo = Integer.parseInt(request.getParameter("diaryNo"));
				String currentPage = request.getParameter("cp");
				
				int result = myPageService.deleteDiary(diaryNo);
				
				if(result > 0) {
					status = "success";
					msg = "게시글 삭제 성공";
					path = "babyDiaryForm.do?";
				} else {
					status = "error";
					msg = "게시글 삭제 실패";
					path = request.getHeader("referer");
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
			}
			
			// ====== 육아일기 수정 화면 전환 ======
			else if(command.equals("/updateDiaryForm.do")) {
				errorMsg = "육아일기 수정 화면 전환";
				
				int diaryNo = Integer.parseInt(request.getParameter("diaryNo"));
				String diaryContent = request.getParameter("diaryContent");
				String fileName = request.getParameter("fileName");
				
				Diary diary = new Diary(diaryNo, diaryContent);
				
				System.out.println(fileName);
				System.out.println(diary);
				
				request.getSession().setAttribute("diary", diary);
				request.getSession().setAttribute("fileName", fileName);
				
				path = "/WEB-INF/views/mypage/mypage_diary_update.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			else if(command.equals("/updateDiary.do")) {
				errorMsg = "육아일기 수정";
				
				int maxSize = 1024 * 1024 * 10;
				
				String root = request.getSession().getServletContext().getRealPath("/");
				
				String filePath = root + "resources\\diaryImages";
				
				MultipartRequest mRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				int diaryNo = Integer.parseInt(mRequest.getParameter("diaryNo"));
				String diaryContent = mRequest.getParameter("diaryContent");
				
				Diary diary = new Diary(diaryNo, diaryContent);
				
				DiaryAttachment dFile = null;
				
				Enumeration<String> files = mRequest.getFileNames();
				
				if(files.hasMoreElements()) {
					String name = files.nextElement();
					
					if(mRequest.getFilesystemName(name) != null) {
						dFile = new DiaryAttachment();
						
						dFile.setFileOriginName(mRequest.getOriginalFileName(name));
						dFile.setFileChangeName(mRequest.getFilesystemName(name));
						
						dFile.setFileLevel(0);
						
						dFile.setFilePath(filePath);
					}
				}
				
				int result = myPageService.updateDiary(diary, dFile);
				
				if(result > 0) {
					status = "success";
					msg = "육아일기 수정 성공";
					path = "babyDiaryForm.do";
				} else {
					status = "error";
					msg = "육아일기 수정 실패";
					path = request.getHeader("referer");
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
			}

			// ====== 비밀번호 변경 화면 전환 ======
			else if(command.equals("/replacePwdForm.do")) {
				errorMsg = "새로운 비밀번호 설정 화면 전환";
				
				path = "/WEB-INF/views/member/replacePwdForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ====== 비밀번호 변경 servlet ======
			else if(command.equals("/replacePwd.do")) {
				String currentPwd = request.getParameter("currentPwd");
				String newPwd = request.getParameter("newPwd1");
				String memberId = ((Member)session.getAttribute("loginMember")).getMemberId();
			
				Member member = new Member(memberId, currentPwd);
				System.out.println(member);
				System.out.println(newPwd);
				int result = new MyPageService().replacePwd(member, newPwd);
			

				if(result > 0) { // 비밀번호 변경 성공
					status = "success";
					msg = "비밀번호가 변경 되었습니다";
				}else if(result == 0) { // 비밀번호 변경 실패
					status = "error";
					msg = "비밀번호가 변경에 실패했습니다.";
				}else { // 현재 비밀번호 불일치
					status = "error";
					msg = "현재 비밀번호가 일치하지 않습니다.";
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				response.sendRedirect(request.getContextPath());
			}
			
			// 내가 작성한 글 화면 전환
			else if(command.equals("/myBoardList.do")) {
				errorMsg = "내가 작성한 글 화면 전환" + errorMsg;
				
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				String currentPage = request.getParameter("cp");
				
				Page pInfo = myPageService.getMyPageInfo(currentPage, memberNo);
				List<Board> bList = myPageService.selectList(pInfo);

				request.setAttribute("pInfo", pInfo);
				request.setAttribute("bList", bList);
				
				path = "/WEB-INF/views/mypage/mypage_myboard.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 아기정보 수정 화면 전환
			else if(command.equals("/updateBabyInfoForm.do")) {
				errorMsg = "아기정보 수정 화면 전환";
				
				path = "/WEB-INF/views/mypage/updateBabyInfo.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 아기정보 수정 Servlet
			else if(command.equals("/updateBabyInfo.do")) {
				errorMsg = "아기정보 수정";
				
				General loginGeneral = (General)request.getSession().getAttribute("loginGeneral");
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				
				String kidsName = request.getParameter("kidsName");
//				String height = request.getParameter("kidsHeight");
//				String weight = request.getParameter("kidsWeight");
				
				double kidsHeight = Double.parseDouble(request.getParameter("kidsHeight")); 
				double kidsWeight = Double.parseDouble(request.getParameter("kidsWeight"));
				
//				if(!height.equals("") && !weight.equals("")) {
//					kidsHeight = Double.parseDouble(request.getParameter("kidsHeight")); 
//					kidsWeight = Double.parseDouble(request.getParameter("kidsWeight"));
//				}
				
				Member member = new Member(memberNo);
				General general = new General(kidsName, kidsHeight, kidsWeight);
				
				int result = new MyPageService().updateBabyInfo(member, general);
				
				if(result > 0 ) {
					status = "success";
					msg = "아기정보 수정 성공!";
					text = "아기정보를 성공적으로 수정했습니다.";
					
					general.setKidsBirth(loginGeneral.getKidsBirth());
					general.setKidsGender(loginGeneral.getKidsGender());
					session.setAttribute("loginGeneral", general);

				}else {
					status = "error";
					msg = "아기정보 수정 실패!";
					text = "아기정보 수정중 문제가 발생했습니다. 문제가 지속 될 경우 문의바랍니다.";
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				response.sendRedirect(request.getContextPath());
				

			}
			
			// 아기정보 입력 화면 전환 
			else if(command.equals("/insertBabyInfoForm.do")) {
				errorMsg = "아기정보 입력 화면 전환";
				
				path = "/WEB-INF/views/mypage/insertBabyInfoForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			// 아기정보 입력 Servlet
			else if(command.equals("/insertBabyInfo.do")) {
				errorMsg = "아기정보 입력";
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
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
				Member member = new Member(memberNo);
				General general = new General(kidsName, kidsGender, kidsBirth, kidsHeight, kidsWeight);

				int result = new MyPageService().insertBabyInfo(member, general);
			
				if(result > 0 ) {
					status = "success";
					msg = "아기정보 입력 성공!";
					text = "아기정보를 성공적으로 입력했습니다.";
			
					session.setAttribute("loginGeneral", general);

				}else {
					status = "error";
					msg = "아기정보 수정 실패!";
					text = "아기정보 입력중 문제가 발생했습니다. 문제가 지속 될 경우 문의바랍니다.";
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				session.setAttribute("text", text);
				
				response.sendRedirect(request.getContextPath());			
			}
			
			// 주소입력 화면 전환
			
			else if(command.equals("/updateAddressForm.do")) {
				errorMsg = "주소 입력 화면 전환";
				
				path = "/WEB-INF/views/mypage/updateAddressForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			else if(command.equals("/updateAddress.do")) {
				errorMsg = "주소 입력";
				
				Member loginMember = (Member)session.getAttribute("loginMember");
				//int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				String post = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				String memberAddress = address1 + "," + address2 + "," + post;
				
				Member member = new Member(memberAddress);
				member.setMemberNo(loginMember.getMemberNo());
				
				int result = new MyPageService().updateAddress(member);
				
				if(result > 0 ) {
					status = "success";
					msg = "주소 입력 성공!";
					text = "주소를 성공적으로 설정했했습니다.";
			
					member.setMemberId(loginMember.getMemberId());
					member.setMemberName(loginMember.getMemberName());
					member.setMemberNickname(loginMember.getMemberNickname());
					member.setMemberEmail(loginMember.getMemberEmail());
					member.setMemberGrade(loginMember.getMemberGrade());
		
					session.setAttribute("loginMember", member);

				}else {
					status = "error";
					msg = "주소 입력 실패!";
					text = "주소 설정중 문제가 발생했습니다. 문제가 지속 될 경우 문의바랍니다.";
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				session.setAttribute("text", text);
				
				response.sendRedirect(request.getContextPath());	
			}
			

		}catch(Exception e) {
			
			e.printStackTrace();
			
			path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg + "과정에서 오류가 발생했습니다.");
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	

	public int getMonthsDifference(String birth) { 
		int year = Integer.parseInt(birth.substring(0, 4));
		int month = Integer.parseInt(birth.substring(5, 7));
		
		Calendar cal = Calendar.getInstance();
		
		int month1 = year*12 + month;
		int month2 = cal.get(Calendar.YEAR)*12 + cal.get(Calendar.MONTH)+1;
		
		return month2 - month1;
	}

}