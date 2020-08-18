package com.kh.baby.adminPage.model.service;
import static com.kh.baby.common.DBCP.getConnection;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import com.kh.baby.adminPage.model.dao.AdminPageDAO;
import com.kh.baby.adminPage.model.vo.AdminPage;
import com.kh.baby.adminPage.model.vo.Attachment;
import com.kh.baby.adminPage.model.vo.Hospital;
import com.kh.baby.adminPage.model.vo.PageInfo;
import com.kh.baby.adminPage.model.vo.RequestBoard;
import com.kh.baby.adminPage.model.vo.ReportBoard;
import com.kh.baby.board.model.vo.Board;
import com.kh.baby.member.model.vo.Member;

public class AdminPageService {
	private AdminPageDAO dao;
	
	public AdminPageService() throws Exception{
		dao = new AdminPageDAO();
	}
	
	/** 신고게시판, 공지사항 페이징바 Service
	 * @param currentPage
	 * @param boardStatus
	 * @return PageInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String currentPage, String boardStatus) throws Exception{
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);
		
		int listCount = dao.getListCount(conn, boardStatus);

		conn.close();
		
		return new PageInfo(cp, listCount, boardStatus);
	}

	/** 신고게시판, 공지사항 일부 게시글만 조회 Service
	 * @param pInfo
	 * @param boardStatus
	 * @return list
	 * @throws Exception
	 */
	public List<AdminPage> selectList(PageInfo pInfo, String boardStatus) throws Exception{
		Connection conn = getConnection();
		
		List<AdminPage> list = dao.selectList(conn, pInfo, boardStatus);
				
		conn.close();
		
		return list;
	}
	
	/** 신고게시글 등록 Service
	 * @param boardNo
	 * @param reason
	 * @return result
	 * @throws Exception
	 */
	public int insertReport(int boardNo, int reason) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertReport(conn, boardNo, reason);
		
		if(result>0) {
			result = dao.changeReport(conn, boardNo);
			conn.commit();
		}else {
			conn.rollback();
		}
		
		conn.close();
		
		return result;
	}

	/** 요청게시판 페이징바 Service
	 * @param currentPage
	 * @return PageInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String currentPage) throws Exception {
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);
		
		int listCount = dao.getListCount(conn);

		conn.close();
		
		return new PageInfo(cp, listCount);
	}
	
	/** 요청게시판 일부 게시글만  조회 Service
	 * @param pInfo
	 * @return list
	 * @throws Exception
	 */
	public List<RequestBoard> selectList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		List<RequestBoard> list = dao.selectList(conn, pInfo);
		
		conn.close();
		
		return list;
	}

	/**신고게시판, 요청게시판, 공지사항  썸네일 목록 조회 Service
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectFileList(PageInfo pInfo, String boardStatus) throws Exception {
		Connection conn = getConnection();
		
		List<Attachment> fList = dao.selectFileList(conn, pInfo, boardStatus);
		
		conn.close();
				
		return fList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 신고게시판 게시글 수 세기 Service
	 * @return result
	 * @throws Exception
	 */
	public int reportBoard2() throws Exception{
		Connection conn = getConnection();
		
		int result = dao.reportBoard2(conn);
		
		conn.close();
		
		return result;
	}

	/** 요청게시판 게시글 수 세기 Service
	 * @return result
	 * @throws Exception
	 */
	public int requestBoard2() throws Exception{
		Connection conn = getConnection();
		
		int result = dao.requestBoard2(conn);
		
		conn.close();
		
		return result;
	}

	/** 게시글 상세 조회 Service
	 * @param boardNo
	 * @return adminPage
	 * @throws Exception
	 */
	public AdminPage selectBoard(int boardNo) throws Exception {
		Connection conn = getConnection();
		
		AdminPage adminPage = dao.selectBoard(conn, boardNo);
		
		if(adminPage != null) {
			int result = dao.increaseCount(conn, boardNo);
			
			if(result > 0) {
				conn.commit();
				
				// 반환되는 board에 저장된 조회수를 DB와 맞게 1 증가
				adminPage.setReadCount(adminPage.getReadCount()+1);
				
			}else {
				conn.rollback();
			}
		}
		conn.close();
		
		return adminPage;
	}

	/** 요청게시글 상세조회
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public RequestBoard selectRequest(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		RequestBoard board = dao.selectrequest(conn, boardNo);
		
		if(board != null) {
			int result = dao.increaseCount(conn, boardNo);
			
			if(result > 0) {
				conn.commit();
				
				// 반환되는 board에 저장된 조회수를 DB와 맞게 1 증가
				board.setReadCount(board.getReadCount()+1);
				
			}else {
				conn.rollback();
			}
		}
		conn.close();
		
		return board;
	}
	/** 신고된 게시글 삭제 Service
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteReport(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.deleteReport(conn, boardNo);
		
		if(result > 0) {
			result = dao.deleteReport2(conn, boardNo);
			
			if(result >0) {
				conn.commit();				
			}else {
				conn.rollback();
			}
		}
		conn.close();
		
		return result;
		
	}

	/** 신고된 게시글 복구 Service
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int returnReport(int boardNo) throws Exception{
		Connection conn = getConnection();

		int result = dao.returnReport(conn, boardNo);
		
		if(result > 0) conn.commit();
		else 		   conn.rollback();
		
		conn.close();
		
		return result;
		
	}

	/** 조건에 따라 회원 조회 Service
	 * @param type
	 * @return list
	 * @throws Exception
	 */
	public List<Member> checkView(String type) throws Exception{
		Connection conn = getConnection();
		
		List<Member> list = dao.checkView(conn, type);
		
		conn.close();
		
		return list;
	}




	/** 전체 회원 조회 페이징바 Service
	 * @param currentPage
	 * @return PageInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfoM(String currentPage) throws Exception {
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);
		
		int listCount = dao.getListCountM(conn);

		conn.close();
		
		return new PageInfo(cp, listCount);
	}

	/** 전체 회원 조회 일부 게시글만 조회 Service
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<Member> selectListM(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		List<Member> list = dao.selectListM(conn, pInfo);
		
		conn.close();
		
		return list;
	}


	public PageInfo getPageInfoC(String currentPage, String type) throws Exception{
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);
		int listCount;
		if(type.equals("0")) {
			listCount = dao.getListCountM(conn);	
		}else {
			listCount = dao.getListCountC(conn, type);			
		}

		conn.close();
		
		return new PageInfo(cp, listCount);
	}


	public List<Member> selectListC(PageInfo pInfo, String type) throws Exception{
		Connection conn = getConnection();
		List<Member> list;
		if(type.equals("0")) {
			list = dao.selectListM(conn, pInfo);	
		}else {
			list = dao.selectListC(conn, pInfo, type);		
		}
		
		conn.close();
		
		return list;
	}

	public List<Attachment> selectFiles(int boardNo) throws Exception{
		Connection conn = getConnection();
		List<Attachment> fList = dao.selectFiles(conn, boardNo);
		
		conn.close();
		return fList;
	}


	public int checkRequest(int parentNo) throws Exception{
		Connection conn = getConnection();
		int result = dao.checkRequest(conn, parentNo);
		
		conn.close();
		return result;
	}


	public int deleteRequest(int boardNo) throws Exception {
		Connection conn = getConnection();
		int result = dao.deleteRequest(conn, boardNo);
		
		if(result>0) {
			conn.commit();
		}else {
			conn.rollback();
		}
		conn.close();
		return result;
	}


	public PageInfo getPageInfo(String currentPage, String boardType, String searchKey, String searchValue) throws Exception{
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);		
		
		String condition = createCondition(searchKey, searchValue);
		
		int listCount = dao.getSearchCount(conn, boardType, condition);
		
		conn.close();
		
		return new PageInfo(cp, listCount, boardType);
	}
	
	public PageInfo getPageInfoR(String currentPage, String boardType, String searchValue) throws Exception{
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);		
		
		String condition = "REQUEST_CONTEN LIKE '%' || '" + searchValue + "' || '%'";
		
		int listCount = dao.getSearchCount(conn, boardType, condition);
		
		conn.close();
		
		return new PageInfo(cp, listCount, boardType);
	}
	
	public List<AdminPage> selectSearch(PageInfo pInfo, String searchValue, String searchKey) throws Exception {
		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);
		
		List<AdminPage> list = dao.selctSearch(conn, pInfo, condition);
		
		conn.close();
		
		return list;
	}


	public List<RequestBoard> selectSearchR(PageInfo pInfo, String searchValue) throws Exception {
		Connection conn = getConnection();
		
		String condition = "REQUEST_CONTEN LIKE '%' || '" + searchValue + "' || '%'";
		
		List<RequestBoard> list = dao.selctSearchR(conn, pInfo, condition);
		
		conn.close();
		
		return list;
	}
	
	private String createCondition(String searchKey, String searchValue) {
		String condition = null;
		
		switch(searchKey) {
		case "title" : condition = "BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "content" : condition = "BOARD_CONTENT LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "all" : condition = "BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%'"+ "OR BOARD_CONTENT LIKE + '%' || '" + searchValue + "' || '%'"; break;
		}
		
		return condition;
	}


	public Hospital updateHos(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		Hospital hospital = dao.updateHos(conn, boardNo);
		
		conn.close();
		return hospital;
	}


	public int updateHosBoard(Hospital hospital) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.updateBoard(conn, hospital);

		if(result > 0) {
			result = dao.updateBoardHos(conn, hospital);
			
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


	public int insertHos(AdminPage board, Hospital hospital) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertBoard(conn, board);
		
		if(result >0) {
			result = dao.insertHos(conn, hospital);
			
			if(result>0) {
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

	public int insertKnow(AdminPage adminpage, int select) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertKnow(conn, adminpage);

		if(result > 0) {
			result = dao.knowCommon(conn, select);
			
			if(result >0) {
				
				conn.commit();
			}else {
				conn.rollback();
			}
		}
			
		conn.close();
		return result;
	}

	public int insertNotice(String title, String content, int type) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertNotice(conn, title, content, type);

		if(result > 0) {
			conn.commit();
		}else {
			conn.rollback();
		}
			
		conn.close();
		return result;
	}
	
	public int selectBoardNo() throws Exception{
		Connection conn = getConnection();
		
		int result = dao.selectBoardNo(conn);
		
		conn.close();
		return result;
	}




	public int deleteNotice(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.deleteNotice(conn, boardNo);
		
		if(result > 0) conn.commit();
		else 		   conn.rollback();
		
		conn.close();
		
		return result;
		
	}
	
	public PageInfo getPageInfoM(String currentPage, String searchKey, String searchValue) throws Exception{
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);		
		
		String condition = createConditionM(searchKey, searchValue);
		
		int listCount = dao.getSearchCountM(conn, condition);
		
		conn.close();
		
		return new PageInfo(cp, listCount);
	}
	
	private String createConditionM(String searchKey, String searchValue) {
		String condition = null;
		
		switch(searchKey) {
		case "id" : condition = "MEMBER_ID LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "name" : condition = "MEMBER_NM LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "address" : condition = "MEMBER_ADDR LIKE '%' || '" + searchValue + "' || '%'"; break;
		}
		
		return condition;
	}


	public List<Member> selectSearchM(PageInfo pInfo, String searchValue, String searchKey) throws Exception{
		Connection conn = getConnection();
		
		String condition = createConditionM(searchKey, searchValue);
		
		List<Member> list = dao.selctSearchM(conn, pInfo, condition);
		
		conn.close();
		
		return list;
	}
}
