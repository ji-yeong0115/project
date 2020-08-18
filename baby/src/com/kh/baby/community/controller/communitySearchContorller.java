package com.kh.baby.community.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.baby.community.model.vo.Board;
import com.kh.baby.community.model.vo.PageInfo;
import com.kh.baby.community.model.service.communitySearchService;

@WebServlet("/communitySearch")
public class communitySearchContorller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		int boardType = Integer.parseInt(request.getParameter("type"));
		String currentPage = request.getParameter("cp");
		
		try {
			
			communitySearchService service = new communitySearchService();
			
			PageInfo pInfo = service.getPageInfo(currentPage, boardType, searchKey, searchValue);	
       	 	List<Board> freeList = service.selectSearch(pInfo, searchValue, searchKey);       	 	
			
       	 	String path = "/WEB-INF/views/community/free_list.jsp";
       	 
			request.setAttribute("pInfo", pInfo);
			request.setAttribute("freeList", freeList);
			
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			String path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "오류가 발생했습니다.");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	
	}

	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
