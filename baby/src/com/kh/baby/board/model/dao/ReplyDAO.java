package com.kh.baby.board.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.baby.board.model.vo.Reply;

public class ReplyDAO {
	
	private Properties prop;
	
	public ReplyDAO() throws Exception{
		String fileName 
		= ReplyDAO.class.getResource
		("/com/kh/baby/sql/board/reply-query.properties").getPath();  

		prop = new Properties();
		
		prop.load(new FileReader(fileName));
		
	}

	/** 댓글 목록 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return rList
	 * @throws Exception
	 */
	public List<Reply> selectList(Connection conn, int boardNo) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Reply> rList = null;
		
		String query = prop.getProperty("selectReply");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			rList = new ArrayList<Reply>();
			Reply reply = null;
			
			while(rset.next()) {
				reply = new Reply(rset.getInt("REPLY_NO"), 
						rset.getString("REPLY_CONTENT"), 
						rset.getTimestamp("REPLY_WRITE_DT"),
						rset.getTimestamp("REPLY_MODIFY_DT"),
						rset.getString("MEMBER_NICK"),
						rset.getString("MEMBER_ID"));
				rList.add(reply);
			}
			
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return rList;
	}

	/** 댓글 삽입 DAO
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertReply(Connection conn, Reply reply) throws Exception{

		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertReply");
		
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

	/** 댓글 삭제 DAO
	 * @param conn
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteReply(Connection conn, int replyNo) throws Exception{

		PreparedStatement pstmt = null;
		int result =0;
		
		String query = prop.getProperty("deleteReply");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, replyNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}

	/** 댓글 수정 DAO
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateReply(Connection conn, Reply reply) throws Exception{

		PreparedStatement pstmt = null;
		int result =0;
		
		String query = prop.getProperty("updateReply");
		
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

}
