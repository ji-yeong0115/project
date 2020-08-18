package com.kh.baby.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.baby.board.model.service.TimeFilterService;
import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.Page;


@WebServlet("/timeFilter.do")
public class TimeFilterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int yn = Integer.parseInt(request.getParameter("yn"));
		
		int boardType = Integer.parseInt(request.getParameter("type"));
		String currentPage = request.getParameter("cp");
		
		String errorMsg = "";
		
		try {
			
			errorMsg = "게시글 조회";
			
			TimeFilterService service = new TimeFilterService();
			
			Page pInfo = service.getPageInfo(currentPage, boardType, yn);
			List<Board> bList = service.selectFilter(pInfo, yn);       	 	
			
       	 	String path = "/WEB-INF/views/board/hospital_list.jsp";
          	 
			request.setAttribute("pInfo", pInfo);
			request.setAttribute("bList", bList);
			
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			String path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "검색하는 과정에서 오류가 발생했습니다.");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request, response);
	}

}
