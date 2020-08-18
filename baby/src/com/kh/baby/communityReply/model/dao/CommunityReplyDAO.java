package com.kh.baby.communityReply.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.baby.communityReply.model.vo.CommunityReply;

public class CommunityReplyDAO {
	
	private Properties prop;
	
	public CommunityReplyDAO() throws Exception {
		String fileName 
        = CommunityReplyDAO.class.getResource("/com/kh/baby/sql/board/replyCommunity-query.properties").getPath();  

		prop = new Properties();
		prop.load(new FileReader(fileName));
	}

	
	/** 자유게시판 댓글 목록 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return rList
	 * @throws Exception
	 */
	public List<CommunityReply> freeSelectList(Connection conn, int boardNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommunityReply> rList = null; 
		String query = prop.getProperty("freeSelectList");
		try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.setInt(1, boardNo);
	         
	         rs = pstmt.executeQuery();
	         
	         rList = new ArrayList<>();
	         CommunityReply reply = null;
	         
	         while(rs.next()) {
	            reply = new CommunityReply(
	                  rs.getInt("REPLY_NO"), 
	                  rs.getString("REPLY_CONTENT"), 
	                  rs.getString("MEMBER_ID"), 
	                  rs.getTimestamp("REPLY_WRITE_DT"), 
	                  rs.getTimestamp("REPLY_MODIFY_DT")
	                  );
	            
	            rList.add(reply);
	         }
	      }finally {
	         rs.close();
	         pstmt.close();
	      }
	      return rList;
	}


	/** 자유게시판 댓글 등록 
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int freeInsertReply(Connection conn, CommunityReply reply) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("freeInsertReply");
		try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.setString(1, reply.getReplyContent());
	         pstmt.setString(2, reply.getMemberId());
	         pstmt.setInt(3, reply.getBoardNo());
	         
	         result = pstmt.executeUpdate();
	         
	      }finally {
	         pstmt.close();
	      }
		
		return result;
	}
	
	
	/** 자유게시판 댓글 수정용
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int freeUpdateReply(Connection conn, CommunityReply reply) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("freeUpdateReply");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setInt(2, reply.getReplyNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}
	
	


	/** 자유게시판 댓글 삭제용 dao
	 * @param replyNo 
	 * @param conn
	 * @return result
	 * @throws Exception
	 */
	public int freeDeleteReply(int replyNo, Connection conn) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("freeDeleteReply");
		
		try {   
			
		pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, replyNo);
        result = pstmt.executeUpdate();
			
		} finally {
			pstmt.close();
		}
		
		return result;
	}

	
	
}
