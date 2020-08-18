package com.kh.baby.community.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.kh.baby.community.model.dao.CommunityDAO;
import com.kh.baby.community.model.vo.Attachment;
import com.kh.baby.community.model.vo.Board;
import com.kh.baby.community.model.vo.BoardLike;
import com.kh.baby.community.model.vo.PageInfo;

public class CommunityService {

	private CommunityDAO dao;
	
	public CommunityService() throws Exception {
		dao = new CommunityDAO();
	}
	/** 자유게시판 게시글(글+파일) 등록 Service
	 * @param board
	 * @param fList
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Board board, List<Attachment> fList) throws Exception{
		Connection conn = getConnection();
		int result = 0; 
		// 1. 다음 게시글 번호 얻어오기
			int boardNo = dao.selectNextNo(conn);
			int categoryNo = dao.selectCategoryNo(conn,board);

			if(boardNo >0 ) {
				//얻어온 다음 글번호 board에다 세팅
				board.setBoardNo(boardNo);
				
		       board.setBoardContent(replaceParameter(board.getBoardContent())); // 크로스 사이트 스크립팅 방지
		       board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>")); // 개행문자 처리
		       
		       result = dao.insertBoard(conn, board);
		       
		       if(result>0 && !fList.isEmpty()) {
		    	   result = 0; 
		    	   
		    	   for(Attachment at : fList) {
		    		   at.setParentBoardNo(boardNo);
		    		   
		    		   result=dao.insertAttachment(conn,at);
		    		   
		    		   if(result ==0 ) break;
		    		   }
		    	   }
		       
		       if(result>0) {
		    	   result = 0;
		    	   result = dao.insertFreeBoard(conn,  boardNo, categoryNo);
		       } 
		       
			}

			if (result >0 ) {
				// 모두 삽입 성공 시 게시글 번호 반환해, 게시글 상세 조회 화면으로 바로 이동하게 함
				result = boardNo;
				conn.commit();
			} else {
				
				for(Attachment at : fList) {
					String filePath = at.getFilePath();
					String fileName = at.getFileChangeName();
					
					File deleteFile = new File(filePath + fileName);
					deleteFile.delete();
				}
			}
		
		conn.close();
		return result;
	}
	
	
	
	/** 홍보게시판 게시글(글+파일) 등록 Service
	 * @param board
	 * @param fList
	 * @return result
	 * @throws Exception
	 */
	public int insertPromoBoard(Board board, List<Attachment> fList) throws Exception{
		Connection conn = getConnection();
		int result = 0; 
		int boardNo = dao.selectNextNo(conn);
		int categoryNo = dao.selectCategoryNo(conn,board);
	
		
		if(boardNo >0 ) {
			//얻어온 다음 글번호 board에다 세팅
			board.setBoardNo(boardNo);
			
			board.setBoardContent(replaceParameter(board.getBoardContent())); // 크로스 사이트 스크립팅 방지
			board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>")); // 개행문자 처리
			
			result = dao.insertBoard(conn, board);
			
			if(result>0 && !fList.isEmpty()) {
				result = 0; 
				
				for(Attachment at : fList) {
					at.setParentBoardNo(boardNo);
					
					result=dao.insertAttachment(conn,at);
					
					if(result ==0 ) break;
				}
			}
			
			if(result>0) {
				result = 0;
			
				result = dao.insertPromoBoard(conn,  boardNo, categoryNo, board);
			} 
			
		}
		
		if (result >0 ) {
			// 모두 삽입 성공 시 게시글 번호 반환해, 게시글 상세 조회 화면으로 바로 이동하게 함
			result = boardNo;
			conn.commit();
		} else {
			
			for(Attachment at : fList) {
				String filePath = at.getFilePath();
				String fileName = at.getFileChangeName();
				
				File deleteFile = new File(filePath + fileName);
				deleteFile.delete();
			}
		}
		
		conn.close();
		return result;
	}

	/** 자유게시판 페이징처리 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(int boardType, String currentPage) throws Exception {
		Connection conn = getConnection();
		int cp = currentPage == null ? 1: Integer.parseInt(currentPage); //currentpage가 null이면 참일때 1 아니면 파싱하는 삼항연산자
		int listCount = dao.getListCount(conn,boardType);

		conn.close();
		return new PageInfo(cp, listCount, boardType);
	}
	
	/** 자유게시판 페이징처리 정보 생성 분류 필터 적용 시 Service
	 * @param currentPage
	 * @param boardType
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getSortPageInfo(int boardType, String currentPage, String sortType) throws Exception {
		Connection conn = getConnection();
		int cp = currentPage == null ? 1: Integer.parseInt(currentPage); //currentpage가 null이면 참일때 1 아니면 파싱하는 삼항연산자
		int listCount = dao.getSortListCount(conn,boardType,sortType);
		
		PageInfo pInfo = new PageInfo(cp, listCount, boardType);
		pInfo.setSortType(sortType);
		
		conn.close();
		return pInfo;
	}
	
	
	/** 홍보게시판 페이징처리 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo promoPageInfo(int boardType, String currentPage) throws Exception {
		Connection conn = getConnection();
		int cp = currentPage == null ? 1: Integer.parseInt(currentPage); //currentpage가 null이면 참일때 1 아니면 파싱하는 삼항연산자
		int listCount = dao.getPromoCount(conn,boardType);
		
		conn.close();
		return new PageInfo(cp, listCount, boardType);
	}
	
	
	
	
	/** 게시글 목록 조회 Service
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		List<Board> bList = dao.selectList(conn, pInfo);
		conn.close();
		return bList;
	}
	
	
	/** 자유게시판 게시글 상세 조회 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(int boardNo) throws Exception{
		Connection conn = getConnection();
		Board board = dao.selectBoard(conn, boardNo);
		
		if (board !=null ) { // 조회수 증가 dao
			int result = dao.increaseCount(conn, boardNo);
			
			if (result >0 ) { conn.commit();
			
			board.setReadCount(board.getReadCount()+1);
			
			} else { conn.rollback();}
		}
		conn.close();
		return board;
	}
	
	/** 홍보 게시글 목록 조회 Service
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> promoSelectList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		List<Board> promoList = dao.promoSelectList(conn, pInfo);
		conn.close();
		
		return promoList;
	}
	
	
	/** 홍보게시판 게시글 상세 조회 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board promoSelectBoard(int boardNo) throws Exception{
		Connection conn = getConnection();
		Board board = dao.promoSelectBoard(conn, boardNo);
		
		if (board !=null ) { // 조회수 증가 dao
			int result = dao.increaseCount(conn, boardNo);
			
			if (result >0 ) { conn.commit();
			
			board.setReadCount(board.getReadCount()+1);
			
			} else { conn.rollback();}
		}
		conn.close();
		return board;
	}
	
	
	/**  게시글에 포함된 이미지 조회 service
	  * @param boardNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectFiles(int boardNo) throws Exception {
		Connection conn = getConnection();
		List<Attachment> fList = dao.selectFiles(conn, boardNo);
		conn.close();		
		return fList;
	}

	
	
	/** 자유게시판 글 수정 화면 구성 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board updateFreeView(int boardNo) throws Exception {
		Connection conn = getConnection();
		Board board = dao.freeUpdateView(conn,boardNo);
		board.setBoardContent(board.getBoardContent().replace("<br>", "\r\n")); 
		conn.close();		
		return board;
	}
	
	/** 게시글 수정Service
	 * @param board
	 * @param fList
	 * @return 
	 * @throws Exception
	 */
	public int freeUpdateBoard(Board board, List<Attachment> fList) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		
		board.setBoardContent(replaceParameter(board.getBoardContent())); // 크로스 사이트 스크립팅 방지
	    board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>")); // 개행문자 처리
	     
	    // 1. 게시물 수정 dao 
	    result = dao.updateBoard(conn,board);

	    List<Attachment> deleteFiles = new ArrayList<Attachment>();
		
	    if(result>0 && !fList.isEmpty()) {
	    	  result = 0; // result 재사용
	    	  
	    	  List<Attachment> oldList = dao.selectFiles(conn, board.getBoardNo()); // dao재활용
	    	  
	    	  boolean flag = false;
	    	  
	    	  for(Attachment newFile : fList) {
	    		  
	    		  flag = false; // flag 초기화
	    		  for(Attachment oldFile:oldList) {
	    			  if(newFile.getFileLevel()==oldFile.getFileLevel()) {
	    				  flag=true;
	    				  deleteFiles.add(oldFile); //기존 파일을 삭제 리스트에 추가
	    				  newFile.setFileNo(oldFile.getFileNo());
	    			  }
	    		  }
	    		  
	    		  newFile.setParentBoardNo(board.getBoardNo());
	    		  
	    		  if(flag) { // update 상황 == flag가 트루일때
	    			  result = dao.updateAttachment(conn, newFile);
	    		  } else { // insert 상황
	    			  result = dao.insertAttachment(conn, newFile);
	    		  }
	    		  
	    		  if (result==0) break; // 방금 업데이트를 실패했다는 뜻, 그럼 바로멈춤
	    			      			  
	    	  }
	      }
	    
		    if(result>0) {
				result = 0;
				result = dao.freeUpdateBoard(conn, board);
			} 
		
		      
	      List<Attachment> tempList = null;
	      
	      if (result>0) {
	    	  result = board.getBoardNo(); // 수정 완료 후 내가 있던 페이지로 돌아가려고 해당게시글 상세조회를 위해 result에 글번호 저장해서 반환
	    	  
	    	  conn.commit();
	    	  tempList = deleteFiles;
	      } else {
	    	  conn.rollback();
	    	  tempList = fList; 
	      }
	      
	      for (Attachment at : tempList) {
	    	  String filePath = at.getFilePath();
	    	  String fileName = at.getFileChangeName();
	    	  
	    	  File deleteFile = new File(filePath + fileName);
	    	  deleteFile.delete();
	      }
		conn.close();		
		return result;		
	}
	
	/** 게시글삭제 service
	 * @param boardNo 
	 * @return result
	 * @throws Exception
	 */
	public int deleteBoard(int boardNo) throws Exception {
		Connection conn = getConnection();
		int result = dao.deleteBoard(conn, boardNo);
		
		if (result>0) conn.commit();
		else conn.rollback();
		
		conn.close();		
		return result;
	}
	
		
	/** 홍보게시판 글 수정 화면 구성 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board updatePromoView(int boardNo) throws Exception {
		Connection conn = getConnection();
		Board board = dao.promoUpdateView(conn,boardNo);
		board.setBoardContent(board.getBoardContent().replace("<br>", "\r\n")); 
		conn.close();		
		return board;
	}
	
	
	/** 홍보게시판 수정 SERVICE
	 * @param board
	 * @param fList
	 * @return
	 * @throws Exception
	 */
	public int promoUpdateBoard(Board board, List<Attachment> fList) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		
		board.setBoardContent(replaceParameter(board.getBoardContent())); // 크로스 사이트 스크립팅 방지
	    board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>")); // 개행문자 처리
	     
	    // 1. 게시물 수정 dao 
	    result = dao.updateBoard(conn,board);

	    List<Attachment> deleteFiles = new ArrayList<Attachment>();
		
	    if(result>0 && !fList.isEmpty()) {
	    	  result = 0; // result 재사용
	    	  
	    	  List<Attachment> oldList = dao.selectFiles(conn, board.getBoardNo()); // dao재활용
	    	  
	    	  boolean flag = false;
	    	  
	    	  for(Attachment newFile : fList) {
	    		  
	    		  flag = false; // flag 초기화
	    		  for(Attachment oldFile:oldList) {
	    			  if(newFile.getFileLevel()==oldFile.getFileLevel()) {
	    				  flag=true;
	    				  deleteFiles.add(oldFile); //기존 파일을 삭제 리스트에 추가
	    				  newFile.setFileNo(oldFile.getFileNo());
	    			  }
	    		  }
	    		  newFile.setParentBoardNo(board.getBoardNo());
	    		  
	    		  if(flag) {  result = dao.updateAttachment(conn, newFile);
	    		  } else {  result = dao.insertAttachment(conn, newFile);}
	    		  
	    		  if (result==0) break; // 방금 업데이트를 실패했다는 뜻, 그럼 바로멈춤
	    	  }
	      }
		    if(result>0) {
				result = 0;
				result = dao.promoUpdateBoard(conn, board);
			} 
		      
	      List<Attachment> tempList = null;
	      
	      if (result>0) {
	    	  result = board.getBoardNo(); // 수정 완료 후 내가 있던 페이지로 돌아가려고 해당게시글 상세조회를 위해 result에 글번호 저장해서 반환
	    	  
	    	  conn.commit();
	    	  tempList = deleteFiles;
	      } else {
	    	  conn.rollback();
	    	  tempList = fList; 
	      }
	      
	      for (Attachment at : tempList) {
	    	  String filePath = at.getFilePath();
	    	  String fileName = at.getFileChangeName();
	    	  
	    	  File deleteFile = new File(filePath + fileName);
	    	  deleteFile.delete();
	      }
		conn.close();		
		return result;		
	}
	
	
	/** 썸네일 목록 조회 Service
	 * @param pInfo
	 * @return fileList
	 * @throws Exception
	 */
	public List<Attachment> selectFileList(PageInfo pInfo)throws Exception {
		Connection conn = getConnection();
		List<Attachment> fileList = dao.selectFileList(conn, pInfo);
		conn.close();
		return fileList;
	}
	
	
	/** 게시판 좋아요 기능
	 * @param boardLike
	 * @return
	 * @throws Exception
	 */
	public int updateBoardLike(Board board) throws Exception {
		Connection conn = getConnection();
		int result =  dao.insertBoardLike(conn, board);
			
		if (result >0 ) { conn.commit();
								
		} else { conn.rollback();}
		
		conn.close();

		return result;
	}
	
	/** 자유게시판 분류 service
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<Board> freeSelectSearch(PageInfo pInfo, String sortType) throws Exception {
		Connection conn = getConnection();
		
		String condition = createCondition(sortType);
		
		List<Board> freeList = dao.freeSelectSearch(conn, pInfo, condition);
		conn.close();
		
		return freeList;
	}
	
	/** 자유게시판 분류 페이징처리 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo freeSearchPageInfo(int boardType, String currentPage, String sortType) throws Exception {
		Connection conn = getConnection();
		int cp = currentPage == null ? 1: Integer.parseInt(currentPage); 

		String condition = createCondition(sortType);
		
		int listCount = dao.getSearchCount(conn, boardType, condition);
		
		conn.close();
		
		return new PageInfo(cp, listCount, boardType);
	}
	
	/** 홍보게시판 분류 service
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<Board> promoFilter(PageInfo pInfo, String sortType) throws Exception {
		Connection conn = getConnection();
		
		String condition = promoCondition(sortType);
		
		List<Board> promoList = dao.promoSelectFilter(conn, pInfo,condition);
		conn.close();
		
		return promoList;
	}
	
	
	/** 홍보게시판 분류 페이징처리 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo promoFilterPageInfo(int boardType, String currentPage, String sortType) throws Exception {
		Connection conn = getConnection();
		int cp = currentPage == null ? 1: Integer.parseInt(currentPage); 
		
		String condition = promoCondition(sortType);
		
		int listCount = dao.promoFilterCount(conn, boardType, condition);
		
		conn.close();
		
		return new PageInfo(cp, listCount, boardType);
	}
	
	
	
	
	 /** 자유게시판 분류 키워드 얻어오기
	 * @param sortType
	 * @return condition
	 */
	private String createCondition(String sortType) {
			String condition = null;
			switch(sortType) {
			case "질문" : condition = "CATEGORY_NAME='" + sortType+"'"; break;
			case "나눔" : condition = "CATEGORY_NAME='" + sortType+"'"; break;
			case "수다" : condition = "CATEGORY_NAME='" + sortType+"'";break;
			case "전체보기" : condition = "1" ;break;
			
			}
			

			
			return condition;
	}
	 
	 
	 
	/** 홍보게시판 분류 키워드 얻어오기
	 * @param sortType
	 * @return condition
	 */
	private String promoCondition(String sortType) {
		String condition = null;
		switch(sortType) {
		case "3" : condition = "SELL_AGE=" + sortType; break;
		case "4" : condition = "SELL_AGE=" + sortType; break;
		case "5" : condition = "SELL_AGE=" + sortType; break;
		case "6" : condition = "SELL_AGE=" + sortType; break;
		case "7" : condition = "SELL_AGE=" + sortType; break;
		case "전체보기" : condition = "1" ; break;
		
		}
		
		
		return condition;
	}
	
	
	/** 자유게시판 분류 필터 적용 후 페이지 리스트 얻어오기 
	 * @param pInfo
	 * @return
	 */
	public List<Board> freeSortList(PageInfo pInfo) throws Exception{
		 Connection conn = getConnection();
		      
		 List<Board> freeList = dao.freeSortList(conn, pInfo);
		            
		 conn.close();
		      
		 return freeList;
	}
	 
	// -------------------------홍보게시판 분류 필터 적용 후 페이징바 이동 ------------------- //
	/** 홍보게시판 페이징처리 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo promoSortPageInfo(int boardType, String currentPage, String sortType) throws Exception {
		Connection conn = getConnection();
		int cp = currentPage == null ? 1: Integer.parseInt(currentPage); 
		int listCount = 0;
		
		
		if (sortType==null || sortType.equals("전체보기")) {
			
			listCount = dao.getPromoCount(conn,boardType);
			
		} else {
		
			listCount = dao.getPromoSortCount(conn,boardType,sortType);
		}
		
		PageInfo pInfo = new PageInfo(cp, listCount, boardType);
		pInfo.setSortType(sortType);
		
		conn.close();
		return pInfo;
	
	}
	
	/** 홍보 게시글 목록 조회 Service
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> promoSortList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		List<Board> promoList = dao.promoSortList(conn, pInfo);
		conn.close();
		
		return promoList;
	}
	
	/** 썸네일 목록 조회 Service
	 * @param pInfo
	 * @return fileList
	 * @throws Exception
	 */
	public List<Attachment> promoSortFileList(PageInfo pInfo)throws Exception {
		Connection conn = getConnection();
		List<Attachment> fileList = dao.promoSortFileList(conn, pInfo);
		conn.close();
		return fileList;
	}
	 

	
	
	//---------------------------------------------------------------------------
	
		
		// 크로스 사이트 스크립트 방지 메소드
		private String replaceParameter(String param) {
			String result = param;
			if(param != null) {
				result = result.replaceAll("&", "&amp;");
				result = result.replaceAll("<", "&lt;");
				result = result.replaceAll(">", "&gt;");
				result = result.replaceAll("\"", "&quot;");
			}
			
			return result;
		}
		
}
