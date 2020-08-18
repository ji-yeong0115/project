package com.kh.baby.adminPage.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.baby.adminPage.model.vo.AdminPage;
import com.kh.baby.adminPage.model.vo.Attachment;
import com.kh.baby.adminPage.model.vo.Hospital;
import com.kh.baby.adminPage.model.vo.PageInfo;
import com.kh.baby.adminPage.model.vo.RequestBoard;
import com.kh.baby.member.model.vo.Member;

public class AdminPageDAO {
	
	private Properties prop;
	
	public AdminPageDAO() throws Exception{
		String fileName = AdminPage.class.getResource("/com/kh/baby/sql/adminPage/adminPage-query.properties").getPath();
		
		prop = new Properties();
		
		prop.load(new FileReader(fileName));
	}

	/** 신고게시판, 공지사항  페이징바 DAO
	 * @param conn
	 * @param boardStatus
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String boardStatus) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int listCount = 0;
		String query = null;
		
		if(boardStatus.equals("D")) {
			query = prop.getProperty("getListCountReport");
		}else if(boardStatus.equals("Y")) {
			query = prop.getProperty("getListCountNotice");
		}
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardStatus);
			rset = pstmt.executeQuery();
			
			if(rset.next()) listCount = rset.getInt(1);
			
		}finally {
			rset.close();
			pstmt.close();
		}

		return listCount;
	}
	
	/** 신고게시판, 공지사항  일부 게시글만 조회 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public List<AdminPage> selectList(Connection conn, PageInfo pInfo, String boardStatus) throws Exception{

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<AdminPage> list = null;
		
		String query = null;
		
		if(boardStatus.equals("D")) {
			query = prop.getProperty("selectListReport");
		}else if(boardStatus.equals("Y")) {
			query = prop.getProperty("selectListNotice");
		}
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			int length=0;
			String string ="";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pInfo.getBoardStatus());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<AdminPage>();
			
			while(rset.next()) {
				
				if(rset.getString("BOARD_CONTENT").length()<30) {
					length =rset.getString("BOARD_CONTENT").length();
				}else{
					length = 30;
					string = "...";
				}
				
				if(boardStatus.equals("D")) {
					AdminPage board = new AdminPage(rset.getInt("BOARD_NO"), 
							rset.getString("BOARD_TITLE"), 
							(rset.getString("BOARD_CONTENT")).substring(0, length)+string, 
							rset.getInt("BOARD_READ_CNT"), 
							rset.getDate("BOARD_WRITE_DT"),
							rset.getString("MEMBER_NM"), 
							rset.getString("CATEGORY_NAME"),
							rset.getString("REPORT_REASON")
						);
					
					list.add(board);
					
				}else if(boardStatus.equals("Y")) {
					AdminPage board = new AdminPage(rset.getInt("BOARD_NO"), 
						   rset.getString("CATEGORY_NAME"),
						   rset.getString("BOARD_TITLE"), 
						   (rset.getString("BOARD_CONTENT")).substring(0, length)+string, 
						   rset.getString("MEMBER_NM"), 
						   rset.getInt("BOARD_READ_CNT"), 
						   rset.getDate("BOARD_MODIFY_DT")
						   );
				
				list.add(board);
				}
			}
		}finally {
			rset.close();
			pstmt.close();
		}

		return list;
	}
	
	/** 신고게시글 등록 DAO
	 * @param conn
	 * @param boardNo
	 * @param reason
	 * @return result
	 * @throws Exception
	 */
	public int insertReport(Connection conn, int boardNo, int reason) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertReport");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, reason);
			pstmt.setInt(2, boardNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}
	
	/** 신고게시글 상태 변경(N) DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int changeReport(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("changeReport");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}	

	/** 요청게시판 페이징바 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		Statement stmt = null;
		ResultSet rset = null;
		int listCount = 0;
		String query = prop.getProperty("getListCountRequest");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) listCount = rset.getInt(1);
			
		}finally {
			rset.close();
			stmt.close();
		}

		return listCount;
	}
	
	/** 요청게시판 일부게시글만 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return list
	 * @throws Exception
	 */
	public List<RequestBoard> selectList(Connection conn, PageInfo pInfo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<RequestBoard> list = null;
		
		String query = prop.getProperty("selectListRequest");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<RequestBoard>();
			
			while(rset.next()) {
				RequestBoard board = new RequestBoard(rset.getInt("REQUEST_NO"),
						rset.getString("CATEGORY_NAME"),
						rset.getString("REQUEST_CONTEN"),
						rset.getString("MEMBER_ID"),
						rset.getInt("BOARD_NO")
		                );
				
				list.add(board);
			}
		}finally {
			rset.close();
			pstmt.close();
		}

		return list;
	}

	/** 신고게시판, 요청게시판, 공지사항 썸네일 목록 조회
	 * @param conn
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectFileList(Connection conn, PageInfo pInfo, String boardStatus) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Attachment> fList = null;

		String query = null;
		
		if(boardStatus.equals("D")) {
			query = prop.getProperty("selectFileList1");
		}else if(boardStatus.equals("Y")) {
			query = prop.getProperty("selectFileList3");
		}else if(boardStatus.equals("R")) {
			boardStatus="Y";
			query = prop.getProperty("selectFileList2");
		}
		
		try {
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit() -1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pInfo.getBoardStatus());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<>();
			Attachment at = null;
			
			while(rset.next()) {
				at = new Attachment(
						rset.getInt("FILE_NO"), 
						rset.getInt("PARENT_BOARD_NO"), 
						rset.getString("FILE_CHANGE_NAME"), 
						rset.getString("FILE_PATH")
						);
				
				fList.add(at);
			}
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return fList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/** 신고게시판 게시글 수 세기 DAO
	 * @param conn
	 * @return result
	 * @throws Exception
	 */
	public int reportBoard2(Connection conn) throws Exception{
		Statement stmt = null;
		ResultSet rset = null;
		int result = 0;
		String query = prop.getProperty("reportCnt");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			if(rset.next()) {
				result = rset.getInt(1);
			}
			
		}finally {
			rset.close();
			stmt.close();
		}
		
		return result;
	}

	/** 요청게시판 게시글 수 세기 DAO
	 * @param conn
	 * @return result
	 * @throws Exception
	 */
	public int requestBoard2(Connection conn) throws Exception{
		Statement stmt = null;
		ResultSet rset = null;
		int result = 0;
		String query = prop.getProperty("requestCnt");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
			
		}finally {
			rset.close();
			stmt.close();
		}
		
		return result;
	}

	/** 게시글 상세 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public AdminPage selectBoard(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt=null;
		ResultSet rset = null;
		AdminPage adminPage =null;
		
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				adminPage = new AdminPage(rset.getInt("BOARD_NO"),
						                  rset.getString("BOARD_TITLE"),
						                  rset.getString("BOARD_CONTENT"),
						                  rset.getInt("BOARD_READ_CNT"),
						                  rset.getDate("BOARD_WRITE_DT"),
						                  rset.getDate("BOARD_MODIFY_DT"),
						                  rset.getString("MEMBER_NM"),
						                  rset.getString("CATEGORY_NAME")
						                  );
			}

		}finally {
			rset.close();
			pstmt.close();
		}
		
		return adminPage;
	}

	/** 요청게시글 상세조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public RequestBoard selectrequest(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt=null;
		ResultSet rset = null;
		RequestBoard board =null;
		
		String query = prop.getProperty("selectRequest");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				board = new RequestBoard(rset.getInt("REQUEST_NO"),
						rset.getString("CATEGORY_NAME"),
						rset.getString("REQUEST_CONTEN"),
						rset.getString("MEMBER_ID"),
						rset.getInt("BOARD_NO")
		                );
			}

		}finally {
			rset.close();
			pstmt.close();
		}
		
		return board;
	}
	/** 신고된 게시글 삭제 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int deleteReport(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}

	/** 신고된 게시글 복구 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int returnReport(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("returnBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}
	
	/** 조건에 따라 회원 조회 DAO
	 * @param conn
	 * @param type
	 * @return list
	 * @throws Exception
	 */
	public List<Member> checkView(Connection conn, String type) throws Exception {

		List<Member> list = null;
		Statement stmt = null;
		ResultSet rset = null;
		String query=null;
		
		if(type.equals("status_y")) {
			query = prop.getProperty("statusYView");			
		}else if(type.equals("status_n")) {
			query = prop.getProperty("statusNView");
		}else if(type.equals("grad_g")) {
			query = prop.getProperty("gradGView");
		}else if(type.equals("grad_s")) {
			query = prop.getProperty("gradSView");
		}
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			list = new ArrayList<Member>();
			while(rset.next()) {
				Member member = new Member(rset.getInt("MEMBER_NO"), 
						                   rset.getString("MEMBER_ID"), 
						                   rset.getString("MEMBER_NM"), 
						                   rset.getString("MEMBER_NICK"), 
						                   rset.getString("MEMBER_EMAIL"), 
						                   rset.getString("MEMBER_ADDR"), 
						                   rset.getDate("MEMBER_ENROLL_DATE"), 
						                   rset.getString("MEMBER_STATUS"), 
						                   rset.getString("MEMBER_GRADE"));
				
				list.add(member);
			}
		}finally {
			rset.close();
			stmt.close();
		}
		return list;
	}
	
	/** 조회수 증가 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseCount(Connection conn, int boardNo) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		return result;
	}




	

	/** 전체 회원 조회 페이징바 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCountM(Connection conn) throws Exception{
		Statement stmt = null;
		ResultSet rset = null;
		int listCount = 0;
		String query = prop.getProperty("getListCountM");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) listCount = rset.getInt(1);
			
		}finally {
			rset.close();
			stmt.close();
		}

		return listCount;
	}

	/** 전체 회원 조회 일부게시글만 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return list
	 * @throws Exception
	 */
	public List<Member> selectListM(Connection conn, PageInfo pInfo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = null;
		
		String query = prop.getProperty("selectListM");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				Member member = new Member(rset.getInt("MEMBER_NO"), 
		                   rset.getString("MEMBER_ID"), 
		                   rset.getString("MEMBER_NM"), 
		                   rset.getString("MEMBER_NICK"), 
		                   rset.getString("MEMBER_EMAIL"), 
		                   rset.getString("MEMBER_ADDR"), 
		                   rset.getDate("MEMBER_ENROLL_DATE"), 
		                   rset.getString("MEMBER_STATUS"), 
		                   rset.getString("MEMBER_GRADE"));
				
				list.add(member);
			
				}
		}finally {
			rset.close();
			pstmt.close();
		}

		return list;
	}

	public int getListCountC(Connection conn, String type) throws Exception {

		Statement stmt = null;
		ResultSet rset = null;
		String query = null;
		int listCount = 0;
		
		if(type.equals("status_y")) {
			query = prop.getProperty("statusYView");			
		}else if(type.equals("status_n")) {
			query = prop.getProperty("statusNView");
		}else if(type.equals("grad_g")) {
			query = prop.getProperty("gradGView");
		}else if(type.equals("grad_s")) {
			query = prop.getProperty("gradSView");
		}else if(type.equals("0")) {
			query = "SELECT COUNT(*) FROM MEMBER ORDER BY MEMBER_NO";
		}
		
		try {
			
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) listCount = rset.getInt(1);
			
		}finally {
			rset.close();
			stmt.close();
		}

		return listCount;
	}

	public List<Member> selectListC(Connection conn, PageInfo pInfo, String type) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = null;
		String query = null;
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			if(type.substring(0,6).equals("status")) {
				query = prop.getProperty("selectListC1");
				if(type.equals("status_y")) {
					type="Y";
				}else if(type.equals("status_n")) {
					type="N";
				}
			}else {
				query = prop.getProperty("selectListC2");
				if(type.equals("grad_g")) {
					type="G";
				}else if(type.equals("grad_s")) {
					type="S";
				}
			}
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,type);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				Member member = new Member(rset.getInt("MEMBER_NO"), 
		                   rset.getString("MEMBER_ID"), 
		                   rset.getString("MEMBER_NM"), 
		                   rset.getString("MEMBER_NICK"), 
		                   rset.getString("MEMBER_EMAIL"), 
		                   rset.getString("MEMBER_ADDR"), 
		                   rset.getDate("MEMBER_ENROLL_DATE"), 
		                   rset.getString("MEMBER_STATUS"), 
		                   rset.getString("MEMBER_GRADE"));
				
				list.add(member);
				}
		}finally {
			rset.close();
			pstmt.close();
		}

		return list;
	}

	public int selectNextNo(Connection conn) throws Exception{
		Statement stmt = null;
		ResultSet rset = null;
		int boardNo = 0;
		String query = prop.getProperty("selectNextNo");
		// 
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				boardNo = rset.getInt(1);
			}
		}finally {
			rset.close();
			stmt.close();
		}
		
		return boardNo;
	}

	public List<Attachment> selectFiles(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Attachment> fList =null;
		String query = prop.getProperty("selectFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset= pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			Attachment file = null;
			
			while(rset.next()) {
				file = new Attachment();
				file.setFileNo(rset.getInt("FILE_NO"));
				file.setFileChangeName(rset.getString("FILE_CHANGE_NAME"));
				file.setFilePath(rset.getString("FILE_PATH"));
				file.setFileLevel(rset.getInt("FILE_LEVEL"));
				
				fList.add(file);
			}
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return fList;
	}

	public int checkRequest(Connection conn, int parentNo) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteHos");
		
		try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, parentNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int deleteRequest(Connection conn, int boardNo) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteRequest");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int getSearchCount(Connection conn, String boardType, String condition) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int listCount = 0;
		String query = null;
		
		if(boardType.equals("D")) {
			query = "SELECT COUNT(*) FROM BOARD JOIN MEMBER USING(MEMBER_NO) JOIN B_CATEGORY ON(BOARD.BOARD_TYPE=B_CATEGORY.CATEGORY_NO) WHERE BOARD_STATUS=? AND " + condition;
		}else if(boardType.equals("Y")) {
			query = "SELECT COUNT(*) FROM BOARD JOIN MEMBER USING(MEMBER_NO) JOIN B_CATEGORY ON(BOARD.BOARD_TYPE=B_CATEGORY.CATEGORY_NO) WHERE BOARD_NOTICE=? AND BOARD_STATUS='Y' AND "+ condition;
		}else if(boardType.equals("R")) {
			boardType="Y";
			query = "SELECT COUNT(*) FROM BOARD_REQUEST JOIN MEMBER USING(MEMBER_NO) WHERE REQUEST_STATUS=? AND "+ condition;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardType);
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

	public List<AdminPage> selctSearch(Connection conn, PageInfo pInfo, String condition) throws Exception{

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<AdminPage> list = null;
		
		String query = null;
		
		if(pInfo.getBoardStatus().equals("D")) {
			query = "SELECT * FROM(SELECT ROWNUM RNUM, V.* FROM(SELECT * FROM BOARD JOIN MEMBER USING(MEMBER_NO) JOIN B_CATEGORY ON(BOARD.BOARD_TYPE=B_CATEGORY.CATEGORY_NO) WHERE BOARD_STATUS=? AND "+ condition+ "ORDER BY BOARD_NO DESC) V ) WHERE RNUM BETWEEN ? AND ?";
		}else if(pInfo.getBoardStatus().equals("Y")) {
			query = "SELECT * FROM(SELECT ROWNUM RNUM, V.* FROM(SELECT * FROM BOARD JOIN MEMBER USING(MEMBER_NO) JOIN B_CATEGORY ON(BOARD.BOARD_TYPE=B_CATEGORY.CATEGORY_NO) WHERE BOARD_NOTICE=? AND BOARD_STATUS='Y' AND "+ condition+ "ORDER BY BOARD_NO DESC) V ) WHERE RNUM BETWEEN ? AND ?";
		}
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			int length=0;
			String string ="";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, pInfo.getBoardStatus());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<AdminPage>();
			AdminPage board = null;
			
			while(rset.next()) {
				if(rset.getString("BOARD_CONTENT").length()<30) {
					length =rset.getString("BOARD_CONTENT").length();
				}else{
					length = 30;
					string = "...";
				}
				
				board = new AdminPage(rset.getInt("BOARD_NO"), 
						   rset.getString("CATEGORY_NAME"),
						   rset.getString("BOARD_TITLE"), 
						   (rset.getString("BOARD_CONTENT")).substring(0, length)+string, 
						   rset.getString("MEMBER_NM"), 
						   rset.getInt("BOARD_READ_CNT"), 
						   rset.getDate("BOARD_MODIFY_DT")
						   );
				list.add(board);
			}
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return list;
	}

	public List<RequestBoard> selctSearchR(Connection conn, PageInfo pInfo, String condition) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<RequestBoard> list = null;
		 
		String query = "SELECT * FROM(SELECT ROWNUM RNUM, V.* FROM(SELECT * FROM BOARD_REQUEST JOIN MEMBER USING(MEMBER_NO) JOIN R_CATEGORY ON(BOARD_REQUEST.REQUEST_TYPE=R_CATEGORY.CATEGORY_NO) WHERE REQUEST_STATUS='Y' AND "+ condition+" ORDER BY REQUEST_NO DESC) V ) WHERE RNUM BETWEEN ? AND ?";
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<RequestBoard>();
			
			while(rset.next()) {
				RequestBoard board = new RequestBoard(rset.getInt("REQUEST_NO"),
						rset.getString("CATEGORY_NAME"),
						rset.getString("REQUEST_CONTEN"),
						rset.getString("MEMBER_ID"),
						rset.getInt("BOARD_NO")
		                );
				
				list.add(board);
			}
		}finally {
			rset.close();
			pstmt.close();
		}

		return list;
	}

	public Hospital updateHos(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Hospital hospital = null;
		 
		String query = prop.getProperty("updateHos");
		
		pstmt=conn.prepareStatement(query);
		pstmt.setInt(1, boardNo);
		rset = pstmt.executeQuery();
		
		if(rset.next()) {
			hospital = new Hospital(rset.getInt("BOARD_NO"),
					rset.getString("BOARD_TITLE"),
					rset.getString("HOS_ADDR"),
					rset.getString("HOS_TIME"),
					rset.getString("HOS_NIGHT_YN"),
					rset.getString("HOS_WEEKEND_YN")
					);			
		}
		try {
			
		}finally {
			rset.close();
			pstmt.close();
		}
		return hospital;
	}

	public int updateBoard(Connection conn, Hospital hospital) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateHos1");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, hospital.getBoardTitle());
			pstmt.setInt(2, hospital.getBoardNo());
			
			result = pstmt.executeUpdate();			
		}finally {
			pstmt.close();
		}		
		return result;
	}
	
	public int updateBoardHos(Connection conn, Hospital hospital) throws Exception{

		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateHos2");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, hospital.getHosTime());
			pstmt.setString(2, hospital.getHosAddr());
			pstmt.setString(3, hospital.getHosNight());
			pstmt.setString(4, hospital.getHosWeekend());
			pstmt.setInt(5, hospital.getBoardNo());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int insertBoard(Connection conn, AdminPage board) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());;
			pstmt.setInt(3, board.getBoardType());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}
	

	public int insertHos(Connection conn, Hospital hospital) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertHos");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, hospital.getHosAddr());
			pstmt.setString(2, hospital.getHosTime());
			pstmt.setString(3, hospital.getHosNight());
			pstmt.setString(4, hospital.getHosWeekend());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int insertKnow(Connection conn, AdminPage adminpage) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertKnow");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, adminpage.getBoardTitle());
			pstmt.setString(2, adminpage.getBoardContent());
			pstmt.setInt(3, adminpage.getBoardType());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int knowCommon(Connection conn, int select) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("knowCommon");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, select);
			
			result=pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}
	
	public int insertNotice(Connection conn, String title, String content, int type) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, type);
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int selectBoardNo(Connection conn) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectBoardNo");
		
		try {
			pstmt = conn.prepareStatement(query);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				result=rset.getInt(1);
			}
		}finally {
			pstmt.close();
			rset.close();
		}
		
		return result;
	}


	public int deleteNotice(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}


	public int deleteReport2(Connection conn, int boardNo) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteReport2");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}
	
	public int getSearchCountM(Connection conn, String condition) throws Exception {

		Statement stmt = null;
		ResultSet rset = null;
		int result = 0;
		String query = "SELECT COUNT(*) FROM MEMBER WHERE " + condition;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			if(rset.next()) {
				result = rset.getInt(1);
			}
			
		}finally {
			rset.close();
			stmt.close();
		}

		return result;
	}

	public List<Member> selctSearchM(Connection conn, PageInfo pInfo, String condition) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = null;
		
		String query = "SELECT * FROM(SELECT ROWNUM RNUM, V.* FROM(SELECT * FROM MEMBER WHERE "+ condition+  " ORDER BY MEMBER_NO) V ) WHERE RNUM BETWEEN ? AND ?";
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Member>();
			Member member = null;
			
			while(rset.next()) {
				member = new Member(rset.getInt("MEMBER_NO"), 
		                   rset.getString("MEMBER_ID"), 
		                   rset.getString("MEMBER_NM"), 
		                   rset.getString("MEMBER_NICK"), 
		                   rset.getString("MEMBER_EMAIL"), 
		                   rset.getString("MEMBER_ADDR"), 
		                   rset.getDate("MEMBER_ENROLL_DATE"), 
		                   rset.getString("MEMBER_STATUS"), 
		                   rset.getString("MEMBER_GRADE"));

						   list.add(member);
						}

			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return list;
	}

}
