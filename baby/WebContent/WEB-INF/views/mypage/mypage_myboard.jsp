<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.kh.baby.mypage.model.vo.Board"%>
<%@page import="com.kh.baby.mypage.model.vo.Page"%>
<%@page import="java.util.List"%>
<%

	Page pInfo = (Page)request.getAttribute("pInfo");
	List<Board> bList = (List<Board>)request.getAttribute("bList");
	
	int currentPage = pInfo.getCurrentPage();
	int listCount = pInfo.getListCount();
	int maxPage = pInfo.getMaxPage();
	int startPage = pInfo.getStartPage();
	int endPage = pInfo.getEndPage();
	
	int prev = (currentPage-1)/10 * 10;
	int next = (currentPage+9)/10 *10 + 1;
	
%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 작성한 글</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<style>
	
		@font-face { font-family: 'InfinitySans-BoldA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-BoldA1.woff') 
		format('woff'); font-weight: normal; font-style: normal; }
	
     /* ------------------------------------------------------------------------ */
     /* content_main 스타일 */
     #content_main {
       width: 80%;
       padding-right: 15px;
     }

     /* ------------------------------------------------------------------- */

     /* 타이틀 */
     #title{
       text-align: center;
       margin-top: 20px;
       font-family: 'InfinitySans-BoldA1';
     }

     /* 체크박스, 정렬순서 오른쪽으로 정렬 */
     #checkboxFloat > *{
       float: right;
     }

     /* 페이지 번호 중앙으로 */
     .pagination {
       justify-content: center;
     }

     /* ------------------------------------------------------------------------- */
     #label-title{
          color: rgb(56, 190, 243);
       }

       .input-wid{
           width: 70%;
       }
     
     .modal-body input, .modal-body label, .modal-body textarea{
       float: left;
     }
     
     /* nav 스타일 */
     .nav {
     	width: 100%;
     }
     
     .nav-age {
     	width: 15%;
     	background-color: rgb(186, 234, 253);
     }
</style>

  
</head>
<body>
		<div class="container-fluid">
			<%@ include file="../common/header.jsp" %>
		
			<div class="row" id="content">
		  	<%@ include file="../common/aside.jsp" %>
		  
		  
			<div id="content_main">
			
					<div class="section-title"><br>
	          <h2 id="title">내가 작성한 글</h2>
    			</div>
			   
			   <div class="row">
                    <div class="col-md-12">
                        <table class="table table-sm" id="list-table">
                            <thead>
                              <tr>
                                <th scope="col">글번호</th>
                                <th scope="col">게시판 분류</th>
                                <th></th>
                                <th scope="col">제목 [댓글 수]</th>
                                <th scope="col">작성일</th>
                              </tr>
                            </thead>
                            <tbody>
                            	
                            <%if(bList.isEmpty()){ %>
                            	<tr><td colspan="5">존재하는 게시글이 없습니다.</td></tr>
                            <%} else {%>
                            	<%for(int i=0; i<bList.size(); i++){ %>
                            	<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy'년' MM'월' dd'일' HH':'mm':'ss"); %>
                            			<tr>
	                            			<td><%=bList.get(i).getBoardNo() %></td>
	                            			<td><%=bList.get(i).getStringboardType()%> 
	                            				<%if(bList.get(i).getBoardType() == 3) { %>
	                            					[<%=bList.get(i).getCategoryName() %>]
	                            				<% } %>
	                            			</td>
	                            			<td><input type="hidden" value="<%=bList.get(i).getBoardType()%>"></td>
	                            			<td><%=bList.get(i).getBoardTitle()%> [<%=bList.get(i).getReplyCount()%>]</td>
	                            			<td><%=sdf.format(bList.get(i).getBoardCreateDate())%></td>
                            			</tr>	
                            	<%} %>
                            <%} %>

                            </tbody>
                          </table>
                    </div>
                </div>
                
          <!-- 페이지 번호 -->
               <div class="row">
                    <div class="col-md-12">
                      <ul class="pagination">
                      	<%if(currentPage > 10) { %>
                      		<li>
	        					<a class="page-link" href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=1">&lt;&lt;</a>
	        				</li>
	        				<li>
	        					<a class="page-link" href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=<%=prev%>">&lt;</a>
	        				</li>
                      	<%} %>
                      	
						<%for(int p = startPage; p<= endPage; p++) {%>
							<%if(p == currentPage) {%>
								<li><a class="page-link"><%=p %></a></li>
							<%}else { %>
								<li>
	        						<a class="page-link" href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=<%=p%>"><%=p %></a>
	        					</li>
							<%} %>
						<%} %>
						
						<%if(next < maxPage){ %>
		        			<!-- 다음 페이징바[>] -->
		        			<li>
		        				<a class="page-link" href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=<%=next%>">&gt;</a>
		        			</li>
		        			
		        			<!-- 마지막 페이지로 이동[>>] -->
		        			<li>
		        				<a class="page-link" href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=<%=maxPage%>">&gt;&gt;</a>
		        			</li>
		        		<% } %>
                      </ul>
                    </div>
                </div> 
    
			</div>
        
    
    </div>
      
		<%@ include file="../common/footer.jsp" %>
		</div>
		
<script>
$(function(){
	$("#list-table td").click(function(){
		var boardNo = $(this).parent().children().eq(0).text();
		var boardType = $(this).parent().children().eq(2).children().val();
		
		if(boardType == 3) {
			location.href = "<%=request.getContextPath()%>/community/freeView.do?type=" + boardType + "&cp=1&no=" + boardNo;
		} else {
			location.href = "<%=request.getContextPath()%>/community/promoView.do?type=" + boardType + "&cp=1&no=" + boardNo;
		}
		
	}).mouseenter(function(){
		$(this).parent().css("cursor", "pointer");
	
	});
});
</script>
</body>
</html>