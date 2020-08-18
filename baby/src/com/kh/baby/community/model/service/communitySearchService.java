package com.kh.baby.community.model.service;

import com.kh.baby.community.model.dao.CommunitySearchDAO;
import com.kh.baby.community.model.vo.Attachment;
import com.kh.baby.community.model.vo.Board;
import com.kh.baby.community.model.vo.PageInfo;

import static com.kh.baby.common.DBCP.getConnection;

import java.sql.Connection;
import java.util.List;

public class communitySearchService {

	CommunitySearchDAO dao = new CommunitySearchDAO();
	
	/**  Service
	 * @param currentPage
	 * @param boardType
	 * @param searchKey
	 * @param searchValue
	 * @return pInfo
	 * @throws ex
	 */
	public PageInfo getPageInfo(String currentPage, int boardType, String searchKey, String searchValue) throws Exception{
		
		Connection conn = getConnection();

		int cp;
		
		if(currentPage == "null") {
			cp = 1;
		}else {
			cp=Integer.parseInt(currentPage);
		}
		
		String condition = createCondition(searchKey, searchValue);
		
		int listCount = dao.getSearchCount(conn, boardType, condition);
		
		conn.close();
		
		return new PageInfo(cp, listCount, boardType);
	}

	/** Service
	 * @param pInfo
	 * @param searchValue
	 * @param searchKey
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectSearch(PageInfo pInfo, String searchValue, String searchKey) throws Exception {

		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);
		List<Board> freeList = dao.selectSearch(conn, pInfo, condition);
		conn.close();
		
		return freeList;
	}
	
	
	/** 검색 키워드 얻어오기 service
	 * @param searchKey
	 * @param searchValue
	 * @return condition
	 */
	private String createCondition(String searchKey, String searchValue) {
		String condition = null;
		
		switch(searchKey) {
		case "title" : condition = "BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "content" : condition = "BOARD_CONTENT LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "all" : condition = "BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%'"
									+ "OR BOARD_CONTENT LIKE + '%' || '" + searchValue + "' || '%'"; break;
		case "hashtag" : condition = "HASHTAG LIKE '%' || '" + searchValue + "' || '%'"; break;

		case "선택": break;
		}
		
		
		return condition;
	}
	

	// --------------------------------------------------------------
	
	/** 홍보게시판 페이지정보 얻어오기 Service
	 * @param currentPage
	 * @param boardType
	 * @param searchKey
	 * @param searchValue
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo promoGetPageInfo(String currentPage, int boardType, String searchKey, String searchValue) throws Exception{
		
		Connection conn = getConnection();
		
		int cp;
		
		if(currentPage == "null") {
			cp = 1;
		}else {
			cp=Integer.parseInt(currentPage);
		}
		
		String condition = createCondition(searchKey, searchValue);
		
		int listCount = dao.promoSearchCount(conn, boardType, condition);
		
		conn.close();
		
		return new PageInfo(cp, listCount, boardType);
	}
	
	/** 홍보게시판 게시글 검색 Service
	 * @param pInfo
	 * @param searchValue
	 * @param searchKey
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> promoSelectSearch(PageInfo pInfo, String searchValue, String searchKey) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);

		List<Board> promoList = dao.promoSelectSearch(conn, pInfo, condition);
		conn.close();
		
		return promoList;
	}
	
	
	/** 썸네일 목록 조회 Service
	 * @param pInfo
	 * @return fileList
	 * @throws Exception
	 */
	public List<Attachment> promoFileList(PageInfo pInfo)throws Exception {
		Connection conn = getConnection();
		List<Attachment> fileList = dao.promoFileList(conn, pInfo);
		conn.close();
		return fileList;
	}
	
	
	
	

}
