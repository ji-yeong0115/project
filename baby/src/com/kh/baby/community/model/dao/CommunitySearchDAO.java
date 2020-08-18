package com.kh.baby.community.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kh.baby.community.model.vo.Attachment;
import com.kh.baby.community.model.vo.Board;
import com.kh.baby.community.model.vo.PageInfo;

public class CommunitySearchDAO {

	/**  DAO
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
		
		String query = "SELECT COUNT(*) FROM FREE_COMMUNITY_LIST WHERE BOARD_TYPE = ? "
				+ "AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') AND " + condition;
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

	/**  DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectSearch(Connection conn, PageInfo pInfo, String condition) throws Exception{

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<Board> freeList = null;
		
		String query = "SELECT * FROM (SELECT ROWNUM RNUM, V.* FROM "
				+ "(SELECT * FROM FREE_COMMUNITY_LIST WHERE BOARD_TYPE = ? AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') AND "
				+ condition +"ORDER BY BOARD_NO DESC) V) WHERE RNUM BETWEEN ? AND ?";
		try{
			
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			freeList = new ArrayList<Board>();
			Board board = null;
			
			while(rset.next()) {

				board = new Board(rset.getInt("BOARD_NO"), 
						rset.getString("BOARD_TITLE"), 
						rset.getInt("BOARD_READ_CNT"), 
						rset.getTimestamp("BOARD_WRITE_DT"), 
						rset.getString("CATEGORY_NAME"), 
						rset.getString("MEMBER_NM"), 
						rset.getInt("BOARD_LIKE_CNT"),
						rset.getString("BOARD_NOTICE"));
				freeList.add(board); }
				
			
			
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return freeList;
	}
	
	//---------------------------------------------------
	
	
	/** 홍보게시판 글개수 얻어오기 DAO
	 * @param conn
	 * @param boardType
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int promoSearchCount(Connection conn, int boardType, String condition) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int listCount = 0;
		
		String query = "SELECT COUNT(*) FROM FREE_PROMO_LIST WHERE BOARD_TYPE = ? "
				+ "AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') AND " + condition;
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
	
	/** 홍보게시판 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> promoSelectSearch(Connection conn, PageInfo pInfo, String condition) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<Board> promoList = null;
		
		String query = "SELECT * FROM (SELECT ROWNUM RNUM, PROMO_V.* FROM"
				+ "(SELECT * FROM FREE_PROMO_LIST WHERE BOARD_TYPE=? AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') AND "
				+ condition +"  ORDER BY BOARD_NO DESC) PROMO_V)  WHERE RNUM BETWEEN ? AND ?";
		
		System.out.println(query);
		try{
			
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			promoList = new ArrayList<Board>();
			Board board = null;
			
			while(rset.next()) {
				
				board = new Board(rset.getInt("BOARD_NO"), 
						rset.getInt("SELL_AGE"), 
						rset.getString("BOARD_TITLE"), 
						rset.getString("HASHTAG"),
						rset.getString("MEMBER_NM"),
						rset.getTimestamp("BOARD_WRITE_DT"));
				
				promoList.add(board); }
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return promoList;
	}
	
	
	/** 홍보게시판 썸네일 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> promoFileList(Connection conn, PageInfo pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Attachment> fileList = null;
		
		String query = "SELECT * FROM ATTACHMENT WHERE PARENT_BOARD_NO "
				+ "IN(SELECT BOARD_NO FROM (SELECT ROWNUM RNUM, V.* "
				+ "FROM (SELECT BOARD_NO  FROM BOARD WHERE  BOARD_TYPE=? "
				+ "ORDER BY BOARD_NO DESC ) V ) WHERE RNUM BETWEEN ? AND ?) "
				+ "AND FILE_LEVEL = 0 AND FILE_STATUS='Y'";  
				

		try {
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1 ;
			int endRow = startRow+pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset=pstmt.executeQuery();
			
			fileList = new ArrayList<Attachment>();
			
			Attachment at = null;
			while (rset.next()) {
				
				at = new Attachment(rset.getInt("FILE_NO"), 
									rset.getInt("PARENT_BOARD_NO"), 
									rset.getString("FILE_CHANGE_NAME"), 
									rset.getString("FILE_PATH"))  ;
				fileList.add(at);
			}
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return fileList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
