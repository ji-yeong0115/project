package com.kh.baby.community.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.baby.community.model.vo.Attachment;
import com.kh.baby.community.model.vo.Board;
import com.kh.baby.community.model.vo.BoardLike;
import com.kh.baby.community.model.vo.PageInfo;

public class CommunityDAO{
	
	private Properties prop;
	
	public CommunityDAO() throws Exception {
		String fileName 
		= CommunityDAO.class.getResource("/com/kh/baby/sql/board/community-query.properties").getPath();
		
		prop = new Properties();
		
		prop.load(new FileReader(fileName));
	}

	/** 다음 게시글 번호 반환 DAO
	 * @param conn
	 * @return boardNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception{
		Statement stmt = null; 
		ResultSet rset = null;
		int boardNo=0;
		String query = prop.getProperty("selectNextNo");
		// selectNextNo=SELECT SEQ_BNO.NEXTVAL FROM DUAL
		try {
			stmt=conn.createStatement();
			rset=stmt.executeQuery(query);
			
			if (rset.next()) {
				boardNo=rset.getInt(1);
			}
			
		} finally {
			rset.close();
			stmt.close();
		}
		
		return boardNo;
	}
	/** 카테고리 넘버 받아오기 DAO
	 * @param conn
	 * @return categoryNo
	 * @throws Exception
	 */
	public int selectCategoryNo(Connection conn,Board board) throws Exception{
		PreparedStatement pstmt=null;
		ResultSet rset = null;
		int categoryNo=0;
		
		String query = prop.getProperty("selectCategoryNo");
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, board.getCategoryName());

			rset=pstmt.executeQuery();
			
			if (rset.next()) {
				categoryNo=rset.getInt(1);
			}
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return categoryNo;
	}


	/** 게시글 삽입 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Board board) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertBoard");
		try {
			pstmt = conn.prepareStatement(query);
					
			pstmt.setInt(1, board.getBoardNo());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3,  board.getBoardContent());
			pstmt.setString(4,  board.getMemberId());
			pstmt.setInt(5, board.getBoardType());
			
			result = pstmt.executeUpdate();
			
		} finally {
			pstmt.close();
		}
				
		return result;
	}
	
	/** 자유게시판 (community_Free) 삽입용 dao 
	 * @param conn
	 * @param boardNo
	 * @param board
	 * @return result 
	 * @throws Exception
	 */
	public int insertFreeBoard(Connection conn, int boardNo, int categoryNo) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertFreeBoard");
  
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, categoryNo);
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result ;
	}
	
	

	/** 파일 등록 dao 
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertAttachment");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, at.getParentBoardNo());
			pstmt.setString(2, at.getFileOriginName());
			pstmt.setString(3, at.getFileChangeName());
			pstmt.setString(4, at.getFilePath());
			pstmt.setInt(5, at.getFileLevel());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
			
		}
		return result;
	}

	/** 자유게시판 전체 게시글 수 조회 dao
	 * @param conn
	 * @param boardType
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, int boardType)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardType);
			rs = pstmt.executeQuery();
				
			if (rs.next()) listCount = rs.getInt(1); 
		
			
		} finally {
			rs.close();
			pstmt.close();
		}
		
		
		return listCount;
	}
	
	
	/** 자유게시판 전체 게시글 수 조회 dao
	 * @param conn
	 * @param boardType
	 * @return listCount
	 * @throws Exception
	 */
	public int getSortListCount(Connection conn, int boardType, String sortType)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int listCount = 0;
		
		String query = prop.getProperty("getSortListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardType);
			pstmt.setString(2, sortType);
			rs = pstmt.executeQuery();
			
			if (rs.next()) listCount = rs.getInt(1); 
			
			
		} finally {
			rs.close();
			pstmt.close();
		}
		return listCount;
	}
	
	/** 홍보게시판 전체 게시글 수 조회 dao
	 * @param conn
	 * @param boardType
	 * @return listCount
	 * @throws Exception
	 */
	public int getPromoCount(Connection conn, int boardType)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int listCount = 0;
		
		String query = prop.getProperty("getPromoCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardType);
			rs = pstmt.executeQuery();
			
			if (rs.next()) listCount = rs.getInt(1); 
			
			
		} finally {
			rs.close();
			pstmt.close();
		}
		
		
		return listCount;
	}

	/** 게시글 목록 조회 DAO 자유게시판 
	 * @param conn
	 * @param pInfo
	 * @return freeList
	 * @throws Exception
	 */
	public List<Board> selectList(Connection conn, PageInfo pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> freeList= null;
		
		String query = prop.getProperty("selectList");
		try {
	
			
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1 ;
			
			int endRow = startRow+pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null ; //임시로 사용할 참조변수
			freeList = new ArrayList<Board>();
			
			while (rset.next()) {
			
				board = new Board(rset.getInt("BOARD_NO"), 
						rset.getString("BOARD_TITLE"), 
						rset.getInt("BOARD_READ_CNT"), 
						rset.getTimestamp("BOARD_WRITE_DT"), 
						rset.getString("CATEGORY_NAME"), 
						rset.getString("MEMBER_NM"), 
						rset.getInt("BOARD_LIKE_CNT"),
						rset.getString("BOARD_NOTICE"));
				freeList.add(board); }
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return freeList;
	}
	
	
	/** 게시글 목록 조회 DAO 홍보게시판 
	 * @param conn
	 * @param pInfo
	 * @return freeList
	 * @throws Exception
	 */
	public List<Board> promoSelectList(Connection conn, PageInfo pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> promoList= null;
		
		String query = prop.getProperty("promoSelectList");
		try {
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1 ;
			int endRow = startRow+pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			
			
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null ; //임시로 사용할 참조변수
			promoList = new ArrayList<Board>();
			
			while (rset.next()) {
				board = new Board(rset.getInt("BOARD_NO"), 
						rset.getInt("SELL_AGE"), 
						rset.getString("BOARD_TITLE"), 
						rset.getString("HASHTAG"),
						rset.getString("MEMBER_NM"),
						rset.getTimestamp("BOARD_WRITE_DT"));
				
				promoList.add(board); }
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return promoList;
	}

	/** 자유게시글 상세 조회  dao 
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board board = null;
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
							    		  
			     board = new Board(rset.getInt("BOARD_NO"), 
			    		  rset.getString("BOARD_TITLE"), 
			    		  rset.getString("BOARD_CONTENT"), 
			    		  rset.getString("MEMBER_NM"), 
			    		  rset.getInt("BOARD_READ_CNT"), 
			    		  rset.getTimestamp("BOARD_WRITE_DT"), 
			    		  rset.getTimestamp("BOARD_MODIFY_DT"), 
			    		  rset.getString("CATEGORY_NAME"), 
			    		  rset.getInt("BOARD_LIKE_CNT"),
			    		  rset.getString("MEMBER_ID"));		
			}
			
		} finally {
			rset.close();
			pstmt.close();
		}
		return board;
	}

	
	/** 홍보게시글 상세 조회  dao 
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board promoSelectBoard(Connection conn, int boardNo) throws Exception  {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board board = null;
		String query = prop.getProperty("promoSelectBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				board = new Board(boardNo, 
						rset.getString("BOARD_TITLE"), 
						rset.getString("BOARD_CONTENT"), 
						rset.getInt("BOARD_READ_CNT"), 
						rset.getTimestamp("BOARD_WRITE_DT"), 
						rset.getInt("BOARD_LIKE_CNT"),
						rset.getString("MEMBER_NM"), 
						rset.getInt("SELL_AGE"), 
						rset.getString("HASHTAG"),
						rset.getInt("SELL_PRICE"),
						rset.getString("SELL_INTRO"),
						rset.getString("MEMBER_ID")
						);
			}
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return board;
	}
	
	
	/** 게시글에 포함된 이미지 조회 dao 
	 * @param conn
	 * @param boardNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectFiles(Connection conn, int boardNo)  throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Attachment> fList = null;
		String query = prop.getProperty("selectFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			fList = new ArrayList<Attachment>();
			
			Attachment file = null ; //반복문 돌면서 넣어줄
			
			while(rset.next()) {
				file = new Attachment();
				file.setFileNo(rset.getInt("FILE_NO"));
				file.setFileChangeName(rset.getString("FILE_CHANGE_NAME"));
				file.setFilePath(rset.getString("FILE_PATH"));
				file.setFileLevel(rset.getInt("FILE_LEVEL"));
				
				fList.add(file);
			}
			
		} finally {
			rset.close();
			pstmt.close();
		}
			return fList;
		}

	
	
	/** 홍보게시판 community_sell 게시글 삽입 dao 
	 * @param conn
	 * @param boardNo
	 * @param categoryNo
	 * @return
	 * @throws Exception
	 */
	public int insertPromoBoard(Connection conn, int boardNo, int categoryNo, Board board) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertPromoBoard");

  
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, board.getHashtag());
			pstmt.setString(3, board.getCategoryName());
			pstmt.setInt(4, board.getPrice());
			pstmt.setString(5, board.getSellIntro());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result ;
	}

	/** 자유게시판 글 수정용 dao 
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board freeUpdateView(Connection conn, int boardNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board board = null;
		String query = prop.getProperty("freeUpdateView");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				
				board = new Board(boardNo, rset.getString("BOARD_TITLE"),
								rset.getString("BOARD_CONTENT"), 
								 rset.getString("CATEGORY_NAME"));
			}
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return board;
	}

	/** 게시판 게시글 수정 dao 
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Board board)  throws Exception {
		PreparedStatement pstmt = null;
		int result=0;
		String query = prop.getProperty("updateBoard");

		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			pstmt.close();
		}
		return result;
	}

	/** 자유게시판 용, 게시글 수정 dao 
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int freeUpdateBoard(Connection conn, Board board)  throws Exception {
		PreparedStatement pstmt = null;
		int result=0;
		String query = prop.getProperty("freeUpdateBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, board.getCategoryName());
			pstmt.setInt(2, board.getBoardNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			pstmt.close();
		}
		
		return result;
	}
	
	/** 파일 정보 수정하는 dao 
	 * @param conn
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
	public int updateAttachment(Connection conn, Attachment newFile) throws Exception {
		PreparedStatement pstmt = null;
		int result=0;
		String query = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newFile.getFileOriginName());
			pstmt.setString(2, newFile.getFileChangeName());
			pstmt.setString(3, newFile.getFilePath());
			pstmt.setInt(4, newFile.getFileNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 게시글 삭제dao
	 * @param conn
	 * @param boardNo, boardType
	 * @return result 
	 * @throws Exception
	 */
	public int deleteBoard(Connection conn, int boardNo) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 홍보게시판 글 수정용 dao 
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board promoUpdateView(Connection conn, int boardNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board board = null;
		String query = prop.getProperty("promoUpdateView");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				
				board = new Board(boardNo, rset.getString("BOARD_TITLE"),
								rset.getString("BOARD_CONTENT"), 
								rset.getInt("SELL_AGE"),
								rset.getString("HASHTAG"),
								rset.getInt("SELL_PRICE"),
								rset.getString("SELL_INTRO")); 
			}
			
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return board;
	}

	/** 홍보 게시판 수정 dao 
	 * @param conn
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public int promoUpdateBoard(Connection conn, Board board) throws Exception {
		PreparedStatement pstmt = null;
		int result=0;
		String query = prop.getProperty("promoUpdateBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, board.getHashtag());
			pstmt.setInt(2, board.getSellAge());
			pstmt.setInt(3, board.getPrice());
			pstmt.setString(4, board.getSellIntro());
			pstmt.setInt(5, board.getBoardNo());
			
			result = pstmt.executeUpdate();

		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 홍보게시판 썸네일 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectFileList(Connection conn, PageInfo pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Attachment> fileList = null;
		
		String query = prop.getProperty("selectFileList");

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

	/** 조회수 증가 dao
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int increaseCount(Connection conn, int boardNo) throws Exception{
	PreparedStatement pstmt =null;
		
		int result = 0;
		String query = prop.getProperty("increaseCount");
		
		try {
			
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			result=pstmt.executeUpdate();
			
		} finally {
			pstmt.close();
		}
		
		
		return result;
	}

	
	
	/** 좋아요 dao  (좋아요 추가)
	 * @param conn
	 * @param boardLike
	 * @return
	 * @throws Exception
	 */
	public int insertBoardLike(Connection conn, Board board) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertBoardLike");
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, board.getBoardNo());
			result=pstmt.executeUpdate();
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 자유게시판 분류 후 글개수 얻어오기 dao 
	 * @param conn
	 * @param boardType
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getSearchCount(Connection conn, int boardType, String condition) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int listCount = 0;
		String query=null;
		
		if (condition.contains("1")) {
		
		query = "SELECT COUNT(*) FROM FREE_COMMUNITY_LIST WHERE BOARD_TYPE = ? AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') " ;
			
		} else {
		query = "SELECT COUNT(*) FROM FREE_COMMUNITY_LIST WHERE BOARD_TYPE = ? "
				+ "AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') AND " + condition;
		
		}
		
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

	/** 자유게시판 분류 후 목록 조회 dao
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Board> freeSelectSearch(Connection conn, PageInfo pInfo, String condition) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<Board> freeList = null;
		
		String query = null;
		
		if(condition.contains("1")) {
			query = "SELECT * FROM (SELECT ROWNUM RNUM, V.* FROM "
					+ "(SELECT * FROM FREE_COMMUNITY_LIST WHERE BOARD_TYPE = ? AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D')"
					+ " ORDER BY BOARD_NO DESC) V) WHERE RNUM BETWEEN ? AND ?";
			
		}else {
		query = "SELECT * FROM (SELECT ROWNUM RNUM, V.* FROM "
				+ "(SELECT * FROM FREE_COMMUNITY_LIST WHERE BOARD_TYPE = ? AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') AND "
				+ condition +"ORDER BY BOARD_NO DESC) V) WHERE RNUM BETWEEN ? AND ?";
		}
		
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
	
	
	/** 홍보게시판 분류 후 글개수 얻어오기 dao 
	 * @param conn
	 * @param boardType
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int promoFilterCount(Connection conn, int boardType, String condition) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int listCount = 0;
		String query = null;
		
		
		
		if (condition.equals("1")) {
			 query = "SELECT COUNT(*) FROM FREE_PROMO_LIST WHERE BOARD_TYPE = ? "
						+ "AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') " ;
			
			
		} else {  query = "SELECT COUNT(*) FROM FREE_PROMO_LIST WHERE BOARD_TYPE = ? "
				+ "AND (BOARD_STATUS ='Y' OR BOARD_STATUS='D') AND " + condition; }
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
	
	/** 홍보게시판 분류 후 목록 조회 dao
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Board> promoSelectFilter(Connection conn, PageInfo pInfo, String condition) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<Board> promoList = null;
		String query = null;
		
		if (condition.equals("1")) {
			query = "SELECT * FROM (SELECT ROWNUM RNUM, V.* FROM "
					+ "(SELECT * FROM FREE_PROMO_LIST WHERE BOARD_TYPE = ? AND BOARD_STATUS ='Y' "
					+"ORDER BY BOARD_NO DESC) V) WHERE RNUM BETWEEN ? AND ?";
		} else {
		
		query = "SELECT * FROM (SELECT ROWNUM RNUM, V.* FROM "
				+ "(SELECT * FROM FREE_PROMO_LIST WHERE BOARD_TYPE = ? AND BOARD_STATUS ='Y' AND "
				+ condition +"ORDER BY BOARD_NO DESC) V) WHERE RNUM BETWEEN ? AND ?"; }
		
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
			
			while (rset.next()) {
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

	
	/** 자유게시판 필터 적용 후 목록 조회 dao
	 * @param conn
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<Board> freeSortList(Connection conn, PageInfo pInfo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> freeList= null;
		
		String query = prop.getProperty("freeSortList");
		
		try {
	
			
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1 ;
			
			int endRow = startRow+pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			
			
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setString(2, pInfo.getSortType());
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null ; //임시로 사용할 참조변수
			freeList = new ArrayList<Board>();
			
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
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return freeList;
	}

	// ----------------------------------홍보게시판 분류 필터 적용 후 페이징바 유지 ------------------ 
	/** 홍보게시판  분류 필터 적용 후 페이징바 이동 시 전체 게시글 수 조회 dao
	 * @param conn
	 * @param boardType
	 * @return listCount
	 * @throws Exception
	 */
	public int getPromoSortCount(Connection conn, int boardType, String sortType)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int listCount = 0;
		
		int sellAge = 0 ;
		String query = null;
		
		
		try {
			
			if (sortType==null ) {
				
				query = prop.getProperty("getPromoCount");
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, boardType);
				rs = pstmt.executeQuery();
				
				if (rs.next()) listCount = rs.getInt(1); 
				
			} else {
				
				sellAge = Integer.parseInt(sortType);
				
				query = prop.getProperty("getPromoSortCount");
				
				
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, boardType);
					pstmt.setInt(2, sellAge);
					rs = pstmt.executeQuery();
					
					if (rs.next()) listCount = rs.getInt(1); 
			}
			
		} finally {
			rs.close();
			pstmt.close();
		}
		
		
		return listCount;
	}

	/** 홍보 게시판  분류 필터 적용 후 페이징바 이동 게시글 목록 조회 DAO  
	 * @param conn
	 * @param pInfo
	 * @return freeList
	 * @throws Exception
	 */
	public List<Board> promoSortList(Connection conn, PageInfo pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> promoList= null;
		
		int sellAge = Integer.parseInt(pInfo.getSortType());
		
		String query = prop.getProperty("promoSortList");
		try {
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1 ;
			int endRow = startRow+pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			
			
			pstmt.setInt(1, pInfo.getBoardType());
			pstmt.setInt(2, sellAge);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null ; //임시로 사용할 참조변수
			promoList = new ArrayList<Board>();
			
			while (rset.next()) {
				board = new Board(rset.getInt("BOARD_NO"), 
						rset.getInt("SELL_AGE"), 
						rset.getString("BOARD_TITLE"), 
						rset.getString("HASHTAG"),
						rset.getString("MEMBER_NM"),
						rset.getTimestamp("BOARD_WRITE_DT"));
				
				promoList.add(board); }
			
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return promoList;
	}

	/** 홍보게시판 분류 필터 적용 후 페이징바 이동 썸네일 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> promoSortFileList(Connection conn, PageInfo pInfo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Attachment> fileList = null;
		
		int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1 ;
		int endRow = startRow+pInfo.getLimit()-1;

		
		String query = prop.getProperty("promoSortFileList");
		try {
			
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
