package com.kh.baby.board.model.service;

import java.sql.Connection;
import java.util.List;

import static com.kh.baby.common.DBCP.getConnection;

import com.kh.baby.board.model.dao.ReplyDAO;
import com.kh.baby.board.model.vo.Reply;

public class ReplyService {
	
	private ReplyDAO dao;

	public ReplyService() throws Exception{
		dao = new ReplyDAO();
	}
	
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

	/** 댓글 목록 조회 Service
	 * @param boardNo
	 * @return rList
	 * @throws Exception
	 */
	public List<Reply> selectReply(int boardNo) throws Exception{
		
		Connection conn = getConnection();
		
		List<Reply> rList = dao.selectList(conn, boardNo);
		
		conn.close();
		
		return rList;
	}

	/** 댓글 삽입 Service
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertReply(Reply reply) throws Exception{
		
		Connection conn = getConnection();
		
		reply.setReplyContent(
				replaceParameter(reply.getReplyContent()));
		
		reply.setReplyContent(
				reply.getReplyContent().replaceAll("\n", "<br>"));
		
		int result = dao.insertReply(conn, reply);
				
		if(result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}

	/** 댓글 삭제 Service
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteReply(int replyNo) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.deleteReply(conn, replyNo);
		
		if(result > 0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}

	/** 댓글 수정 Service
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateReply(Reply reply) throws Exception{

		Connection conn = getConnection();
		
		reply.setReplyContent(
				replaceParameter(reply.getReplyContent()));
		
		reply.setReplyContent(
				reply.getReplyContent().replaceAll("\n", "<br>"));
		
		int result = dao.updateReply(conn, reply);
				
		if(result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		
		return result;
	}

}
