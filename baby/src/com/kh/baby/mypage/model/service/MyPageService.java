package com.kh.baby.mypage.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import com.kh.baby.member.model.vo.General;
import com.kh.baby.member.model.vo.Member;
import com.kh.baby.mypage.model.dao.MyPageDAO;
import com.kh.baby.mypage.model.vo.Board;
import com.kh.baby.mypage.model.vo.Diary;
import com.kh.baby.mypage.model.vo.DiaryAttachment;
import com.kh.baby.mypage.model.vo.Grow;
import com.kh.baby.mypage.model.vo.Page;
import com.kh.baby.mypage.model.vo.PageInfo;

public class MyPageService {
	private MyPageDAO dao;
	
	public MyPageService() throws Exception {
		dao = new MyPageDAO();
	}
	
	// 크로스 사이트 스트립팅 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		
		return result;
	}

	/** 육아일기 삽입 Service
	 * @param diary
	 * @param dFile
	 * @return result
	 * @throws Exception
	 */
	public int insertDiary(Diary diary, DiaryAttachment dFile) throws Exception {
		Connection conn = getConnection();
		
		int result = 0;
		
		int diaryNo = dao.selectNextNo(conn);
		
		if(diaryNo > 0) {
			diary.setDiaryNo(diaryNo);
			
			diary.setDiaryContent(replaceParameter(diary.getDiaryContent()));
			diary.setDiaryContent(diary.getDiaryContent().replace("\r\n", "<br>"));
			
			result = dao.insertDiary(conn, diary);
			
			if(result > 0 && dFile != null) {
				result = 0;
				dFile.setDiaryNo(diaryNo);
				result = dao.insertDiaryAttachment(conn, dFile);
			}
		}
		
		if(result > 0) {
			result = diaryNo;
			conn.commit();
		} else {
			File deleteFile = new File(dFile.getFilePath() + dFile.getFileChangeName());
			deleteFile.delete();
			conn.rollback();
		}
		
		conn.close();
		
		return result;
	}

	/** 페이징 처리 정보 생성
	 * @param currentPage
	 * @param memberNo
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String currentPage, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int cp = (currentPage == null) ? 1 : Integer.parseInt(currentPage);
		
		int listCount = dao.getListCount(conn, memberNo);
		
		conn.close();
		
		return new PageInfo(cp, listCount, memberNo);
	}
	
	/** 육아일기 조회 Service
	 * @param memberNo
	 * @return dList
	 * @throws Exception
	 */
	public List<Diary> selectDiary(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		List<Diary> dList = dao.selectDiary(conn, pInfo);
		
		conn.close();
		
		return dList;
	}

	/** 육아일기 이미지 조회 Service
	 * @param diaryNo
	 * @return file
	 * @throws Exception
	 */
	public DiaryAttachment selectFile(int diaryNo) throws Exception {
		Connection conn = getConnection();
		
		DiaryAttachment file = dao.selectFile(conn, diaryNo);
		
		conn.close();
		
		return file;
	}

	/** 육아일기 삭제 Service
	 * @param diaryNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteDiary(int diaryNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteBoard(conn, diaryNo);
		
		if(result > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}
		
		conn.close();
		
		return result;
	}

	/** 육아일기 수정 Service
	 * @param diary
	 * @param dFile
	 * @return result
	 * @throws Exception
	 */
	public int updateDiary(Diary diary, DiaryAttachment dFile) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		
		diary.setDiaryContent(replaceParameter(diary.getDiaryContent())); // 크로스 사이트 스크립팅 방지
		diary.setDiaryContent(diary.getDiaryContent().replace("\r\n", "<br>")); // 개행문자 처리
		
		result = dao.updateDiary(conn, diary);
		
		
		DiaryAttachment oldFile = dao.selectFile(conn, diary.getDiaryNo());
		if(result > 0 && dFile != null) {
			result = 0;
			
			dFile.setFileNo(oldFile.getFileNo());
			dFile.setDiaryNo(diary.getDiaryNo());
			
			result = dao.updateFile(conn, dFile);
		}
		
		if(result > 0) {
			conn.commit();
			File deleteFile = new File(oldFile.getFilePath() + oldFile.getFileChangeName());
			deleteFile.delete();
		} else {
			conn.rollback();
			File deleteFile = new File(dFile.getFilePath() + dFile.getFileChangeName());
		}
		
		conn.close();
		
		return result;
	}
	public int replacePwd(Member member, String newPwd) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.checkPwd(conn, member);
		System.out.println(result +"!!");
		if(result>0){
			result = 0;
			member.setMemberPwd(newPwd);
			
			result = dao.replacePwd(conn, member);
			System.out.println(result+"!");
		}else {
			result = -1;
		}
		
		if(result>0)	conn.commit();
		else			conn.rollback();
		
		conn.close();
		return result;
	}

	/** 내가 작성한 글 페이지 처리 정보 생성
	 * @param currentPage
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Page getMyPageInfo(String currentPage, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int cp = (currentPage == null) ? 1 : Integer.parseInt(currentPage);
		
		int listCount = dao.getMyListCount(conn, memberNo);
		
		conn.close();
		
		return new Page(cp, listCount, memberNo);
	}

	public List<Board> selectList(Page pInfo) throws Exception {
		Connection conn = getConnection();
		
		List<Board> bList = dao.selectList(conn, pInfo);
		
		conn.close();
		
		return bList;
	}

	public int updateBabyInfo(Member member, General general) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateBabyInfo(conn, member.getMemberNo(), general);
		
		if(result>0)	conn.commit();
		else			conn.rollback();
		
		conn.close();
		
		return result;
	}
	public int insertBabyInfo(Member member, General general) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertBabyInfo(conn, member.getMemberNo(), general);
		
		if(result>0)	conn.commit();
		else			conn.rollback();
		
		conn.close();
		
		return result;
	}
	public int updateAddress(Member member) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateAddress(conn, member);
		
		if(result>0)	conn.commit();
		else			conn.rollback();
		
		conn.close();
		
		return result;
	}

	public Grow calGrow(int month, String gender) throws Exception {
		Connection conn = getConnection();
		
		Grow grow = dao.calGrow(conn, month, gender);
		
		conn.close();
		
		return grow;
	}
}
