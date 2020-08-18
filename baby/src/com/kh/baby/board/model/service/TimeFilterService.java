package com.kh.baby.board.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.baby.board.model.dao.TimeFilterDAO;
import com.kh.baby.board.model.vo.Board;
import com.kh.baby.board.model.vo.Page;

public class TimeFilterService {
	
	TimeFilterDAO dao = new TimeFilterDAO();

	/** 필터 내용이 포함된 페이지 정보 생성 Service
	 * @param currentPage
	 * @param boardType
	 * @param yn
	 * @return
	 * @throws Exception
	 */
	public Page getPageInfo(String currentPage, int boardType, int yn) throws Exception{
		
		Connection conn = getConnection();
		
		int cp;
		
		if(currentPage == "null") {
			cp = 1;
		}else {
			cp=Integer.parseInt(currentPage);
		}
		String condition = createCondition(yn);

		int listCount = dao.getFilterCount(conn, boardType, condition);
		
		conn.close();
		
		return new Page(cp, listCount, boardType);
	}



	/** 필터 조건이 포함된 게시글 목록 조회 Service
	 * @param pInfo
	 * @param yn
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectFilter(Page pInfo, int yn) throws Exception{
		
		Connection conn = getConnection();
		
		String condition = createCondition(yn);
		
		List<Board> bList = dao.selectFilter(conn, pInfo, condition);
		
		conn.close();
		
		return bList;
	}
	
	/** 필터 조건에 따라 sql에 사용될 조건절 내용을 만드는 메소드
	 * @param yn
	 * @return condition
	 */
	private String createCondition(int yn) {
		String condition = null;
		switch(yn) {
		case 2: condition = "AND HOS_NIGHT_YN = 'Y'"
						+ " AND HOS_WEEKEND_YN = 'N'"
						+ " AND BOARD_NOTICE = 'N'"; break;
		
		case 3: condition = "AND HOS_NIGHT_YN = 'N'"
							+ " AND HOS_WEEKEND_YN = 'Y'"
							+ " AND BOARD_NOTICE = 'N'"; break;
		
		case 4: condition = "AND HOS_NIGHT_YN = 'Y'"
				+ " AND HOS_WEEKEND_YN = 'Y'"
				+ " AND BOARD_NOTICE = 'N'"; break;
		}
		
		return condition;
	}

}
