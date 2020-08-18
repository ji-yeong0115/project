package com.kh.baby.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.Page;

public class SearchKnowDAO {

	/** 검색 내용이 포함된 게시글 수 조회 DAO
	 * @param conn
	 * @param boardType
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getSearchCount(Connection conn, int boardType, String condition) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int listCount = 0;
		
		String query = "SELECT COUNT(*) FROM V_KNOW_LIST WHERE BOARD_TYPE = ? "
				+ "AND BOARD_STATUS='Y' AND " + condition;
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, boardType);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return listCount;
	}

	/** 검색 내용이 포함된 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectSearch(Connection conn, Page pInfo, String condition) throws Exception{

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<Board> bList = null;
		
		String query = "SELECT * FROM (SELECT ROWNUM RNUM, V.* FROM "
				+ "(SELECT * FROM V_KNOW_LIST WHERE BOARD_TYPE = ? AND BOARD_STATUS ='Y' AND "
				+ condition +"ORDER BY BOARD_NO DESC) V) WHERE RNUM BETWEEN ? AND ?";
		
		try{
			
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<Board>();
			Board board = null;
			
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

}
