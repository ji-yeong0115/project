package com.kh.baby.board.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.BoardLike;
import com.kh.baby.board.model.vo.Page;
import com.kh.baby.board.model.vo.RequestBoard;

public class BoardDAO {
	private Properties prop;
	
	public BoardDAO() throws Exception {
		String fileName 
		= BoardDAO.class.getResource("/com/kh/baby/sql/board/board-query.properties").getPath();
		
		prop = new Properties();
		
		prop.load(new FileReader(fileName));
	}

	/** 병원 : 전체 게시글 수 조회 DAO
	 * @param conn
	 * @param boardType
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, int boardType) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardType);
			rset = pstmt.executeQuery();
			
			if(rset.next()) listCount = rset.getInt(1);
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return listCount;
	}
	
	/** 병원 : 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectList(Connection conn, Page pInfo) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> bList = null;
		
		String query = prop.getProperty("selectList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null;
			bList = new ArrayList<Board>();
			
			while(rset.next()) {
				board = new Board(
						rset.getInt("BOARD_NO"), 
						rset.getString("BOARD_TITLE"), 
						rset.getInt("BOARD_LIKE_CNT"), 
						rset.getInt("BOARD_READ_CNT"), 
						rset.getString("HOS_ADDR"),
						rset.getString("BOARD_NOTICE"),
						rset.getString("HOS_NIGHT_YN"), 
						rset.getString("HOS_WEEKEND_YN"));
				
				board.setReplyCount(rset.getInt("BOARD_REPLY_CNT"));
				
				bList.add(board);
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return bList;
	}
	
	/** 병원 공지사항 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectHosBoard(Connection conn, Page pInfo) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> boardList = null;
		
		String query = prop.getProperty("selectBoardList");
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null;
			boardList = new ArrayList<Board>();
			
			while(rset.next()) {
				board = new Board(
						rset.getInt("BOARD_NO"), 
						rset.getString("BOARD_TITLE"), 
						rset.getInt("BOARD_LIKE_CNT"), 
						rset.getInt("BOARD_READ_CNT"), 
						rset.getString("HOS_ADDR"),
						rset.getString("BOARD_NOTICE"),
						rset.getString("HOS_NIGHT_YN"), 
						rset.getString("HOS_WEEKEND_YN"));
				
				board.setReplyCount(rset.getInt("BOARD_REPLY_CNT"));
				
				boardList.add(board);
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return boardList;
	}

	/** 병원 : 게시글 상세 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset= null;
		
		String query = prop.getProperty("selectBoard");
		Board board = null;
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new Board(
						boardNo,
						rset.getString("BOARD_TITLE"),
						rset.getTimestamp("BOARD_WRITE_DT"), 
						rset.getTimestamp("BOARD_MODIFY_DT"), 
						rset.getString("HOS_ADDR"), 
						rset.getString("HOS_TIME"), 
						rset.getString("HOS_NIGHT_YN"), 
						rset.getString("HOS_WEEKEND_YN"),
						rset.getString("BOARD_NOTICE"),
						rset.getString("BOARD_CONTENT"));
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return board;
	}

	/** 병원 : 조회 수 증가 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int readCount(Connection conn, int boardNo) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("readCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 병원 : 게시글 수정 화면 구성
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board updateView(Connection conn, int boardNo) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board board = null;
		
		String query = prop.getProperty("updateView");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new Board(
						rset.getString("BOARD_TITLE"), 
						rset.getTimestamp("BOARD_WRITE_DT"), 
						rset.getTimestamp("BOARD_MODIFY_DT"), 
						rset.getString("HOS_ADDR"), 
						rset.getString("HOS_TIME"), 
						rset.getString("HOS_NIGHT_YN"), 
						rset.getString("HOS_WEEKEND_YN"));
			}
		
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return board;
	}



	/** 병원 : 게시글 수정(board) DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Board board) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateHosBoard");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setInt(2, board.getBoardNo());
			
			result = pstmt.executeUpdate();			
		}finally {
			pstmt.close();
		}		
		return result;
	}

	/** 병원 : 게시글 수정(board_hos) DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateBoardHos(Connection conn, Board board) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateHos");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getHosTime());
			pstmt.setString(2, board.getHosAddress());
			pstmt.setString(3, board.getHosNightYN());
			pstmt.setString(4, board.getHosWeekenYN());
			pstmt.setInt(5, board.getBoardNo());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 병원 : 게시글 삭제 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteHosBoard(Connection conn, int boardNo) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteHosBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 병원 : 공지사항 수정 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateHosNotice(Connection conn, Board board) throws Exception{

		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateHosNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardNo());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}
	
	/** 게시글 등록 요청 DAO
	 * @param conn
	 * @param rBoard
	 * @return result
	 * @throws Exception
	 */
	public int requestBoard(Connection conn, RequestBoard rBoard) throws Exception{

		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("requestBoard");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, rBoard.getRequestType());
			pstmt.setString(2, rBoard.getRequestContent());
			pstmt.setInt(3, rBoard.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 정보 상태 수정 요청 DAO
	 * @param conn
	 * @param rBoard
	 * @return result
	 * @throws Exception
	 */
	public int updateInfo(Connection conn, RequestBoard rBoard) throws Exception{

		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateInfo");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, rBoard.getRequestType());
			pstmt.setString(2, rBoard.getRequestContent());
			pstmt.setInt(3, rBoard.getBoardNo());
			pstmt.setInt(4, rBoard.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 정보 상태 삭제 요청 DAO
	 * @param conn
	 * @param rBoard
	 * @return result
	 * @throws Exception
	 */
	public int deleteInfo(Connection conn, RequestBoard rBoard) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteInfo");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, rBoard.getRequestType());
			pstmt.setString(2, rBoard.getRequestContent());
			pstmt.setInt(3, rBoard.getBoardNo());
			pstmt.setInt(4, rBoard.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int getKListCount(Connection conn, int boardType) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int listCount = 0;
		
		String query = prop.getProperty("getKListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardType);
			rset = pstmt.executeQuery();
			
			if(rset.next()) listCount = rset.getInt(1);
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return listCount;
	}

	public List<Board> selectKList(Connection conn, Page pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> bList = null;
		
		String query = prop.getProperty("selectKList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null;
			bList = new ArrayList<Board>();
			
			while(rset.next()) {
				board = new Board(
						rset.getInt("BOARD_NO"), 
						rset.getString("BOARD_TITLE"), 
						rset.getInt("BOARD_READ_CNT"), 
						rset.getInt("COMMON_AGE"));
				bList.add(board);
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return bList;
	}
	
	public int getAgeListCount(Connection conn, int boardType, int commonAge) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int listCount = 0;
		
		String query = prop.getProperty("getAgeListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardType);
			pstmt.setInt(2, commonAge);
			rset = pstmt.executeQuery();
			
			if(rset.next()) listCount = rset.getInt(1);
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return listCount;
	}
	
	public List<Board> selectAgeList(Connection conn, Page pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> bList = null;
		
		String query = prop.getProperty("selectAgeList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, pInfo.getCommonAge());
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null;
			bList = new ArrayList<Board>();
			
			while(rset.next()) {
				board = new Board(
						rset.getInt("BOARD_NO"), 
						rset.getString("BOARD_TITLE"), 
						rset.getInt("BOARD_READ_CNT"), 
						rset.getInt("COMMON_AGE"));
				bList.add(board);
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return bList;
	}
	
	public List<Board> bestList(Connection conn, int boardType) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> bList = null;
		
		String query = prop.getProperty("bestList");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardType);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<Board>();
			Board board = null;
			while(rset.next()) {
				board = new Board();
				board.setBoardNo(rset.getInt("BOARD_NO"));
				board.setBoardTitle(rset.getString("BOARD_TITLE"));
				
				bList.add(board);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		return bList;
	}

	public int insertBoardLike(Connection conn, BoardLike boardLike) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertBoardLike");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardLike.getMemberNo());
			pstmt.setInt(2, boardLike.getBoardNo());
			pstmt.setInt(3, boardLike.getBoardType());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	public int deleteBoardLike(Connection conn, BoardLike boardLike) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteBoardLike");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardLike.getMemberNo());
			pstmt.setInt(2, boardLike.getBoardNo());
			pstmt.setInt(3, boardLike.getBoardType());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 지식게시판 게시글 상세 조회
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectKboard(Connection conn, int boardNo) throws Exception{

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectKboard");
		Board board = null;
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new Board(
						boardNo,
						rset.getString("BOARD_TITLE"),
						rset.getString("BOARD_CONTENT"),
						rset.getTimestamp("BOARD_WRITE_DT"), 
						rset.getTimestamp("BOARD_MODIFY_DT")
						);
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return board;
	}

	/** 지식게시판 게시글 수정 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateKnowledge(Connection conn, Board board) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateKnowledge");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 지식게시판 게시글 삭제 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteknowledge(Connection conn, int boardNo) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteknowledge");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}


}
