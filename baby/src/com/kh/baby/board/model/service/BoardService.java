package com.kh.baby.board.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.baby.board.model.dao.BoardDAO;
import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.BoardLike;
import com.kh.baby.board.model.vo.Page;
import com.kh.baby.board.model.vo.RequestBoard;

public class BoardService {
	private BoardDAO dao;
	
	public BoardService() throws Exception {
		dao = new BoardDAO();
	}
	
	// 크로스 사이트 스트립팅 방지 메소드
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

	/** 페이징 처리 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @return pInfo
	 * @throws Exception
	 */
	public Page getPageInfo(String currentPage, int boardType) throws Exception{
		
		Connection conn = getConnection();

		int cp = currentPage == "null" ? 1 : Integer.parseInt(currentPage);
		
		int listCount = 0;
		if(boardType == 1) {
			listCount = dao.getListCount(conn, boardType);
		} else {
			listCount = dao.getKListCount(conn, boardType);
		}

		conn.close();
		
		return new Page(cp, listCount, boardType);
	}

	/** 병원게시판 게시글 목록 조회 Service
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectList(Page pInfo) throws Exception{
		
		Connection conn = getConnection();
		
		List<Board> bList = null;
				
		if(pInfo.getBoardType()==1) {
			bList = dao.selectList(conn, pInfo);
		} else {
			bList = dao.selectKList(conn,pInfo);
		}
		
		conn.close();
		
		return bList;
	}

	/** 병원게시판 게시글 상세조회 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		
		Board board = dao.selectBoard(conn, boardNo);
		
		if(board != null) {
			int result = dao.readCount(conn, boardNo);
			
			if(result>0) {
				
				conn.commit();
				board.setReadCount(board.getReadCount()+1);
				
			}else {
				conn.rollback();
			}
		}
		conn.close();
		return board;
	}

	/** 병원게시판 게시글 수정 화면 구성 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board updateHosView(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		Board board = dao.updateView(conn, boardNo);
		
		
		conn.close();
		
		return board;
	}



	/** 병원게시판 게시글 수정 Service
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateHosBoard(Board board) throws Exception{

		Connection conn = getConnection();
		
		int result = dao.updateBoard(conn, board);
		

		if(result > 0) {
			
			result = 0;
			result = dao.updateBoardHos(conn, board);
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		}else {
			conn.rollback();
		}
		
		conn.close();
		
		return result;
	}

	/** 병원게시판 게시글 삭제 Service
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteHosBoard(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.deleteHosBoard(conn, boardNo);
		
		if(result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}
	
	/** 병원게시판 공지사항 수정 화면 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board updateHosBoardView(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		Board board = dao.selectBoard(conn, boardNo);
		
		board.setBoardContent(board.getBoardContent().replaceAll("<br>", "\r\n"));
		
		return board;
	}

	/** 병원게시판 공지사항 수정 Service
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int updateHosNotice(Board board) throws Exception{

		Connection conn = getConnection();
		
		 board.setBoardContent(replaceParameter(board.getBoardContent()));
		 board.setBoardContent(board.getBoardContent().replaceAll("\r\n", "<br>"));

		
		int result = dao.updateHosNotice(conn, board);
		
		if(result > 0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}
	
	/** 정보 등록 요청 service
	 * @param rBoard
	 * @return result
	 * @throws Exception
	 */
	public int createBoard(RequestBoard rBoard) throws Exception{
		
		Connection conn = getConnection();
		
		rBoard.setRequestContent(replaceParameter
				(rBoard.getRequestContent()));
		rBoard.setRequestContent(rBoard.getRequestContent()
				.replaceAll("\r\n", "<br>"));
		
		int result = dao.requestBoard(conn, rBoard);
		
		if(result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}
	
	/** 정보 상태 수정 요청 Service
	 * @param rBoard
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(RequestBoard rBoard) throws Exception{
		
		Connection conn = getConnection();
		int result = 0;
		
		rBoard.setRequestContent(replaceParameter
				(rBoard.getRequestContent()));
		rBoard.setRequestContent(rBoard.getRequestContent()
				.replaceAll("\r\n", "<br>"));
		
		if(rBoard.getRequestType().equals("U")) {
			result = dao.updateInfo(conn, rBoard);
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		}else {
			result = dao.deleteInfo(conn, rBoard);
			
			if(result > 0) conn.commit();
			else conn.rollback();
		}
		
		conn.close();

		return result;
	}
	
	public List<Board> bestList(int boardType) throws Exception {
		Connection conn = getConnection();
		List<Board> bList = dao.bestList(conn, boardType);
		
		conn.close();
		return bList;
	}
	
	public Page getAgePageInfo(String currentPage, int boardType, int commonAge) throws Exception{
	      
	      Connection conn = getConnection();
	      
	      int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);
	      int listCount = dao.getAgeListCount(conn, boardType, commonAge);
	      
	      Page page = new Page(cp, listCount, boardType);
	      page.setCommonAge(commonAge);
	      
	      conn.close();
	      
	      return page;
	   }

	   public List<Board> selectAgeList(Page pInfo) throws Exception{
	      Connection conn = getConnection();
	      
	      List<Board> bList = dao.selectAgeList(conn, pInfo);
	            
	      conn.close();
	      
	      return bList;
	   }

	public int updateBoardLike(BoardLike boardLike) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = dao.insertBoardLike(conn, boardLike);
			result = 1;
		} catch (Exception e) {
			result = dao.deleteBoardLike(conn, boardLike);
			result = 2;
		}
		
		if(result > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}
		conn.close();
		
		return result;
	}

	/** 지식게시판 게시글 상세조회
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectkBoard(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		
		Board board = dao.selectKboard(conn, boardNo);
		
		
		if(board != null) {
			int result = dao.readCount(conn, boardNo);
		
			if(result > 0) {
				conn.commit();
				board.setReadCount(board.getReadCount() + 1);
			}else {
				conn.rollback();
			}
		}
		
		conn.close();

		return board;
	}
	
	/** 지식게시판 수정 화면 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board updateBoardView(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		Board board = dao.selectKboard(conn, boardNo);
		
		board.setBoardContent(board.getBoardContent().replaceAll("<br>", "\r\n"));
		
		
		return board;
	}

	/** 지식게시판 게시글 수정 Service
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateKnowledge(Board board) throws Exception{
		
		Connection conn = getConnection();
		

		 board.setBoardContent(replaceParameter(board.getBoardContent()));
		 board.setBoardContent(board.getBoardContent().replaceAll("\r\n", "<br>"));
		
		int result = dao.updateKnowledge(conn, board);
		
		if(result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}

	/** 지식게시판 게시글 삭제 Service
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteKnowledge(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.deleteknowledge(conn, boardNo);
		
		if(result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}

	public Page getHosPageInfo(String currentPage, int boardType, int yn) throws Exception{
		
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);
		
		int listCount = 0;
		if(yn == 1) {
			listCount = dao.getListCount(conn, boardType);
		}

		conn.close();
		
		return new Page(cp, listCount, boardType);
	}

}