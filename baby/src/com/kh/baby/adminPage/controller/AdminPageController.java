package com.kh.baby.adminPage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.baby.adminPage.model.service.AdminPageService;
import com.kh.baby.adminPage.model.vo.AdminPage;
import com.kh.baby.adminPage.model.vo.Attachment;
import com.kh.baby.adminPage.model.vo.PageInfo;
import com.kh.baby.adminPage.model.vo.RequestBoard;
import com.kh.baby.adminPage.model.vo.Hospital;
import com.kh.baby.member.model.vo.Member;

@WebServlet("/adminPage/*")
public class AdminPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		String uri = request.getRequestURI();

		String contextPath = request.getContextPath();

		String command = uri.substring((contextPath + "/adminPage").length());

		String path = null;
		RequestDispatcher view = null;

		String status = null;
		String msg = null;
		String errorMsg = " 과정에서 오류가 발생했습니다.";

		try {
			AdminPageService service = new AdminPageService();

			// 신고게시판 리스트 조회
			if (command.equals("/reportBoard.do")) {
				errorMsg = "신고게시판 리스트 조회" + errorMsg;

				String currentPage = request.getParameter("cp");
				String boardStatus = "D";

				// 페이징 처리를 위한 데이터를 계산하는 Service 호출
				PageInfo pInfo = service.getPageInfo(currentPage, boardStatus);

				// 현재 페이지와 한 페이지에 보여질 게시글 수를 이용하여 게시글 목록에 보여져야 할 게시글만 list 형태로 조회하기
				List<AdminPage> list = service.selectList(pInfo, boardStatus);

				// 썸네일 이미지 목록 조회
				List<Attachment> fList = service.selectFileList(pInfo, boardStatus);

				request.setAttribute("pInfo", pInfo);
				request.setAttribute("reportBoard", list);
				request.setAttribute("fList", fList);

				path = "/WEB-INF/views/adminPage/reportBoard.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 신고게시글 등록
			}else if(command.equals("/reportInsert.do")) {
				errorMsg = "신고 게시글 등록" + errorMsg;
				
				String boardtype= request.getParameter("type");
				int reason= Integer.parseInt(request.getParameter("report"));
				int boardNo= Integer.parseInt(request.getParameter("boardNo"));
				
				int result = service.insertReport(boardNo, reason);
				
				if(result > 0) {
					status="success";
		            msg = "신고 게시글 등록 성공";
		            
		            if(boardtype.equals("3")) {
		            	response.sendRedirect(request.getContextPath()+"/community/freeView.do?type=3&cp=1&no="+boardNo);
		            }else if(boardtype.equals("4")) {
		            	response.sendRedirect(request.getContextPath()+"/community/knowledgeView.do?type=4&cp=1&no="+boardNo);
		            }
				} else {
					 status = "error";
		             msg = "신고 게시글 등록 실패";
		             path =request.getHeader("referer");
				}	
				  request.getSession().setAttribute("status", status);
		          request.getSession().setAttribute("msg", msg);
	
				// 요청게시판 리스트 조회
			} else if (command.equals("/requestBoard.do")) {
				errorMsg = "요청게시판 리스트 조회" + errorMsg;

				String currentPage = request.getParameter("cp");
				String boardStatus = "R";

				PageInfo pInfo = service.getPageInfo(currentPage);

				List<RequestBoard> list = service.selectList(pInfo);

				List<Attachment> fList = service.selectFileList(pInfo, boardStatus);

				request.setAttribute("pInfo", pInfo);
				request.setAttribute("fList", fList);

				request.setAttribute("requestBoard", list);
				path = "/WEB-INF/views/adminPage/requestBoard.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 공지사항 게시판 리스트 조회
			} else if (command.equals("/noticeBoard.do")) {
				errorMsg = "공지사항 게시판 리스트 조회" + errorMsg;

				String currentPage = request.getParameter("cp");
				String boardStatus = "Y";

				PageInfo pInfo = service.getPageInfo(currentPage, boardStatus);

				List<AdminPage> list = service.selectList(pInfo, boardStatus);

				List<Attachment> fList = service.selectFileList(pInfo, boardStatus);

				request.setAttribute("pInfo", pInfo);
				request.setAttribute("fList", fList);

				request.setAttribute("noticeBoard", list);
				path = "/WEB-INF/views/adminPage/noticeBoard.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 전체 회원 조회
			} else if (command.equals("/memberView.do")) {
				errorMsg = "전체 회원 조회 " + errorMsg;
				String type = request.getParameter("type");
				String currentPage = request.getParameter("cp");
				
				PageInfo pInfo = null;
				List<Member> list = null;

				// 조건별 회원 조회
				errorMsg = "조건별 회원 조회 " + errorMsg;

				pInfo = service.getPageInfoC(currentPage, type);
				list = service.selectListC(pInfo, type);
				
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("memberView", list);

				path = "/WEB-INF/views/adminPage/memberView.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 신고게시판 게시글 수 조회
			} else if (command.equals("/reportCount.do")) {
				errorMsg = "신고게시판 게시글 수 조회" + errorMsg;
				
				int count = service.reportBoard2();
				response.getWriter().print(count);

				// 요청게시판 게시글 수 조회
			} else if (command.equals("/requestCount.do")) {
				errorMsg = "요청게시판 게시글 수 조회" + errorMsg;
				
				int count = service.requestBoard2();
				response.getWriter().print(count);

				// 신고게시글 상세조회
			} else if (command.equals("/reportView.do")) {
				errorMsg = "신고게시글 상세조회" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				String reason = request.getParameter("reason");
				
				AdminPage adminPage = service.selectBoard(boardNo);

				if (adminPage != null) {
					// 2. 게시글 조회 성공 시 이미지 조회
					request.setAttribute("reason", reason);
					List<Attachment> fList = service.selectFiles(boardNo);
					if (!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
				}

				path = "/WEB-INF/views/adminPage/reportView.jsp";
				request.setAttribute("reportView", adminPage);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 요청게시글 상세조회
			} else if (command.equals("/requestView.do")) {
				errorMsg = "요청게시글 상세조회" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("no"));

				RequestBoard board = service.selectRequest(boardNo);

				if (board != null) {
					// 2. 게시글 조회 성공 시 이미지 조회
					List<Attachment> fList = service.selectFiles(boardNo);
					if (!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
				}

				path = "/WEB-INF/views/adminPage/requestView.jsp";
				request.setAttribute("requestView", board);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 공지게시글 상세조회
			} else if (command.equals("/noticeView.do")) {
				errorMsg = "공지게시글 상세조회" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("no"));

				AdminPage adminPage = service.selectBoard(boardNo);

				if (adminPage != null) {
					// 2. 게시글 조회 성공 시 이미지 조회
					List<Attachment> fList = service.selectFiles(boardNo);
					if (!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
				}

				path = "/WEB-INF/views/adminPage/noticeView.jsp";
				request.setAttribute("noticeView", adminPage);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 신고된 게시글 삭제
			} else if (command.equals("/deleteReport.do")) {
				errorMsg = "신고된 게시글 삭제" + errorMsg;

				int boardNo = Integer.parseInt(request.getParameter("no"));

				int result = service.deleteReport(boardNo);

				if (result > 0) {
					status = "success";
					path = "reportBoard.do";
				} else {
					status = "error";
					path = request.getHeader("referer"); // 이전 요청 주소
				}
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 신고된 게시글 복구
			} else if (command.equals("/returnReport.do")) {
				errorMsg = "신고된 게시글 복구" + errorMsg;

				int boardNo = Integer.parseInt(request.getParameter("no"));

				int result = service.returnReport(boardNo);

				if (result > 0) {
					status = "success";
					path = "reportBoard.do";
				} else {
					status = "error";
					path = request.getHeader("referer"); // 이전 요청 주소
				}
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 요청받은 게시글 수정 화면 화면 이동
			} else if (command.equals("/updateform.do")) {
				errorMsg = "요청받은 게시글 수정 화면 이동" + errorMsg;

				int requestNo = Integer.parseInt(request.getParameter("boardNo"));
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Hospital hospital = service.updateHos(boardNo);
				
				request.setAttribute("hospital", hospital);
				request.setAttribute("requestNo", requestNo);
				path = "/WEB-INF/views/adminPage/updateHos.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
				// 요청받은 게시글 수정 화면 화면 이동 -> 병원 정보 수정
			} else if (command.equals("/requestUpdate.do")) {
				errorMsg = "요청받은 병원 정보 수정" + errorMsg;

				String boardTitle = request.getParameter("titleInfo");
				String hosTime = request.getParameter("hosTime");
				String hosAddress = request.getParameter("hosAddress");
				String hosNightYN = request.getParameter("nitghYN");
				String hosWeekenYN = request.getParameter("weekYN");
				int requestNo = Integer.parseInt(request.getParameter("requestNo"));

				int boardNo = Integer.parseInt(request.getParameter("no"));

				Hospital hospital = new Hospital(boardNo, boardTitle, hosAddress, hosTime, hosNightYN, hosWeekenYN);

				int result = service.updateHosBoard(hospital);

				if (result > 0) {
					service.deleteRequest(requestNo);
					path = "requestBoard.do";
					status = "success";
					msg="병원 정보 수정 성공";
				} else {
					status = "error";
					msg="병원 정보 수정 실패";
					path = request.getHeader("referer");
				}

				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);

				// 요청받은 게시글 등록 화면 이동
			} else if (command.equals("/insertform.do")) {
				errorMsg = "요청받은 게시글 등록 화면 이동" + errorMsg;
				
				int type = Integer.parseInt(request.getParameter("type"));
				int requestNo = Integer.parseInt(request.getParameter("boardNo"));
				String content = request.getParameter("content");
				String array[] = content.split(",");
				String time = "09:00 ~ 18:00";

				if(type==1) {
					request.setAttribute("requestNo", requestNo);
					request.setAttribute("name", array[0].trim());
					request.setAttribute("address", array[1].trim());
					request.setAttribute("content", array[2].trim());
					request.setAttribute("time", time);
					path = "/WEB-INF/views/adminPage/insertHos.jsp";
				}else {
					path = "/WEB-INF/views/adminPage/adminWrite.jsp";
				}
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
				// 요청받은 게시글 등록 화면 이동 -> 병원게시글 등록
			} else if (command.equals("/insertHos.do")) {
				errorMsg = "병원게시글 등록" + errorMsg;

				int requestNo = Integer.parseInt(request.getParameter("no"));
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				String hosTime = request.getParameter("hosTime");
				String hosAddress = request.getParameter("hosAddress");
				String nitghYN = request.getParameter("nitghYN");
				String weekYN = request.getParameter("weekYN");
				
				AdminPage board = new AdminPage(title,content,1);
				
				Hospital hospital = new Hospital(hosAddress, hosTime, nitghYN, weekYN, title);
				
				int result = service.insertHos(board, hospital);

				if (result > 0) {
					service.deleteRequest(requestNo);
					status = "success";
					msg="병원 게시글 등록 성공";
					path = "requestBoard.do";
				} else {
					status = "error";
					msg="병원 게시글 등록 실패";
					path = request.getHeader("referer");
				}
				  
		        request.getSession().setAttribute("msg", msg);
				request.getSession().setAttribute("status", status);
				response.sendRedirect(path);

				// 요청받은 게시글 삭제
			} else if (command.equals("/checkRequest.do")) {
				errorMsg = "요청받은 게시글 삭제" + errorMsg;

				int boardNo = Integer.parseInt(request.getParameter("boardNo"));
				int parentNo = Integer.parseInt(request.getParameter("no"));

				int result = service.checkRequest(parentNo);

				if (result > 0) {
					result = service.deleteRequest(boardNo);
					status = "success";
					msg="요청게시글 삭제 성공";
					path = "requestBoard.do";
				} else {
					status = "error";
					msg="요청게시글 삭제 실패";
					path = request.getHeader("referer"); // 이전 요청 주소
				}
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 요청 처리 완료된 게시글 요청게시판에서 삭제
			} else if (command.equals("/deleteRequest.do")) {
				errorMsg = "요청 처리 완료된 게시글 요청게시판에서 삭제" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("boardNo"));
				service.deleteRequest(boardNo);

				path = "requestBoard.do";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 공지사항 삭제
			} else if (command.equals("/deleteNotice.do")) {
				errorMsg = "공지사항 삭제" + errorMsg;
				int boardNo = Integer.parseInt(request.getParameter("boardNo"));
				int result = service.deleteNotice(boardNo);
				
				if(result>0) {
					status = "success";
		             msg = "공지사항 삭제 성공";
					path = "noticeBoard.do";
				}else {
					 status = "error";
		             msg = "공지사항 삭제 실패";
		             path =request.getHeader("referer");
				}	
				  request.getSession().setAttribute("status", status);
		          request.getSession().setAttribute("msg", msg);
		          
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			
				// 신고게시판, 공지사항 게시글 검색
			} else if (command.equals("/search.do")) {
				errorMsg = "게시글 검색" + errorMsg;
				
				String searchKey = request.getParameter("searchKey");
				String searchValue = request.getParameter("searchValue");

				String boardType = request.getParameter("type");
				String currentPage = request.getParameter("cp");

				if (boardType.equals("R")) {
					PageInfo pInfo = service.getPageInfoR(currentPage, boardType, searchValue);

					List<RequestBoard> list = service.selectSearchR(pInfo, searchValue);

					request.setAttribute("requestBoard", list);
					request.setAttribute("pInfo", pInfo);
					path = "/WEB-INF/views/adminPage/requestBoard.jsp";
				} else {
					PageInfo pInfo = service.getPageInfo(currentPage, boardType, searchKey, searchValue);
					List<AdminPage> list = service.selectSearch(pInfo, searchValue, searchKey);
					if (boardType.equals("D")) {
						request.setAttribute("reportBoard", list);
						request.setAttribute("pInfo", pInfo);
						path = "/WEB-INF/views/adminPage/reportBoard.jsp";
					} else if (boardType.equals("Y")) {
						request.setAttribute("noticeBoard", list);
						request.setAttribute("pInfo", pInfo);
						path = "/WEB-INF/views/adminPage/noticeBoard.jsp";
					}
				}

				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 회원 조회 게시글 검색
				} else if (command.equals("/searchM.do")) {
					errorMsg = "회원 조회 게시글 검색" + errorMsg;
					
					String searchKey = request.getParameter("searchKey");
					String searchValue = request.getParameter("searchValue");

					String currentPage = request.getParameter("cp");

					PageInfo pInfo = service.getPageInfoM(currentPage, searchKey, searchValue);
					List<Member> list = service.selectSearchM(pInfo, searchValue, searchKey);
					
					request.setAttribute("memberView", list);
					request.setAttribute("pInfo", pInfo);
					path = "/WEB-INF/views/adminPage/memberView.jsp";
					
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				// 관리자 글쓰기 메뉴 조회
			} else if (command.equals("/adminWrite.do")) {
				errorMsg = "관리자 글쓰기 " + errorMsg;
				
				int type =Integer.parseInt(request.getParameter("type"));
				if(type==0) {
					path = "/WEB-INF/views/adminPage/insertNotice.jsp";					
				}else if(type==1) {
					String time = "09:00 ~ 18:00";
					request.setAttribute("time", time);
					path = "/WEB-INF/views/adminPage/insertHospital.jsp";	
				}else if(type==2) {
					path = "/WEB-INF/views/adminPage/insertKnow.jsp";	
				}
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

				// 관리자 글쓰기 -> 상식게시판 글 작성
			} else if(command.equals("/insertKnow.do")) {
				errorMsg = "상식게시판 글 작성" + errorMsg;
				
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				int select = Integer.parseInt(request.getParameter("select"));
			
				AdminPage adminpage = new AdminPage(title, content, 2);

				int result = service.insertKnow(adminpage, select);
						
				if(result>0) {
					int boardNo = service.selectBoardNo();
					
					if (boardNo > 0) {
						AdminPage board = service.selectBoard(boardNo);
						request.setAttribute("board", board);
						
						response.sendRedirect(request.getContextPath()+"/board/knowledgeView.do?type=2&cp=1&no="+boardNo);
					} else {
						path = request.getHeader("referer");
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					}		
				}			
			
				// 관리자 글쓰기 -> 병원게시판 글 작성
			} else if(command.equals("/insertHospital.do")) {
				errorMsg = "병원게시판 글 작성" + errorMsg;

				String title = request.getParameter("title");
				String hosTime = request.getParameter("hosTime");
				String hosAddress = request.getParameter("hosAddress");
				String nitghYN = request.getParameter("nitghYN");
				String weekYN = request.getParameter("weekYN");
				
				AdminPage board = new AdminPage(title,title,1);
				
				Hospital hospital = new Hospital(hosAddress, hosTime, nitghYN, weekYN, title);
				
				int result = service.insertHos(board, hospital);

				if (result > 0) {
					int boardNo = service.selectBoardNo();
					if (boardNo > 0) {
						AdminPage board2 = service.selectBoard(boardNo);
						request.setAttribute("board", board2);
						
						response.sendRedirect(request.getContextPath()+"/board/hospitalView.do?type=1&cp=1&no="+boardNo);
					} else {
						path = request.getHeader("referer");
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					}		
				}	
				
				// 관리자 글쓰기 -> 공지사항 글 작성
			}else if(command.equals("/insertNotice.do")) {
				errorMsg = "공지사항 글 작성" + errorMsg;
				
				String title = request.getParameter("title");
				String content = request.getParameter("content");
								
				int type = Integer.parseInt(request.getParameter("type"));
			
				int result = service.insertNotice(title, content, type);

				if (result > 0) {
					int boardNo = service.selectBoardNo();
					if (boardNo > 0) {
						status="success";
			            msg = "공지사항 입력 성공";
						AdminPage board = service.selectBoard(boardNo);
						request.setAttribute("noticeView", board);
						
						path = "/WEB-INF/views/adminPage/noticeView.jsp";
					} else {
						 status = "error";
			             msg = "공지사항 입력 실패";
						path = request.getHeader("referer");
					}	

					request.getSession().setAttribute("status", status);
			        request.getSession().setAttribute("msg", msg);
			        
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				}		
			}
		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";

			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
