package com.kh.baby.member.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.kh.baby.member.model.vo.General;
import com.kh.baby.member.model.vo.Member;
import com.kh.baby.member.model.vo.Seller;

public class MemberDAO {
	private Properties prop;
	
	public MemberDAO() throws Exception {
		String fileName 
		= MemberDAO.class.getResource("/com/kh/baby/sql/member/member-query.properties").getPath();
		
		prop = new Properties();
		
		prop.load(new FileReader(fileName));
	}
	
	public Member loginMember(Connection conn, Member member) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member loginMember = null;
		
		String query = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginMember = new Member(
								rset.getInt("MEMBER_NO"),
								rset.getString("MEMBER_ID"),
								rset.getString("MEMBER_NM"),
								rset.getString("MEMBER_NICK"),
								rset.getString("MEMBER_EMAIL"),
								rset.getString("MEMBER_ADDR"),
								rset.getString("MEMBER_GRADE")
							);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		return loginMember;
	}

	public int signUp(Connection conn, Member member) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("signUp");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, member.getMemberNo());
			pstmt.setString(2, member.getMemberId());
			pstmt.setString(3, member.getMemberPwd());
			pstmt.setString(4, member.getMemberName());
			pstmt.setString(5, member.getMemberNickname());
			pstmt.setString(6, member.getMemberEmail());
			pstmt.setString(7, member.getMemberAddress());
			pstmt.setString(8, member.getMemberGrade());
			

			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	

	public int selectMemberNo(Connection conn) throws Exception {
		Statement stmt = null;
		ResultSet rset = null;
		int memberNo = 0;
		
		String query = prop.getProperty("selectMemberNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				memberNo = rset.getInt(1);
			}
		}finally {
			rset.close();
			stmt.close();
		}
		return memberNo;
	}

	public int selectCurrentMemberNo(Connection conn, Member member) throws Exception {
		Statement stmt = null;
		ResultSet rset = null;
		int memberNo = 0;
		
		String query = prop.getProperty("selectCurrentMemberNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				memberNo = rset.getInt(1);
			}
		}finally {
			rset.close();
			stmt.close();
		}
		return memberNo;
	}
	public int signUpGeneral(Connection conn, int memberNo, General general)throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("signUpGeneral");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, general.getKidsName());
			pstmt.setString(3, general.getKidsGender());
			pstmt.setString(4, general.getKidsBirth());
			pstmt.setDouble(5, general.getKidsHeight());
			pstmt.setDouble(6, general.getKidsWeight());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}
	
	public int idDupCheck(Connection conn, String id) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("idCheck");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
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
	
	public int emailDupCheck(Connection conn, String email) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("emailCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			
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
	
	public int nicknameDupCheck(Connection conn, String nickname)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("nicknameCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nickname);
			
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
	
	public int companyNoDupCheck(Connection conn, String companyNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("companyNoCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, companyNo);
			
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


	public int signUpSeller(Connection conn, int memberNo, Seller seller) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("signUpSeller");
  
    try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, seller.getCompanyNo());
			pstmt.setString(3, seller.getCompanyPhone());
		
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		return result;
	}
      
      
	public General loginGeneral(Connection conn, int memberNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		General loginGeneral = null;
		
		String query = prop.getProperty("loginGeneral");
  
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);

			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginGeneral = new General(
								rset.getString("BABY_NM"),
								rset.getString("BABY_GENDER"),
								rset.getString("BABY_BIRTH"),
								rset.getDouble("BABY_HEIGHT"),
								rset.getDouble("BABY_WEIGHT")
							);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return loginGeneral;
	}
	
	public Seller loginSeller(Connection conn, int memberNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Seller loginSeller = null;
		
		String query = prop.getProperty("loginSeller");
  
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);

			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginSeller = new Seller(
								rset.getString("COMPANY_NO"),
								rset.getString("COMPANY_PHONE")
							);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return loginSeller;	
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
	
	public int signOut(Connection conn, Member member) throws Exception{
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("signOut");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int updateMember(Connection conn, Member member) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, member.getMemberNickname());
			pstmt.setString(2, member.getMemberAddress());
			pstmt.setInt(3, member.getMemberNo());
			
			result = pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	public int updateSeller(Connection conn, int memberNo, Seller seller) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateSeller");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, seller.getCompanyPhone());
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		return result;
	}


	public int updateGeneral(Connection conn, int memberNo, General general) throws Exception{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateGeneral");
		
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

	/** 이메일 여부 확인 DAO
	 * @param conn
	 * @param email
	 * @return result
	 * @throws Exception
	 */
	public int selectEmail(Connection conn, String email) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("selectEmail");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		return result;
	}

	/** 새로운 비밀번호 등록 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updatePwd(Connection conn, Member member) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updatePwd");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberPwd());
			pstmt.setString(2, member.getMemberEmail());
			
			result = pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 아이디 찾기 DAO
	 * @param conn
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public String searchId(Connection conn, Member member) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String result = "";
		
		String query = prop.getProperty("searchId");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getMemberName());
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getString(1);
			}
		} finally {
			rset.close();
			pstmt.close();
		}
		
		return result;
	}





}















