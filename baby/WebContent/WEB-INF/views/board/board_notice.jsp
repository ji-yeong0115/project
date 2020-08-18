<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.kh.baby.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	Board board = (Board)request.getAttribute("board");

	String cp = request.getParameter("cp");
	String type = request.getParameter("type");
	
	int boardNo = board.getBoardNo();
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>지역별 병원 정보 공지사항 조회</title>
  
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
	  
		  	<div class="my-5" id="content-main" style="padding-left: 15px;">
	
				<div id="notice-area" style="width: 100%;">
	
                  <div class="row width-100 margin-none top-margin">
                    <div class="col-md-12 padding-none ">
                      <p class="h2 width-100" style="text-align: left;" id="title"><%=board.getBoardTitle() %></p>
                      </div>
                  </div>
          
                    <!-- 신고 및 정보 수정/삭제 요청 버튼 -->
                   <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy'년' MM'월' dd'일' HH':'mm':'ss"); %>
                  <div class="row top-margin width-100 margin-none">
                    <div class="col-md-12 margin-none padding-none width-100">
                      <P class="writeDay marginBotNone" style="text-align: left;">작성일 : <%=sdf.format(board.getBoardCreateDate()) %></P>
                      <P class="writeDay marginBotNone" style="text-align: left;">수정일 : <%=sdf.format(board.getBoardModifyDate()) %></P> <br>
                      
                      <% if(loginMember != null && loginMember.getMemberGrade().equals("A")) {%>
	                      <button class="btn btn-primary modBtn" type="submit" id="deleteBtn">삭제</button>
	                      <a href="updateHosBoardForm.do?type=<%=type%>&no=<%=boardNo%>&cp=<%=cp %>" class="btn btn-primary modBtn ml-1 mr-1">수정</a>                      
                      <%} %>
                      
                    </div>
                  </div>

                  <hr>
          
                  <!-- 게시글이 작성된 공간 -->
                  <div class="row solid board-area" style="width: 100%; margin: 0px;" >
					<p style="margin: auto;"><%=board.getBoardContent() %></p>
                  </div>
          

                  <hr>
                  
                  <!-- 좋아요 이미지와 버튼 -->
                  <div class="row top-margin">
                    <div class="col-md-12">
                      <a href="hospitalForm.do?type=<%=type%>&cp=<%=cp%>" class="btn btn-primary" style="float: right;">목록</a>
                    </div>            
                  </div>
				
				
				<%@include file="../board/reply.jsp" %>

			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp" %>
	</div>
		

	<script>
		
		$("#deleteBtn").on("click", function(){
			if(confirm("정말 삭제 하시겠습니까?")) location.href = "deleteHos.do?no=<%=boardNo %>&type=<%=type%>&cp=<%=cp%>";
		});
	
	</script>
		


	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>



</body>

</html>








    