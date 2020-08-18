<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kh.baby.adminPage.model.vo.Attachment"%>
<%@page import="com.kh.baby.adminPage.model.vo.AdminPage"%>
<% 
	AdminPage adminpage = (AdminPage)request.getAttribute("noticeView");
	ArrayList<Attachment> fList = (ArrayList<Attachment>)request.getAttribute("fList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고게시판 상세조회</title>
<style>
	#board-area{ 
		width :100%;
		height : 100%;
		margin-bottom:100px;
		} 
	#board-content{ padding-bottom:150px;}

	.boardImgArea{
		height: 300px;
	}

	.boardImg{
		width : 100%;
		height: 100%;
		
		max-width : 300px;
		max-height: 300px;
		
		margin : auto;
	} 
	.carousel-control-prev-icon {
 		background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E") !important;
	}
	
	.carousel-control-next-icon {
  		background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E") !important;
	}
</style>
</head>
<body>
	<div class="container-fluid">
	<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<div class="row" id="content">
  	<%@ include file="/WEB-INF/views/common/aside.jsp" %>  
	
	<div class="container">
				<!-- Category -->
				<h6 class="mt-4" class="float-left">카테고리 : [<%=adminpage.getCategoryName()%>]</h6>
				<hr>
				<!-- Title -->
				<h3 class="mt-4" class="float-left"><%=adminpage.getBoardTitle()%></h3>
				<hr>
				<!-- Writer -->
				<p class="lead" align=right>
					작성자 : <%=adminpage.getMemberName()%>
				</p>

				<!-- Date -->
				<span class="float-left">조회수 <%= adminpage.getReadCount() %></span>
				<span class="float-right"><%=adminpage.getCreateDate()%></span>

				<hr>
				
               <% if(fList != null){ %>
					<div class="carousel slide m-3" id="carousel-325626">
                    <div class="carousel-inner boardImgArea">
                     <% 
	                   		String src = null;
	                    	boolean flag = true;
                   			for(int i=0; i<4 ; i++) {
                   			 for(Attachment at : fList){
	                    	  if(at.getFileLevel() == i){
	                    		 src = request.getContextPath()+"/resources/uploadImages/"+at.getFileChangeName();
	                    		 String imgClass = "carousel-item";
	                    	   
	                    		 if(flag){
	                    			imgClass += " active";
	                    			flag=false;
	                    		 }
                 	   %> 	  
	                    	  <div class="<%= imgClass%>">
	                          <img class="d-block w-100 boardImg" src="<%= src %>" />
	                          <input type="hidden" value=<%=at.getFileNo() %>>
	                         </div> 
	                    	
	                    <%  } } } %>
	                    
                    </div> 
                    
                    <a class="carousel-control-prev" href="#carousel-325626" data-slide="prev"><span class="carousel-control-prev-icon"></span> <span class="sr-only">Previous</span></a> <a class="carousel-control-next" href="#carousel-325626" data-slide="next"><span class="carousel-control-next-icon"></span> <span class="sr-only">Next</span></a>
                </div>
                <% } %>
				<!-- Content -->
				<div id="board-content"><%= adminpage.getBoardContent() %></div>
				<hr>
				<div class="float-right">
				<button type="button" class="btn btn-outline-primary" onclick="location.href ='deleteNotice.do?boardNo=<%=adminpage.getBoardNo()%>'">삭제</button>
				<button type="button" class="btn btn-outline-primary" onclick="location.href ='noticeBoard.do'">목록으로</button>
				</div>
			</div>
		</div>
		<%@ include file="../common/footer.jsp"%>
	</div>

<script>
$("#deleteBtn").on("click",function(){
	if(confirm("정말 삭제하시겠습니까?")){
		location.href = "deleteReport.do?no=<%=adminpage.getBoardNo()%>";
	}
});
</script> 
</body>
</html>
