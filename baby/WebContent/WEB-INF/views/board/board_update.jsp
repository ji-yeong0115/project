<%@page import="com.kh.baby.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	Board board = (Board)request.getAttribute("board");

	String cp = request.getParameter("cp");
	String type = request.getParameter("type");
	
	int boardNo = board.getBoardNo();
	
	String noticeYN = board.getBoardNotice();
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>지역별 병원 정보 공지사항 수정화면</title>
  
  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<style>

	@font-face { font-family: 'InfinitySans-BoldA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-BoldA1.woff') 
	format('woff'); font-weight: normal; font-style: normal; }
	
	
	#title{
		font-family: 'InfinitySans-BoldA1';
	}

	
	#content-main{
		width: 80%;
	}
   
	#notice-content {
			margin-top: 30px;
	}
	
	.textleft{
		text-align: left;
	}
	
	.margin-none{
		margin-top: 0px;
	}
	
	.btn-margin{
		margin-right: 15px;
	}
	
	#wrtieDay{
		margin-bottom: 0px;
	}
	
	.paddingTitle{
		padding-left: 15px;
	}
	
	.marginBotNone{
		margin-bottom: 0px;
	}
	.listBtn{
		float: left;
		margin-right: 4px;
	}
	
	.modBtn{
		float: right;
		margin-right: 15px;
	}
	
	.padding-none{
		padding-left: 0px;
		padding-right: 0px;
	}
	
	/*댓글*/	
	.replyWrite>table {
		width: 90%;
		margin-top : 100px;
	}
	
	#replyContentArea {
		width: 90%;
	}
	
	#replyContentArea>textarea {
		resize: none;
		width: 100%;
	}
	
	#replyBtnArea {
		width: 100px;
	
	}
	
	.rWriter {
		display : inline-block;
		margin-right: 30px;
		vertical-align: top;
	}
	
	.rDate {
		display : inline-block;
		font-size: 0.5em;
		color: gray;
	}
	
	#replyListArea {
		list-style-type: none;
	}
	
	.rContent, .btnArea{
		display: inline-block;
		box-sizing: border-box;
	}
	
	.rContent {
		height: 100%;
		width : 84.5%;
	}
	
	.btnArea {
		height: 100%;
		width : 15%;
		text-align: right;
	}


   
</style>
</head>
<body>


	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %> 
	
		<div class="row" id="content">
	  	<%@ include file="../common/aside.jsp" %>
	  
		  	<div id="content-main" style="padding-right: 15px; margin-top: 48px; " >
	
				<form method="POST" action="updateHosNotice.do?type=<%=type%>&no=<%=boardNo%>&notice=<%=noticeYN%>&cp=<%=cp%>">
                    <div id="notice-area" style="width: 100%;">	
						<div id="title-st" class="row">
		                    <div class="col-md-12 padding-none">
		                    	<h3 id="title">[공지사항 수정 화면]</h3> <br>
								<table>
									<tr>
										<th class="h3" style="line-height:3px;" id="subtitle">제목 : &nbsp;</th>
										<td style="width: 94%;"><input type="text" name="titleInfo" class="form-control" value="<%=board.getBoardTitle() %>"></td>
									</tr>
								</table>
		                    </div>
	                  	</div>
						<hr>
          
		                  <!-- 게시글이 작성된 공간 -->
						<div class="form-group">
							<textarea class="form-control" id="content" name="content"
							rows="10" style="resize: none;"><%=board.getBoardContent()%></textarea>
						</div>
				
					
	                  <div class="row top-margin width-100 margin-none">
	                    <div class="col-md-12 margin-none padding-none width-100">
		                      <button class="btn btn-primary modBtn ml-1 mr-1">수정</a>                      
	                    </div>
	                  </div>
                 		 <hr>
            		</div>
                </form>
				
		</div>
	</div>
	<%@include file="../common/footer.jsp" %>
	</div>
		

	
		


	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>



</body>

</html>








    