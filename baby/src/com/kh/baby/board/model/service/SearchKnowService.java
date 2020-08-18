package com.kh.baby.board.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.baby.board.model.dao.SearchKnowDAO;
import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.Page;

public class SearchKnowService {
	
	SearchKnowDAO dao = new SearchKnowDAO();

	/** 검색 내용이 포함된 페이지 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @param searchKey
	 * @param searchValue
	 * @return pInfo
	 * @throws Exception
	 */
	public Page getPageInfo(String currentPage, int boardType, String searchKey, String searchValue) throws Exception{
		
		Connection conn = getConnection();
		
		int cp = currentPage == null ? 1 : Integer.parseInt(currentPage);
		
		String condition = createCondition(searchKey, searchValue);
		
		int listCount = dao.getSearchCount(conn, boardType, condition);
		
		conn.close();
		
		return new Page(cp, listCount, boardType);
	}

	/** 검색 조건에 따라 sql에 사용된 조건절 내용을 만드는 메소드
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
		}
		
		return condition;
	}

	/** 검색 내용이 포함된 게시글 목록 조회 Service
	 * @param pInfo
	 * @param searchValue
	 * @param searchKey
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectSearch(Page pInfo, String searchValue, String searchKey) throws Exception{

		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);
		
		List<Board> bList = dao.selectSearch(conn, pInfo, condition);
		
		conn.close();
		
		return bList;
	}

}
