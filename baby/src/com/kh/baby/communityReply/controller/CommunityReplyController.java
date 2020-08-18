package com.kh.baby.communityReply.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.baby.communityReply.model.service.CommunityReplyService;
import com.kh.baby.communityReply.model.vo.CommunityReply;

@WebServlet("/communityReply/*")
public class CommunityReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();		
		String contextPath = request.getContextPath();		
		String command = uri.substring((contextPath + "/communityReply").length());		

		String errorMsg = " 과정에서 오류가 발생했습니다.";
		
		RequestDispatcher view = null;
		
		String status = null;
		String msg = null;
		String text = null;
		
		
		try {
			
			CommunityReplyService service = new CommunityReplyService();
			
			// ===== 댓글 목록 조회 =====
			if(command.equals("/freeReplyList.do")) {
				errorMsg = "댓글 목록 조회" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("boardNo"));
				List<CommunityReply> rList = service.freeSelectList(boardNo);
				
				Gson gson = new GsonBuilder().
						setDateFormat("yyyy년 MM월 dd일 HH:mm:ss").create();
				gson.toJson(rList, response.getWriter());
				
			}
			
			// ===== 댓글 등록 =====
			else if(command.equals("/freeInsertReply.do")) {
				errorMsg = "댓글 등록" + errorMsg;
				
				int boardNo = Integer.parseInt(request.getParameter("boardNo"));
				String memberId = request.getParameter("memberId");
				String replyContent = request.getParameter("replyContent");
				
				CommunityReply reply = new CommunityReply(replyContent, boardNo, memberId);
				
				int result = service.freeInsertReply(reply);
				
				if(result>0) {
					response.getWriter().print("댓글 등록 성공");
					
				}else {
					response.getWriter().print("댓글 등록 실패");
				}
			}
			
			// ===== 댓글 삭제 =====
			else if(command.equals("/freeDeleteReply.do")) {
				errorMsg = "댓글 삭제" + errorMsg;
				
				int replyNo = Integer.parseInt(
						request.getParameter("replyNo"));
				
				
				int result = service.freeDeleteReply(replyNo);
				
				if(result>0) {
					response.getWriter().print("댓글 삭제 성공");
				}else {
					response.getWriter().print("댓글 삭제 실패");
				}
				
			}
			
			// ===== 댓글 수정 =====
			else if(command.equals("/freeUpdateReply.do")) {
				
				errorMsg = "댓글 수정" + errorMsg;
				
				int replyNo = Integer.parseInt(request.getParameter("replyNo"));
				String replyContent = request.getParameter("replyContent");
				
				CommunityReply reply = new CommunityReply(replyNo, replyContent);				
				
				int result = service.freeUpdateReply(reply);
				
				if(result>0) {
					response.getWriter().print("댓글 수정 성공");
					
				}else {
					response.getWriter().print("댓글 수정 실패");
				}
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			response.getWriter().print(errorMsg);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
