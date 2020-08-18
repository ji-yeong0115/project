package com.kh.baby.communityReply.model.service;

import static com.kh.baby.common.DBCP.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.baby.communityReply.model.dao.CommunityReplyDAO;
import com.kh.baby.communityReply.model.vo.CommunityReply;

public class CommunityReplyService {
	
	private CommunityReplyDAO dao;
	
	public CommunityReplyService() throws Exception {
		dao= new CommunityReplyDAO();
		
		
	}

	/** 자유게시판 댓글 목록 조회 Service
	 * @param boardNo
	 * @return rList
	 * @throws Exception
	 */
		public List<CommunityReply> freeSelectList(int boardNo) throws Exception {
		Connection conn = getConnection();
		List<CommunityReply> rList = dao.freeSelectList(conn,boardNo);
		conn.close();
		
		return rList;
	}
		
		/** 자유게시판 댓글 등록 Service
		 * @param reply
		 * @return result
		 * @throws Exception
		 */
	public int freeInsertReply(CommunityReply reply) throws Exception {
		reply.setReplyContent(
				replaceParameter(reply.getReplyContent()));
		
		//개행 문자 처리 \r\n => <br>
		
		reply.setReplyContent(
				replaceParameter(reply.getReplyContent()));
		
		reply.setReplyContent(
				reply.getReplyContent().replaceAll("\n", "<br>"));
		
		Connection conn = getConnection();
		int result= dao.freeInsertReply(conn,reply);
		
		if (result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		return result;
	}
	
	
	
	/** 자유게시판 댓글 수정용
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int freeUpdateReply(CommunityReply reply) throws Exception {
		Connection conn = getConnection();

		reply.setReplyContent(
				replaceParameter(reply.getReplyContent()));
		
		//개행 문자 처리 \r\n => <br>
		
		reply.setReplyContent(reply.getReplyContent().replaceAll("\n", "<br>"));
		
		int result= dao.freeUpdateReply(conn,reply);
		
		if (result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		return result;
	}
	
	
	/** 자유게시판 댓글 삭제용 service
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int freeDeleteReply(int replyNo) throws Exception {
		Connection conn = getConnection();
		int result = dao.freeDeleteReply(replyNo, conn);
		
		if(result>0) conn.commit();
		else conn.rollback();
		
		conn.close();
		return result;
	}
	
	

//	크로스  사이트 스크립트 방지 메소드 추가
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





}
