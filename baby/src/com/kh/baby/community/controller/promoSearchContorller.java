package com.kh.baby.community.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.baby.community.model.vo.Attachment;
import com.kh.baby.community.model.vo.Board;
import com.kh.baby.community.model.vo.PageInfo;
import com.kh.baby.community.model.service.communitySearchService;

@WebServlet("/promoSearch")
public class promoSearchContorller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		int boardType = Integer.parseInt(request.getParameter("type"));
		String currentPage = request.getParameter("cp");
		
		try {
			
			communitySearchService service = new communitySearchService();
			
			PageInfo pInfo = service.promoGetPageInfo(currentPage, boardType, searchKey, searchValue);
       	 	List<Board> promoList = service.promoSelectSearch(pInfo, searchValue, searchKey);
			List<Attachment> fileList = service.promoFileList(pInfo);
			
       	 	String path = "/WEB-INF/views/community/promo_list.jsp";
       	 
			request.setAttribute("pInfo", pInfo);
			request.setAttribute("promoList", promoList);
			request.setAttribute("fileList", fileList);
			
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
