package com.kh.baby.board.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.baby.board.model.dao.SearchHosDAO;
import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.Page;

public class SearchHosService {
	
	SearchHosDAO dao = new SearchHosDAO();
	
	/** 검색 내용이 포함된 페이지 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @param searchKey
	 * @param searchValue
	 * @return pInfo
	 * @throws ex
	 */
	public Page getPageInfo(String currentPage, int boardType, String searchKey, String searchValue) throws Exception{
		
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
		
		return new Page(cp, listCount, boardType);
	}

	/** 검색 내용이 포함된 게시글 목록 조회 Service
	 * @param pInfo
	 * @param searchValue
	 * @param searchKey
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectSearch(Page pInfo, String searchValue, String searchKey) throws Exception {

		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);
		
		List<Board> bList = dao.selctSearch(conn, pInfo, condition);
		
		conn.close();
		
		return bList;
	}
	
	
	/** 검색 조건에 따라 sql에 사용된 조건전 내용을 만드는 메소드
	 * @param searchKey
	 * @param searchValue
	 * @return condition
	 */
	private String createCondition(String searchKey, String searchValue) {
		String condition = null;
		switch(searchKey) {
		case "title" : condition = "HOS_ADDR LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "content" : condition = "BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%'"; break;
		case "all" : condition = "BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%'"
									+ "OR HOS_ADDR LIKE + '%' || '" + searchValue + "' || '%'"; break;
		}
		
		return condition;
	}

}