package com.kh.baby.mypage.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.baby.member.model.vo.General;
import com.kh.baby.member.model.vo.Member;
import com.kh.baby.mypage.model.vo.Board;
import com.kh.baby.mypage.model.vo.Diary;
import com.kh.baby.mypage.model.vo.DiaryAttachment;
import com.kh.baby.mypage.model.vo.Grow;
import com.kh.baby.mypage.model.vo.Page;
import com.kh.baby.mypage.model.vo.PageInfo;

public class MyPageDAO {
	private Properties prop;

	public MyPageDAO() throws Exception {
		String fileName 
		= MyPageDAO.class.getResource("/com/kh/baby/sql/mypage/mypage-query.properties").getPath();
		
		prop = new Properties();
		
		prop.load(new FileReader(fileName));
	}

	/** 육아일기 번호 지정 DAO
	 * @param conn
	 * @return diaryNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception {
		Statement stmt = null;
		ResultSet rset = null;
		int diaryNo = 0;
		
		String query = prop.getProperty("selectNextNo");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				diaryNo = rset.getInt(1);
			}
		} finally {
			rset.close();
			stmt.close();
		}
		
		return diaryNo;
	}

	/** 육아일기 삽입 DAO
	 * @param conn
	 * @param diary
	 * @return result
	 * @throws Exception
	 */
	public int insertDiary(Connection conn, Diary diary) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertDiary");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, diary.getDiaryNo());
			pstmt.setString(2, diary.getDiaryContent());
			pstmt.setInt(3, diary.getMemberNo());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		return result;
	}

	/** 육아일기 사진 등록 DAO
	 * @param conn
	 * @param diaryNo
	 * @return
	 * @throws Exception
	 */
	public int insertDiaryAttachment(Connection conn, DiaryAttachment dFile) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertDiaryAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dFile.getFileOriginName());
			pstmt.setString(2, dFile.getFileChangeName());
			pstmt.setString(3, dFile.getFilePath());
			pstmt.setInt(4, dFile.getFileLevel());
			pstmt.setInt(5, dFile.getDiaryNo());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 전체 게시글 수 조회 DAO
	 * @param conn
	 * @param memberNo
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, int memberNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) listCount = rset.getInt(1);
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return listCount;
	}
	
	/** 육아일기 조회 DAO
	 * @param conn
	 * @param memberNo
	 * @return dList
	 * @throws Exception
	 */
	public List<Diary> selectDiary(Connection conn, PageInfo pInfo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Diary> dList = null;
		
		String query = prop.getProperty("selectDiary");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getMemberNo());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			dList = new ArrayList<Diary>();
			Diary diary = null;
			while(rset.next()) {
				diary = new Diary(rset.getInt("DIARY_NO"), 
								rset.getString("DIARY_CONTENT"), 
								rset.getDate("DIARY_WRITE_DT"), 
								rset.getDate("DIARY_MODIFY_DT"));
				dList.add(diary);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return dList;
	}

	/** 육아일기 이미지 조회
	 * @param conn
	 * @param diaryNo
	 * @return fList
	 * @throws Exception
	 */
	public DiaryAttachment selectFile(Connection conn, int diaryNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DiaryAttachment file = null;
		
		String query = prop.getProperty("selectFile");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, diaryNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				file = new DiaryAttachment(rset.getInt("DFILE_NO"), 
									rset.getString("DFILE_CHANGE_NAME"), 
									rset.getString("DFILE_PATH"), 
									rset.getInt("DFILE_LEVEL"),
									diaryNo);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return file;
	}

	/** 육아일기 삭제 DAO
	 * @param conn
	 * @param diaryNo
	 * @return
	 * @throws Exception
	 */
	public int deleteBoard(Connection conn, int diaryNo) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteDiary");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, diaryNo);
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 육아일기 수정 DAO
	 * @param conn
	 * @param diary
	 * @return
	 * @throws Exception
	 */
	public int updateDiary(Connection conn, Diary diary) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateDiary");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, diary.getDiaryContent());
			pstmt.setInt(2, diary.getDiaryNo());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 육아일기 이미지 수정 DAO
	 * @param conn
	 * @param dFile
	 * @return result
	 * @throws Exception
	 */
	public int updateFile(Connection conn, DiaryAttachment dFile) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateFile");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dFile.getFileOriginName());
			pstmt.setString(2, dFile.getFileChangeName());
			pstmt.setString(3, dFile.getFilePath());
			pstmt.setInt(4, dFile.getFileNo());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	public int checkPwd(Connection conn, Member member) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		ResultSet rset = null;
		
		String query = prop.getProperty("checkPwd");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return result;
	}
	public int replacePwd(Connection conn, Member member) throws Exception{

		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("replacePwd");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, member.getMemberPwd());
			pstmt.setString(2, member.getMemberId());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}

	/** 내가 작성한 글 수 조회 DAO
	 * @param conn
	 * @param memberNo
	 * @return listCount
	 * @throws Exception
	 */
	public int getMyListCount(Connection conn, int memberNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int listCount = 0;
		
		String query = prop.getProperty("getMyListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) listCount = rset.getInt(1);
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return listCount;
	}

	public List<Board> selectList(Connection conn, Page pInfo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> bList = null;
		
		String query = prop.getProperty("selectList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pInfo.getMemberNo());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			Board board = null;
			bList = new ArrayList<Board>();
			
			while(rset.next()) {
				board = new Board(rset.getInt("BOARD_NO"), 
								rset.getString("BOARD_TITLE"), 
								rset.getTimestamp("BOARD_WRITE_DT"), 
								rset.getInt("BOARD_REPLY_CNT"), 
								rset.getInt("BOARD_TYPE"));
				board.setCategoryName(rset.getString("CATEGORY_NAME"));
				
				if(board.getBoardType() == 3) board.setStringboardType("자유게시판");
				else board.setStringboardType("홍보게시판");
				
				bList.add(board);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return bList;
	}

	public int updateBabyInfo(Connection conn, int memberNo, General general) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateBabyInfo");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, general.getKidsName());
			pstmt.setDouble(2, general.getKidsHeight());
			pstmt.setDouble(3, general.getKidsWeight());
			pstmt.setInt(4, memberNo);
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();			
		}
		
		return result;
	}
	public int insertBabyInfo(Connection conn, int memberNo, General general) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("insertBabyInfo");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, general.getKidsName());
			pstmt.setString(2, general.getKidsGender());
			pstmt.setString(3, general.getKidsBirth());
			pstmt.setDouble(4, general.getKidsHeight());
			pstmt.setDouble(5, general.getKidsWeight());
			pstmt.setInt(6, memberNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		
		return result;
	}


	public int updateAddress(Connection conn, Member member) throws Exception {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateAddress");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberAddress());
			pstmt.setInt(2, member.getMemberNo());
			
			result = pstmt.executeUpdate();
			
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public Grow calGrow(Connection conn, int month, String gender) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Grow grow = new Grow();
		System.out.println(month + "/" + gender);
		String query = prop.getProperty("calGrow");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, month);
			pstmt.setInt(2, month);
			pstmt.setString(3, gender);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				grow.setCm(rset.getDouble("CM"));
				grow.setKg(rset.getDouble("KG"));
				grow.setGender(gender);
				grow.setMonth(month);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		return grow;
	}

}
