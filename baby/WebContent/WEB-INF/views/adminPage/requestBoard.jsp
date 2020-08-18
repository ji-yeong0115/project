<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.adminPage.model.vo.RequestBoard"%>
<%@page import="com.kh.baby.adminPage.model.vo.PageInfo"%>
<%@page import="com.kh.baby.adminPage.model.vo.Attachment"%>
<%@page import="java.util.List"%>

<%
	PageInfo pInfo = (PageInfo)request.getAttribute("pInfo"); 
	List<RequestBoard> list = (List<RequestBoard>)request.getAttribute("requestBoard");
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
<title>신고게시판</title>	
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
	 	<div><h3>요청게시판</h3></div>
		<table class="table table-hover table-striped" id="list-table">
	    	<thead>
	         	<tr>
                  <th>글번호 </th>
                  <th align="left">요청글번호 : 분류</th>
                  <th>요청내용</th>
                  <th>작성자</th>
                  <th>게시글 처리</th>
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
              			for(RequestBoard board : list){
              		%>
              			<tr>
              				<td><%=board.getBoardNo()%></td>
              				<td align="left">
              				<%if(board.getParentNo()!=0){%>
              				<%=board.getParentNo() %> :
              				<%} %>
              				<%=board.getrType()%> 
              				</td>
              				<td class="boardTitle">
              				<!-- 썸네일 추가 -->
             				<%if(fList!=null){
              				 String src = null;
             					for(Attachment at : fList){
             						if(at.getParentBoardNo() == board.getBoardNo()){
             							src = request.getContextPath()+"/resources/AdminPage/"+at.getFileChangeName();
             					%>
             						<img src="<%=src%>">
             					<%} } }	%>
              					<%=board.getrContent()%></td>
              				<td><%=board.getMemberNM()%></td>
              				
              				<td>
              				<%if(board.getrType().equals("생성")){%>
              				<button type="button" class="btn btn-outline-dark" onclick="location.href ='insertform.do?type=1&content=<%=board.getrContent()%>&boardNo=<%=board.getBoardNo()%>'">생성</button>
              				<%}else if(board.getrType().equals("수정")) {%>
              				<button type="button" class="btn btn-outline-primary" onclick="location.href ='updateform.do?boardNo=<%=board.getBoardNo()%>&no=<%=board.getParentNo()%>'">수정</button>
							<%-- <%=request.getContextPath()%>/board/updateForm.do?type=1&no=<%=board.getParentNo()%> --%>
              				<%}else if(board.getrType().equals("삭제")) {%>
              				<button type="button" class="btn btn-outline-danger" onclick="location.href ='checkRequest.do?type=1&no=<%=board.getParentNo()%>&boardNo=<%=board.getBoardNo()%>'">삭제</button>
              				<%} %>
              				</td>
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
        	 	 <a class="page-link" href="<%=request.getContextPath()%>/adminPage/requestBoard.do?cp=1">&lt;&lt;</a>
        	 	</li>
        	 	        	 
        	 	<!-- 이전 페이지로 이동[<] -->	   
        	 	<li>
        	 	 <a class="page-link" href="<%=request.getContextPath()%>/adminPage/requestBoard.do?cp=<%=prev%>">&lt;</a>
        	 	</li>
        	 <%} %>
        	 
       	 	<!-- 10개의 페이지 목록 -->   
       	 	<% for(int p=startPage; p<=endPage ; p++) {%>
       	 	 <%if(p== currentPage){%>
       	 	  <li><a class="page-link"><%=p %></a></li> <!-- 현재 페이지 페이징바는 클릭 불가 -->
       	 	 <%}else{ %>
       	 	  <li>
       	 	   <a class="page-link" href="<%=request.getContextPath()%>/adminPage/requestBoard.do?cp=<%=p%>"><%=p%></a>
       	 	  </li>
       	 	 <%} %>
       	 	<%} %>
       	 	
       	 	<%if(next < maxPage){ %>
       	 	 <!-- 다음 페이징바[>] -->
       	 	 <li>
       	 	  <a class="page-link" href="<%=request.getContextPath()%>/adminPage/requestBoard.do?cp=<%=next%>">&gt;</a>
       	 	 </li>
       	 	 
       	 	 <!-- 마지막 페이지로 이동[>>] -->
      	 		 <li>
       	 	  <a class="page-link" href="<%=request.getContextPath()%>/adminPage/requestBoard.do?cp=<%=maxPage%>">&gt;&gt;</a>
       	 	 </li>	        	 	 
       	 	<%} }%>  
        	</ul>
        </div>
        
        <!-- 검색 -->
        <div>
            <form action="<%=request.getContextPath()%>/adminPage/search.do" method="GET" class="text-center" id="searchForm">
            <input type="hidden" name="type" value="R">
            <input type="hidden" name="cp" value="<%=currentPage %>">
                <div name="searchKey" value="content" class="form-control" style="width:100px; display: inline-block;">내용</div>
                <input type="text" name="searchValue" class="form-control" style="width:25%; display: inline-block;">
                <button class="form-control btn btn-primary" style="width:100px; display: inline-block;">검색</button>
            </form>
        </div>
        </div>  
        </div>
        </div>    
<%@ include file="../common/footer.jsp" %>
</div>	
</body>
</html>
