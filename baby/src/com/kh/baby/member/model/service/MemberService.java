package com.kh.baby.member.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.sql.Connection;

import com.kh.baby.member.model.dao.MemberDAO;
import com.kh.baby.member.model.vo.General;
import com.kh.baby.member.model.vo.Member;
import com.kh.baby.member.model.vo.Seller;

public class MemberService {
	private MemberDAO dao;
	
	public MemberService() throws Exception {
		dao = new MemberDAO();
	}

	public Member loginMember(Member member) throws Exception{
		Connection conn = getConnection();
		
		Member loginMember = dao.loginMember(conn, member);
		
		conn.close();
		
		return loginMember;
	}

	public int signUpGeneral(Member member, General general) throws Exception {
		Connection conn = getConnection();
		
		int memberNo = dao.selectMemberNo(conn);
		
		member.setMemberNo(memberNo);
		
		int result = dao.signUp(conn, member);
		
		
		if(result > 0) {
			result = 0;
			
			result = dao.signUpGeneral(conn, memberNo, general);
			if(result > 0) conn.commit();
			else 		   conn.rollback();
		} else {
			conn.rollback();
		}
		
		conn.close();
		
		return result;
	}

	public int idDupCheck(String id) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.idDupCheck(conn, id);
		
		conn.close();
		
		return result;
	}

	public int emailDupCheck(String email) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.emailDupCheck(conn, email);
		
		conn.close();
		
		return result;
	}

	public int nicknameDupCheck(String nickname) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.nicknameDupCheck(conn, nickname);
		
		conn.close();
		
		return result;
	}
	public int companyNumberDupCheck(String companyNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.companyNoDupCheck(conn, companyNo );
		
		conn.close();
		
		return result;
	}


	public int signUpSeller(Member member, Seller seller) throws Exception{
		Connection conn = getConnection();
		
		int memberNo = dao.selectMemberNo(conn);
		
		member.setMemberNo(memberNo);
		
		int result = dao.signUp(conn, member);
		
		
		if(result > 0) {
			result = 0;
			
			result = dao.signUpSeller(conn, memberNo, seller);
			if(result > 0) conn.commit();
			else 		   conn.rollback();
		} else {
			conn.rollback();
		}
		
		conn.close();
		
		return result;
  }
  
	public General loginGeneral(int memberNo) throws Exception {
		Connection conn = getConnection();
		
		General loginGeneral = dao.loginGeneral(conn, memberNo);
		
		conn.close();
		
		return loginGeneral;
	}

	public Seller loginSeller(int memberNo) throws Exception {
		Connection conn = getConnection();

		Seller loginSeller = dao.loginSeller(conn, memberNo);
		
		conn.close();
		
		return loginSeller;
	}
	
	public int signOut(Member member, String currentPwd) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.checkPwd(conn, member);
		
		if(result > 0) {
			result = dao.signOut(conn, member);
			
			if(result>0)	conn.commit();
			else			conn.rollback();
		}else {
			result = -1;
		}
		
		conn.close();
		return result;
	}

	public int updateMember(Member member) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateMember(conn, member);
		
		if(result>0)	conn.commit();
		else			conn.rollback();
		
		conn.close();
		return result;
	}

	public int updateSeller(Member member, Seller seller) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateMember(conn, member);
		
		
		if(result > 0) {
			result = 0;
	
			result = dao.updateSeller(conn, member.getMemberNo(), seller);
			if(result > 0) conn.commit();
			else 		   conn.rollback();
		} else {
			conn.rollback();
		}
		
		conn.close();
		
		return result;
	}
	



	/** 이메일 여부 확인 Service
	 * @param email
	 * @return result
	 * @throws Exception
	 */
	public int selectEmail(String email) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.selectEmail(conn, email);
		
		conn.close();
		
		return result;
	}

	/** 새로운 비밀번호 등록 Service
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updatePwd(Member member) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updatePwd(conn, member);
		
		if(result > 0) conn.commit();
		else		   conn.rollback();
		
		conn.close();
		return result;
	}

	/** 아이디 찾기 Service
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public String searchId(Member member) throws Exception {
		Connection conn = getConnection();
		
		String result = dao.searchId(conn, member);
		
		conn.close();
		
		return result;
	}


}










