package com.kh.baby.community.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.kh.baby.community.model.vo.Attachment;
import com.kh.baby.community.model.vo.Board;
import com.kh.baby.community.model.vo.BoardLike;
import com.kh.baby.community.model.vo.PageInfo;
import com.kh.baby.community.model.service.*;
import com.kh.baby.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/community/*")
public class CommunityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/community").length());
		String path = null;
		RequestDispatcher view = null;
		String status = null;
		String msg = null;
		String text = null;
		String errorMsg = " 과정에서 오류가 발생했습니다.";
		HttpSession session = request.getSession();
		
		try {
			
			CommunityService communityService = new CommunityService(); //다른 이름의 서비스들도 호출해야되기때문에 이름을 구별해서 붙여줌
			int boardType = Integer.parseInt(request.getParameter("type"));
			String currentPage = request.getParameter("cp");
			String sortType = request.getParameter("sortType");
			
			// 홍보게시판
			if(command.equals("/promoList.do")) {
				errorMsg = "홍보게시판 목록 조회";
				PageInfo pInfo= null;
				List<Board> promoList = null;
				List<Attachment> fileList = null;
				
				
		
				if (sortType==null || sortType.equals("전체보기")) { // 일반적인 경우
					pInfo = communityService.promoPageInfo(boardType, currentPage);
					promoList = communityService.promoSelectList(pInfo);
					fileList = communityService.selectFileList(pInfo);
					
				} else {
					pInfo = communityService.promoSortPageInfo(boardType, currentPage, sortType);
					promoList = communityService.promoSortList(pInfo);
					fileList = communityService.promoSortFileList(pInfo);
				}
				
				

				//---------------------------------------------------
				path = "/WEB-INF/views/community/promo_list.jsp";
				
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("promoList", promoList); 
				request.setAttribute("fileList", fileList);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			// 자유게시판 
						else if(command.equals("/freeList.do")) {
							errorMsg = "자유게시판 목록 조회";
							PageInfo pInfo =null;
							List<Board> freeList =null;
							
							
							if (sortType==null || sortType.equals("전체보기")) {
							pInfo = communityService.getPageInfo(boardType, currentPage);
							freeList = communityService.selectList(pInfo);
							
							} else {
							pInfo = communityService.getSortPageInfo(boardType, currentPage,sortType);
							freeList = communityService.freeSortList(pInfo);
								
							}
							
							
							path = "/WEB-INF/views/community/free_list.jsp";
							request.setAttribute("pInfo", pInfo);
							request.setAttribute("freeList", freeList); 
							
							view = request.getRequestDispatcher(path);
							view.forward(request, response);								
						}
						
			
			// 자유게시판 게시글 작성 화면 이동
			else if(command.equals("/freeInsertForm.do")) {
				
				path = "/WEB-INF/views/community/free_Insert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 홍보게시판 글쓰기 폼응로 연결
			else if(command.equals("/promoInsertForm.do")) {
				
				path = "/WEB-INF/views/community/promo_Insert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 자유게시판 상세 게시글 조회
			else if(command.equals("/freeView.do")) {
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = communityService.selectBoard(boardNo);

				if(board !=null) {
					List<Attachment> fList = communityService.selectFiles(boardNo);
					
					if (!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
					
				path = "/WEB-INF/views/community/free_view.jsp";
				request.setAttribute("board", board);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
				}
			}
			// 홍보게시판 상세 게시글 조회
			else if(command.equals("/promoView.do")) {
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = communityService.promoSelectBoard(boardNo);

				if(board !=null) {
					List<Attachment> fList = communityService.selectFiles(boardNo);
					
					if (!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
					
				path = "/WEB-INF/views/community/promo_view.jsp";
				request.setAttribute("board", board);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
				}
			
			
			// 자유게시판 글 삽입
			
			else if (command.equals("/freeInsert.do")) {
				int maxSize = 1024*1024*10;
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root+"resources\\uploadImages";
				MultipartRequest mRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());

				String boardTitle = mRequest.getParameter("texttitle") ; 
				String boardContent = mRequest.getParameter("textcontent") ; 
				String categoryName = mRequest.getParameter("category") ;
				
				// session에서 로그인된 아이디 얻어오기
				String memberId= ((Member)request.getSession().getAttribute("loginMember")).getMemberId();
				Board board = new Board(boardTitle, boardContent, memberId, categoryName, boardType);
			
				// 파일 정보 저장 !!
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = mRequest.getFileNames(); 
				Attachment temp = null;
				
				while (files.hasMoreElements()) {
					String name= files.nextElement();
					if(mRequest.getFilesystemName(name) !=null) {
						
						temp = new Attachment();
						temp.setFileOriginName(mRequest.getOriginalFileName(name)); //원본 저장
						temp.setFileChangeName(mRequest.getFilesystemName(name));  // 바뀐이름 저장
						int fileLevel=0;
						
						switch(name) {
						case "img1" : fileLevel = 0; break;
						case "img2" : fileLevel = 1; break;
						case "img3" : fileLevel = 2; break;
						case "img4" : fileLevel = 3; break;
						
						}
						
						temp.setFileLevel(fileLevel);
						temp.setFilePath(filePath);
						fList.add(temp); 
					}
				}
				
				int result = communityService.insertBoard(board,fList);
			
				if (result >0 ) {
					status = "success";
					msg = "게시글 등록 성공";
					path = "freeView.do?type="+boardType+"&cp=1&no="+result;
				} else {
					status = "error";
					msg = "게시글 등록 실패";
					path = request.getHeader("referer");
				}
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
			}
			
			
			// 홍보게시판 글 삽입
			else if (command.equals("/promoInsert.do")) {
				
				int maxSize = 1024*1024*10;
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root+"resources\\uploadImages";
				MultipartRequest mRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				String boardTitle = mRequest.getParameter("texttitle") ; 
				String boardContent = mRequest.getParameter("textcontent") ; 
				int sellAge = Integer.parseInt(mRequest.getParameter("category")) ;
				String sellIntro = mRequest.getParameter("sellIntro") ;
				int price = Integer.parseInt(mRequest.getParameter("price"));
				
				String hashtag1 = mRequest.getParameter("hashtag1");
				String hashtag2 = mRequest.getParameter("hashtag2");
				String hashtag3 = mRequest.getParameter("hashtag3");
				String hashtag4 = mRequest.getParameter("hashtag4");
				String hashtag5 = mRequest.getParameter("hashtag5");
				
				String hashtag = hashtag1+", "+hashtag2+", "+hashtag3+", "+hashtag4+", "+hashtag5;
				
				
				// session에서 로그인된 아이디 얻어오기
				String memberId= ((Member)request.getSession().getAttribute("loginMember")).getMemberId();
				Board board = new Board(boardTitle, boardContent, memberId, sellAge, boardType, price, sellIntro, hashtag);
				// 파일 정보 저장 !!
				
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = mRequest.getFileNames(); 
				Attachment temp = null;
				
				while (files.hasMoreElements()) {
					String name= files.nextElement();
					if(mRequest.getFilesystemName(name) !=null) {
						
						temp = new Attachment();
						temp.setFileOriginName(mRequest.getOriginalFileName(name)); //원본 저장
						temp.setFileChangeName(mRequest.getFilesystemName(name));  // 바뀐이름 저장
						int fileLevel=0;
						
						switch(name) {
						case "img1" : fileLevel = 0; break;
						case "img2" : fileLevel = 1; break;
						case "img3" : fileLevel = 2; break;
						case "img4" : fileLevel = 3; break;
						
						}
						
						temp.setFileLevel(fileLevel);
						temp.setFilePath(filePath);
						fList.add(temp); 
					}
				}
				
				int result = communityService.insertPromoBoard(board,fList);
				
			
				
				if (result >0 ) {
					status = "success";
					msg = "게시글 등록 성공";
					path = "promoView.do?type="+boardType+"&cp=1&no="+result;
				} else {
					status = "error";
					msg = "게시글 등록 실패";
					path = request.getHeader("referer");
				}
				
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
				
				// 자유게시판 수정 화면 연결 
			} else if (command.equals("/freeUpdateForm.do")) {
				int boardNo= Integer.parseInt(request.getParameter("no"));
				Board board = communityService.updateFreeView(boardNo);
				
				if(board!=null) {
					List<Attachment> fList = communityService.selectFiles(boardNo); 
					
					if(!fList.isEmpty()) {
						request.setAttribute("fList", fList); 
					} 
					
					path ="/WEB-INF/views/community/free_update.jsp";
					request.setAttribute("board", board); 
					
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				} 
			}
			
			// 자유게시판 수정등록하기 
			
			else if (command.equals("/freeUpdate.do")) {
				int maxSize = 1024*1024*10;
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root+"resources\\uploadImages";
				MultipartRequest mRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());

				int boardNo = Integer.parseInt(mRequest.getParameter("no"));
				String boardTitle = mRequest.getParameter("texttitle") ; 
				String boardContent = mRequest.getParameter("textcontent") ; 
				String categoryName = mRequest.getParameter("category") ;
				
				Board board = new Board(boardNo, boardTitle, boardContent, categoryName, boardType);
			
				// 파일 정보 저장 !!
				List<Attachment> fList = new ArrayList<Attachment>();
				Enumeration<String> files = mRequest.getFileNames(); 
				Attachment temp = null;
				
				while (files.hasMoreElements()) {
					String name= files.nextElement();
					if(mRequest.getFilesystemName(name) !=null) {
						
						temp = new Attachment();
						temp.setFileOriginName(mRequest.getOriginalFileName(name)); //원본 저장
						temp.setFileChangeName(mRequest.getFilesystemName(name));  // 바뀐이름 저장
						int fileLevel=0;
						
						switch(name) {
						case "img1" : fileLevel = 0; break;
						case "img2" : fileLevel = 1; break;
						case "img3" : fileLevel = 2; break;
						case "img4" : fileLevel = 3; break;
						
						}
						temp.setFileLevel(fileLevel);
						temp.setFilePath(filePath);
						fList.add(temp); 
					}
				}
				
				int result = communityService.freeUpdateBoard(board,fList);
			
				
				if (result >0 ) {
					status = "success";
					msg = "게시글 수정 성공";
					path = "freeView.do?type="+boardType+"&cp=1&no="+result;
				} else {
					status = "error";
					msg = "게시글 수정 실패";
					path = request.getHeader("referer");
				}
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
				
				
			}
			
			// 자유 게시판 게시글 삭제하기
			
			else if (command.equals("/freeDelete.do")) {
				int boardNo = Integer.parseInt(request.getParameter("no"));
				int result = communityService.deleteBoard(boardNo);
				
				if (result>0) {
					status = "success";
					msg = "게시글 삭제 성공";
					path = "freeList.do?type="+boardType+"&cp=1";
					
				} else {
					status = "error";
					msg = "게시글 삭제 실패.";
					path = request.getHeader("referer"); 
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				response.sendRedirect(path);
				
			}
			
			// 홍보 게시판 수정 화면으로 연결
			else if (command.equals("/promoUpdateForm.do")) {
				int boardNo= Integer.parseInt(request.getParameter("no"));
				Board board = communityService.updatePromoView(boardNo);
				
				if(board!=null) {
					List<Attachment> fList = communityService.selectFiles(boardNo); 
					
					if(!fList.isEmpty()) {
						request.setAttribute("fList", fList); 
					} 
					
				} 
				path ="/WEB-INF/views/community/promo_update.jsp";
				request.setAttribute("board", board); 
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			// 홍보게시판 수정등록하기 
			
			else if (command.equals("/promoUpdate.do")) {
				int maxSize = 1024*1024*10;
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root+"resources\\uploadImages";
				MultipartRequest mRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				int boardNo = Integer.parseInt(mRequest.getParameter("no"));
				String boardTitle = mRequest.getParameter("texttitle") ; 
				String boardContent = mRequest.getParameter("textcontent") ; 
				int sellAge = Integer.parseInt(mRequest.getParameter("category")) ;
				String sellIntro = mRequest.getParameter("sellIntro") ;
				int price = Integer.parseInt(mRequest.getParameter("price"));
				
				String hashtag1 = mRequest.getParameter("hashtag1");
				String hashtag2 = mRequest.getParameter("hashtag2");
				String hashtag3 = mRequest.getParameter("hashtag3");
				String hashtag4 = mRequest.getParameter("hashtag4");
				String hashtag5 = mRequest.getParameter("hashtag5");
				
				String hashtag = hashtag1+", "+hashtag2+", "+hashtag3+", "+hashtag4+", "+hashtag5;
				
				
				// session에서 로그인된 아이디 얻어오기
				String memberId= ((Member)request.getSession().getAttribute("loginMember")).getMemberId();
				Board board = new Board(boardTitle, boardContent, memberId, sellAge, boardType, price, sellIntro, hashtag);
				board.setBoardNo(boardNo); // 생성자 세팅안해줘서 안넘어갔네요,,,
			
				
				// 파일 정보 저장 !!
				List<Attachment> fList = new ArrayList<Attachment>();
				Enumeration<String> files = mRequest.getFileNames(); 
				Attachment temp = null;
				
				while (files.hasMoreElements()) {
					String name= files.nextElement();
					if(mRequest.getFilesystemName(name) !=null) {
						
						temp = new Attachment();
						temp.setFileOriginName(mRequest.getOriginalFileName(name)); //원본 저장
						temp.setFileChangeName(mRequest.getFilesystemName(name));  // 바뀐이름 저장
						int fileLevel=0;
						
						switch(name) {
						case "img1" : fileLevel = 0; break;
						case "img2" : fileLevel = 1; break;
						case "img3" : fileLevel = 2; break;
						case "img4" : fileLevel = 3; break;
						
						}
						temp.setFileLevel(fileLevel);
						temp.setFilePath(filePath);
						fList.add(temp); 
					}
				}
				

				
				
				int result = communityService.promoUpdateBoard(board,fList);

				
				if (result >0 ) {
					status = "success";
					msg = "게시글 수정 성공";
					path = "promoView.do?type="+boardType+"&cp=1&no="+result;
				} else {
					status = "error";
					msg = "게시글 수정 실패";
					path = request.getHeader("referer");
				}
				request.getSession().setAttribute("status", status);
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect(path);
				
				
			}
			
			// 홍보 게시판 게시글 삭제하기
			
			else if (command.equals("/promoDelete.do")) {
				int boardNo = Integer.parseInt(request.getParameter("no"));
				int result = communityService.deleteBoard(boardNo);
				
				if (result>0) {
					status = "success";
					msg = "게시글 삭제 성공";
					path = "promoList.do?type="+boardType+"&cp=1";
					
				} else {
					status = "error";
					msg = "게시글 삭제 실패.";
					path = request.getHeader("referer"); 
				}
				
				session.setAttribute("status", status);
				session.setAttribute("msg", msg);
				response.sendRedirect(path);
				
			}
			
			// 좋아요기능
			
			 else if(command.equals("/updateLike.do")) {
					int boardNo = Integer.parseInt(request.getParameter("no"));
					
					int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
					
					Board board = new Board(boardNo,boardType,memberNo);
					
					int result = communityService.updateBoardLike(board);

					response.getWriter().print(result);
		         }
			
			
			// 자유게시판 분류 기능
			 else if(command.equals("/freeSort.do")) {
				sortType=request.getParameter("sortType");
				PageInfo pInfo = communityService.freeSearchPageInfo(boardType,currentPage, sortType);	

			 	List<Board> freeList = communityService.freeSelectSearch(pInfo,sortType);       	 	
				
	       	 	path = "/WEB-INF/views/community/free_list.jsp";
	       	 
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("freeList", freeList);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			 }
			
			// 홍보게시판 분류 기능
			 else if(command.equals("/promoSort.do")) {
				 
				 sortType=request.getParameter("sortType");
				 
				 PageInfo pInfo = communityService.promoFilterPageInfo(boardType, currentPage, sortType);	
				 
				 List<Board> promoList = communityService.promoFilter(pInfo, sortType);       	 	
				List<Attachment> fileList = communityService.selectFileList(pInfo);

				 path = "/WEB-INF/views/community/promo_list.jsp";
				 
				 request.setAttribute("pInfo", pInfo);
				 request.setAttribute("promoList", promoList);
				request.setAttribute("fileList", fileList);

				 view = request.getRequestDispatcher(path);
				 view.forward(request, response);
				 
			 }
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
