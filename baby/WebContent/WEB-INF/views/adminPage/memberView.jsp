<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.adminPage.model.vo.PageInfo"%>
<%@page import="com.kh.baby.member.model.vo.Member"%>
<%@page import="java.util.List"%>
	
<%
	PageInfo pInfo = (PageInfo)request.getAttribute("pInfo"); 
	List<Member> list = (List<Member>)request.getAttribute("memberView");
	
	int currentPage = pInfo.getCurrentPage();
	int listCount = pInfo.getListCount();
	int maxPage = pInfo.getMaxPage();
	int startPage = pInfo.getStartPage();
	int endPage = pInfo.getEndPage();
	String boardStatus = pInfo.getBoardStatus();
	 
	int prev = (currentPage-1)/10 *10;
	int next = (currentPage+9)/10 *10+1; 
%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 조회</title>
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
	</style>
	
</head>
<body>
<div class="container-fluid">
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="row" id="content">
 <%@ include file="/WEB-INF/views/common/aside.jsp" %>  
 
	<div class="container">
        <div class="my-5">
        <div><h3>회원 정보 조회</h3></div>
        <form id="frm" style="text-align: right">
				<select name="select1" onchange="change();" style="text-align: right">
					<option value="sel" id="sel">=조건 선택=</option>
					<option value="memberStatus" id="status">회원 상태</option>
					<option value="memberGrade" id="grade">회원 등급</option>
				</select>
				<div id="select2" style="text-align: right"></div> 
		</form>
		
	            <table class="table table-hover table-striped" id="list-table">
	                <thead>
	                    <tr>
	                        <th>회원 번호</th>
	                        <th>아이디</th>
	                        <th>이름</th>
	                        <th>닉네임</th>
	                        <th>이메일</th>
	                        <th>주소</th>
	                        <th>회원 상태</th>
	                        <th>회원 등급</th>
	                        <th>가입일</th>
	                    </tr>
	                </thead>
	                <tbody>
	                <% if(list.isEmpty()) { %>
	               		<tr><td colspan="6">존재하는 회원이 없습니다.</td></tr>
	               	<% } else{ %>
	               		<% for(Member member : list){ %>
	               			<tr>
	               				<td><%=member.getMemberNo()%></td>
	               				<td><%=member.getMemberId()%></td>
	               				<td><%=member.getMemberName()%></td>
	               				<td><%=member.getMemberNickname()%></td>
	               				<td><%=member.getMemberEmail()%></td>
	               				<td><%=member.getMemberAddress()%></td>
	               				<td><%=member.getMemberStatus()%></td>
	               				<td><%=member.getMemberGrade()%></td>
	               				<td><%=member.getMemberEnrollDate()%></td>
	                  	    </tr>
	                  	    <%} } %>
	               </tbody>
	           </table>
	       
	        <!-- 페이징바 -->
	        <%if(!list.isEmpty()) {%>
	        <div style="clear:both">
	        	<ul class = "pagination">
	        	 <%if(currentPage > 10) { %>
	        	 	<!-- 맨 처음 페이지로 이동[<<] -->	
	        	 	<li>
	        	 	 <a class="page-link" href="<%=request.getContextPath()%>/adminPage/memberView.do?type=<%=request.getParameter("type")%>&cp=1">&lt;&lt;</a>
	        	 	</li>
	        	 	        	 
	        	 	<!-- 이전 페이지로 이동[<] -->	   
	        	 	<li>
	        	 	 <a class="page-link" href="<%=request.getContextPath()%>/adminPage/memberView.do?type=<%=request.getParameter("type")%>&cp=<%=prev%>">&lt;</a>
	        	 	</li>
	        	 <%} %>
	        	 
        	 	<!-- 10개의 페이지 목록 -->   
        	 	<% for(int p=startPage; p<=endPage ; p++) {%>
        	 	 <%if(p== currentPage){%>
        	 	  <li><a class="page-link"><%=p %></a></li> <!-- 현재 페이지 페이징바는 클릭 불가 -->
        	 	 <%}else{ %>
        	 	  <li>
        	 	   <a class="page-link" href="<%=request.getContextPath()%>/adminPage/memberView.do?type=<%=request.getParameter("type")%>&cp=<%=p%>"><%=p%></a>
        	 	  </li>
        	 	 <%} %>
        	 	<%} %>
        	 	
        	 	<%if(next < maxPage){ %>
        	 	 <!-- 다음 페이징바[>] -->
        	 	 <li>
        	 	  <a class="page-link" href="<%=request.getContextPath()%>/adminPage/memberView.do?type=<%=request.getParameter("type")%>&cp=<%=next%>">&gt;</a>
        	 	 </li>
        	 	 
        	 	 <!-- 마지막 페이지로 이동[>>] -->
       	 		 <li>
        	 	  <a class="page-link" href="<%=request.getContextPath()%>/adminPage/memberView.do?type=<%=request.getParameter("type")%>&cp=<%=maxPage%>">&gt;&gt;</a>
        	 	 </li>	        	 	 
        	 	<%} } %>  
	        	</ul>
	        </div>
	        </div>    
	        
	        <!-- 검색 -->
	        <div>
	            <form action="searchM.do" method="GET" class="text-center" id="searchForm">
	                <select name="searchKey" class="form-control" style="width:100px; display: inline-block;">
	                    <!-- <option value="title" selected>글제목</option> -->
	                    <option value="id">아이디</option>
	                    <option value="name">성함</option>
	                    <option value="address">주소</option>
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
function change(){
	var select2 = document.getElementById("select2");
	select2.innerHTML = ""; 
	
	if($("#status").prop("selected")){ 
	select2.innerHTML+="<input type='radio' name='status2' id='C'><label for='C'>전체</label><input type='radio' name='status2' id='Y'><label for='Y'>정상</label><input type='radio' name='status2' id='N'><label for='N'>탈퇴</label>";

	}else if($("#grade").prop("selected")){
		select2.innerHTML+="<input type='radio' name='grade2' id='C'><label for='C'>전체</label><input type='radio' name='grade2' id='G'><label for='G'>일반회원</label><input type='radio' name='grade2' id='S'><label for='S'>판매자</label>";
	}
}

$(document).on("click", "input[name='status2']", function(){
	if($("#Y").prop("checked")){
		location.href ="memberView.do?type=status_y";
	}else if($("#N").prop("checked")){
			location.href ="memberView.do?type=status_n"; 		
	}else{
		location.href ="memberView.do?type=0"; 
	}

});
$(document).on("click", "input[name='grade2']", function(){
	if($("#G").prop("checked")){
		location.href ="memberView.do?type=grad_g"; 
	}else if($("#S").prop("checked")){
			location.href ="memberView.do?type=grad_s"; 
	}else{
		location.href ="memberView.do?type=0"; 
	}	
	
});
$(function(){
	var type = "<%=request.getParameter("type")%>";
	console.log(type);
	if(type != null){
		if(type=="status_y" || type=="status_n"){
			$("#status").prop("selected", true);
			change();
			if(type=="status_y"){
				$("#Y").prop("checked", true);
			}else if(type=="status_n"){
				$("#N").prop("checked", true);
			}
		}else if(type=="grad_g" || type=="grad_s"){
			$("#grade").prop("selected", true);
			change();
			if(type=="grad_g"){
				$("#G").prop("checked", true);
			}else if(type=="grad_s"){
				$("#S").prop("checked", true);
			}
		}
	}
});

</script>	
</body>
</html> 

