
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.adminPage.model.vo.AdminPage"%>
<%@page import="com.kh.baby.adminPage.model.vo.PageInfo"%>
<%@page import="com.kh.baby.adminPage.model.vo.Attachment"%>
<%@page import="java.util.List"%>

<%
	PageInfo pInfo = (PageInfo)request.getAttribute("pInfo"); 
	List<AdminPage> list = (List<AdminPage>)request.getAttribute("noticeBoard");
	List<Attachment> fList = (List<Attachment>)request.getAttribute("fList");
	
	int currentPage = pInfo.getCurrentPage();
	int listCount = pInfo.getListCount();
	int maxPage = pInfo.getMaxPage();
	int startPage = pInfo.getStartPage();
	int endPage = pInfo.getEndPage();
	int boardType = pInfo.getBoardType();
	String boardStatus = pInfo.getBoardStatus();
	 
	int prev = (currentPage-1)/10 *10;
	int next = (currentPage+9)/10 *10+1; 
%>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<style>
	.pagination {
        justify-content: center;
    }
    #searchForm{
        position: relative;
    }

    #searchForm>*{
        top : 0;
    }
    
    .boardTitle > img{
    	width: 50px;
    	height: 50px;
    }
    #ctt{
    	color: darkgrey;
    	text-align: left;
    }
    
</style>
	
</head>
<body>
	<div class="container-fluid">
	<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="/WEB-INF/views/common/aside.jsp" %>  
  
		<div class="container">
	        <div class="my-5">
	        <div><h3>공지사항</h3></div>
	            <table class="table table-hover table-striped" id="list-table">
	                <thead>
	                    <tr>
	                        <th>글번호 </th>
	                        <th>카테고리 </th>
	                        <th>제목</th>
	                        <th>작성자</th>
	                        <th>조회수</th>
	                        <th>작성일</th>
	                    </tr>
	                </thead>
	                <tbody>
	                	<%
	                		if(list.isEmpty()) {
	                	%>
	                		<tr><td colspan="6">존재하는 게시글이 없습니다.</td></tr>
	                	<%
	                		} else{
	                	%>
	                		<%
	                			for(AdminPage adminpage : list){
	                		%>
	                			<tr>
	                				<td><%=adminpage.getBoardNo()%></td>
	                				<td><%=adminpage.getCategoryName()%></td>
	                				<td class="boardTitle" align="left">
	                				<!-- 썸네일 추가 -->
                					<%
	                				 String src = null;
                					if(fList!=null){
                					for(Attachment at : fList){
                						if(at.getParentBoardNo() == adminpage.getBoardNo()){
                							src = request.getContextPath()+"/resources/uploadImages/"+at.getFileChangeName();
                					%>
                						<img src="<%=src%>">
                					<% } } } %>
	                					<%=adminpage.getBoardTitle()%><br><div id="ctt"><%=adminpage.getBoardContent()%></div>
	                					</td>
	                				<td><%=adminpage.getMemberName()%></td>
	                				<td><%=adminpage.getReadCount()%></td>
	                				<td><%=adminpage.getCreateDate()%></td>
	                			</tr>
	                   		<%} %>
	                	<%} %>
	                </tbody>
	            </table>
	        
	        <!-- 페이징바 -->
	        <%if(!list.isEmpty()) {%>
	        <div style="clear:both">
	        	<ul class = "pagination">
	        	 <%if(currentPage > 10) { %>
	        	 	<!-- 맨 처음 페이지로 이동[<<] -->	
	        	 	<li>
	        	 	 <a class="page-link" href="<%=request.getContextPath()%>/adminPage/noticeBoard.do?cp=1">&lt;&lt;</a>
	        	 	</li>
	        	 	        	 
	        	 	<!-- 이전 페이지로 이동[<] -->	   
	        	 	<li>
	        	 	 <a class="page-link" href="<%=request.getContextPath()%>/adminPage/noticeBoard.do?cp=<%=prev%>">&lt;</a>
	        	 	</li>
	        	 <%} %>
	        	 
        	 	<!-- 10개의 페이지 목록 -->   
        	 	<% for(int p=startPage; p<=endPage ; p++) {%>
        	 	 <%if(p== currentPage){%>
        	 	  <li><a class="page-link"><%=p %></a></li> <!-- 현재 페이지 페이징바는 클릭 불가 -->
        	 	 <%}else{ %>
        	 	  <li>
        	 	   <a class="page-link" href="<%=request.getContextPath()%>/adminPage/noticeBoard.do?cp=<%=p%>"><%=p %></a>
        	 	  </li>
        	 	 <%} %>
        	 	<%} %>
        	 	
        	 	<%if(next < maxPage){ %>
        	 	 <!-- 다음 페이징바[>] -->
        	 	 <li>
        	 	  <a class="page-link" href="<%=request.getContextPath()%>/adminPage/noticeBoard.do?cp=<%=next%>">&gt;</a>
        	 	 </li>
        	 	 
        	 	 <!-- 마지막 페이지로 이동[>>] -->
       	 		 <li>
        	 	  <a class="page-link" href="<%=request.getContextPath()%>/adminPage/noticeBoard.do?cp=<%=maxPage%>">&gt;&gt;</a>
        	 	 </li>	        	 	 
        	 	<%} } %>  
	        	</ul>
	        </div>
	        </div>    
	        
	        <!-- 검색 -->
	        <div>
	        	<form action="<%=request.getContextPath()%>/adminPage/search.do" method="GET" class="text-center" id="searchForm">
	            <input type="hidden" name="type" value="Y">
	            <input type="hidden" name="cp" value="<%=currentPage%>">
	            	<select name="searchKey" class="form-control" style="width:100px; display: inline-block;">
	                    <!-- <option value="title" selected>글제목</option> -->
	                    <option value="title">글제목</option>
	                    <option value="content">내용</option>
	                    <option value="titcont">제목+내용</option>
	                </select>
	                <input type="text" name="searchValue" class="form-control" style="width:25%; display: inline-block;">
	                <button class="form-control btn btn-primary" style="width:100px; display: inline-block;">검색</button>
	            </form>
	       </div>
	   </div>  
	   </div>
	<%@ include file="../common/footer.jsp" %>
	</div>
	
<script>
//------------------------------------------------------------------------------------------------------------
// 게시글 상세보기 기능 (jquery를 통해 작업)
$("#list-table td:nth-of-type(3)").on("click",function(){
	var boardNo = $(this).parent().children().eq(0).text(); // 클릭된 부모(tr)의 첫번쨰 자식(boardNo)의 값
	
	location.href = "noticeView.do?no="+boardNo;
}).on("mouseenter", function(){
	$(this).parent().css("cursor", "pointer");			
})
;
</script>
</body>
</html> 
