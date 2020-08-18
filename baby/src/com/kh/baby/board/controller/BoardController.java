package com.kh.baby.board.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kh.baby.board.model.service.BoardService;
import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.BoardLike;
import com.kh.baby.board.model.vo.Page;
import com.kh.baby.board.model.vo.RequestBoard;
import com.kh.baby.member.model.vo.Member;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath + "/board").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		String status = null;
		String msg = null;
		String text = null;
		String errorMsg = "";
		
		try {
			BoardService service = new BoardService();
			
			String currentPage = request.getParameter("cp");			
			int boardType = Integer.parseInt(request.getParameter("type"));
			
			// ===== 정보공유방 : 병원 목록 조회 =====
			if(command.equals("/hospitalForm.do")) {
				
				errorMsg = "게시글 목록 조회" + errorMsg;
				
				Page pInfo = service.getPageInfo(currentPage, boardType);

				List<Board> bList = service.selectList(pInfo);
				
				path = "/WEB-INF/views/board/hospital_list.jsp";
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("bList", bList);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				

			}
			
			// ===== 정보공유방 : 병원 게시글 상세 조회 =====
			else if(command.equals("/hospitalView.do")) {
				
				errorMsg = "게시글 상세 조회" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = service.selectBoard(boardNo);	
				
				if(board != null) {	
					
					if(board.getBoardNotice().equals("Y")) {
						path = "/WEB-INF/views/board/board_notice.jsp";
						
					}else if(board.getBoardNotice().equals("N")) {
						path = "/WEB-INF/views/board/hospital_view.jsp";						
					}
					request.setAttribute("board", board);
					
					view = request.getRequestDispatcher(path);
					view.forward(request, response);	
					
				}else {
					request.getSession().setAttribute("status", "error");
					request.getSession().setAttribute("msg", "존재하지 않는 글입니다.");
					response.sendRedirect("/hospitalForm.do");
				}
				
			}
			
			// ===== 정보공유방 : 병원 게시글 수정 화면 =====
			else if(command.equals("/updateForm.do")) {
								
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = service.updateHosView(boardNo);	
				board.setBoardNo(boardNo);
				path = "/WEB-INF/views/board/hospital_update.jsp";					
				request.setAttribute("board", board);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
						
			// ===== 정보공유방 : 병원 정보 수정 =====
			else if(command.equals("/updateHos.do")) {
				
				errorMsg = "병원 정보 수정" + errorMsg;
				
				String boardTitle = request.getParameter("titleInfo");
				String hosTime = request.getParameter("hosTime");
				String hosAddress = request.getParameter("hosAddress");
				String hosNightYN = request.getParameter("nitghYN");
				String hosWeekenYN = request.getParameter("weekYN"); 
				
				int boardNo = Integer.parseInt(request.getParameter("no"));

				Board board = new Board(boardNo, boardTitle, hosAddress, 
						hosTime, hosNightYN, hosWeekenYN);
				
				int result = service.updateHosBoard(board);
				
				if(result>0) {
					status = "success";
					msg = "게시글 수정에 성공했습니다.";
					path = "hospitalView.do?type=" + boardType + "&no=" + boardNo + "&cp=" + currentPage;
				}else {
					status = "error";
					msg = "게시글 수정에 실패했습니다.";
					path = "hospitalView.do?type=" + boardType + "&no=" + boardNo + "&cp=" + currentPage;
				}
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
			}
						
			// ===== 정보공유방 : 병원 공지사항 수정 화면 구성 =====
			else if(command.equals("/updateHosBoardForm.do")) {
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = service.updateHosBoardView(boardNo);
				
				path = "/WEB-INF/views/board/board_update.jsp";
				
				request.setAttribute("board", board);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			// ===== 정보공유방 : 병원 공지사항 수정 =====
			else if(command.equals("/updateHosNotice.do")) {
				
				errorMsg = "공지사항 수정" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				String noticeYN = request.getParameter("notice");
				
				String boardTitle = request.getParameter("titleInfo");
				String boardContent = request.getParameter("content");
				
				Board board = new Board(boardNo, boardTitle, boardContent);				
				
				int result = service.updateHosNotice(board);
				
				if(result>0) {
					status = "success";
					msg = "게시글 수정에 성공했습니다.";
					path = "hospitalView.do?type=" + boardType + "&no=" + boardNo + "&notice=" + noticeYN + "&cp=" + currentPage;
				}else {
					status = "error";
					msg = "게시글 수정에 실패했습니다.";
					path = "hospitalView.do?type=" + boardType + "&no=" + boardNo + "&notice=" + noticeYN + "&cp=" + currentPage;
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
	
			}

		
			// ===== 정보공유방 : 병원 게시글 삭제 =====
			else if(command.equals("/deleteHos.do")){
				errorMsg = "게시글 삭제" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				int result = service.deleteHosBoard(boardNo);
				
				if(result>0) {
					status = "success";
					msg = "게시글 삭제에 성공했습니다.";
					path = "hospitalForm.do?type=" + boardType + "&cp=" + currentPage;
				}else {
					status = "error";
					msg = "게시글 삭제에 실패했습니다.";
					path = "hospitalForm.do?type=" + boardType + "&cp=" + currentPage;
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
				
			}	

			 // ===== 정보공유방 정보 등록 요청 =====
	         else if(command.equals("/CreateInfoForm.do")) {
				
				errorMsg = "정보 등록 요청" + errorMsg;
				
				HttpSession session = request.getSession();
				Member loginMember = (Member)session.getAttribute("loginMember");
				
				String hosName = request.getParameter("hospitalEn");
				String loca = request.getParameter("locationEn");
				String content = request.getParameter("infoText");
				int memberNo = loginMember.getMemberNo();
				String requestType = request.getParameter("requestType");
				
				String requestContent = hosName + ", " + loca + ", " + content;
				
				RequestBoard rBoard = new RequestBoard(requestType, requestContent, memberNo);
				
				int result = service.createBoard(rBoard);
				
				if(result > 0) {
					status = "success";
					msg = "정보 등록 요청에 성공했습니다.";
					path = "hospitalForm.do?type=" + boardType + "&cp=" + currentPage;
				}else {
					status = "error";
					msg = "정보 등록 요청에 실패했습니다.";
					path = "hospitalForm.do?type=" + boardType + "&cp=" + currentPage;
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
				
			}
			
			// ===== 정보 수정 및 삭제 요청 ====
			else if(command.equals("/UpdateInfoForm.do")) {
				
				HttpSession session = request.getSession();
				Member loginMember = (Member)session.getAttribute("loginMember");
								
				String requestType = request.getParameter("updateRadio");
				String requestContent = request.getParameter("modalInfo");
				int boardNo = Integer.parseInt(request.getParameter("no"));
				
				int memberNo = loginMember.getMemberNo();
				
				RequestBoard rBoard = new RequestBoard(requestType, requestContent, boardNo, memberNo);
				
				int result = service.updateBoard(rBoard);
								
				if(result > 0) {
					status = "success";
					msg = "정보 수정/삭제 요청에 성공했습니다.";
					path = "hospitalView.do?type=" + boardType + "&no=" + boardNo + "&cp=" + currentPage;
				}else {
					status = "error";
					msg = "정보 수정/삭제 요청에 실패했습니다.";
					path = "hospitalView.do?type=" + boardType + "&no=" + boardNo + "&cp=" + currentPage;
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
	
			}
		
			// ====== 상식 목록 조회 ======
			else if(command.equals("/knowledgeForm.do")) {
				errorMsg = "연령대별 상식 목록 조회";
				
				int commonAge = Integer.parseInt(request.getParameter("age"));
				Page pInfo = null;
				List<Board> bList = null;
				
				if(commonAge == 0) {
					pInfo = service.getPageInfo(currentPage, boardType);

					bList = service.selectList(pInfo);
				} else {
					pInfo = service.getAgePageInfo(currentPage, boardType, commonAge);
					
					bList = service.selectAgeList(pInfo);
				}
				path = "/WEB-INF/views/board/knowledge_list.jsp";
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("bList", bList);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ===== 지식게시판 상세 조회 Controller
			else if(command.equals("/knowledgeView.do")) {
				errorMsg = "연령대별 상식 조회" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = service.selectkBoard(boardNo);
				
				if(board != null) {
					
					path = "/WEB-INF/views/board/knowledge_view.jsp";
					
					request.setAttribute("board", board);
					
					view = request.getRequestDispatcher(path);
					view.forward(request, response);

				} else {
					
					request.getSession().setAttribute("status", "error");
					request.getSession().setAttribute("msg", "존재하지 않는 글입니다.");
					response.sendRedirect("/knowledgeForm.do");
				}
	
			}
			
			// ===== 지식게시판 게시글 수정 화면 구성 Controller =====
			else if(command.equals("/updatekBoardForm.do")) {
				
				errorMsg = "수정화면으로 전환";
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = service.updateBoardView(boardNo);
				
				
				if(board != null) {
					path = "/WEB-INF/views/board/knowledge_update.jsp";
					request.setAttribute("board", board);
					
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					request.getSession().setAttribute("status", "error");
					request.getSession().setAttribute("msg", 
							"수정 전 내용이 정상 조회되지 않았습니다.");
				}
				
				
			}
			
			// ===== 지식게시판 게시글 수정 Controller =====
			else if(command.equals("/updateKnowledge.do")) {
				
				errorMsg = "게시글 수정";
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				String boardTitle = request.getParameter("titleInfo");
				String boardContent = request.getParameter("content");
				
				Board board = new Board(boardNo, boardTitle, boardContent);
				
				int result = service.updateKnowledge(board);
				
				if(result>0) {
					status = "success";
					msg = "게시글 수정에 성공했습니다.";
					path = "knowledgeView.do?type=" + boardType + "&no=" + boardNo + "&cp=" + currentPage;
				}else {
					status = "error";
					msg = "게시글 수정에 실패했습니다.";
					path = "knowledgeView.do?type=" + boardType + "&no=" + boardNo + "&cp=" + currentPage;
				}
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
				
				
			}
			
			// ===== 지식게시판 게시글 삭제 Controller =====
			else if(command.equals("/deleteKnowledge.do")) {
				
				errorMsg = "게시글 삭제";
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				int result = service.deleteKnowledge(boardNo);
				
				if(result>0) {
					status = "success";
					msg = "게시글 삭제에 성공했습니다.";
					path = "knowledgeForm.do?type=" + boardType + "&cp=" + currentPage + "&age=0";
				}else {
					status = "error";
					msg = "게시글 삭제에 실패했습니다.";
					path = "KnowledgeView.do?type=" + boardType + "&cp=" + currentPage + "&no=" + boardNo;
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
				
				
			}
			
			// ====== main best 글 가져오는 controller ======
	         else if(command.equals("/best_list.do")) {
	            
	            List<Board> bList = service.bestList(boardType);
	            
	            new Gson().toJson(bList, response.getWriter());
	         }
			
			 // ===== 좋아요 이벤트 Controller =====
	         else if(command.equals("/updateLike.do")) {
				int boardNo = Integer.parseInt(request.getParameter("no"));
				
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				
				BoardLike boardLike = new BoardLike(memberNo, boardNo, boardType);
				
				int result = service.updateBoardLike(boardLike);
				
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
