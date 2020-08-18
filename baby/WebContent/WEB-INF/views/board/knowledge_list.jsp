<%@page import="com.kh.baby.board.model.vo.Board"%>
<%@page import="java.util.List"%>
<%@page import="com.kh.baby.board.model.vo.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	Page pInfo = (Page)request.getAttribute("pInfo");
	List<Board> bList = (List<Board>)request.getAttribute("bList");
	
	int currentPage = pInfo.getCurrentPage();
	int listCount = pInfo.getListCount();
	int maxPage = pInfo.getMaxPage();
	int startPage = pInfo.getStartPage();
	int endPage = pInfo.getEndPage();
	int boardType = pInfo.getBoardType();
	int age = pInfo.getCommonAge();
	
	String searchKey = request.getParameter("searchKey");
	String searchValue = request.getParameter("searchValue");
	
	String url2 = null;
	String searchStr = "";
	
	if(searchKey != null && searchValue != null){
		url2 = request.getContextPath() + "/searchKnowledge?type=" + boardType + "&cp=";
		searchStr = "&searchKey="+searchKey+"&searchValue="+searchValue;
	}else{
		url2 = request.getContextPath() + "/board/hospitalForm.do?type=" + boardType + "&cp=";
	}
	
	/* int prev = (currentPage-1)/10 * 10; */
	int prev = (currentPage/10-1)*10 == 0 ? 10 : (currentPage-1)/10*10;
	int next = (currentPage+9)/10 *10 + 1;
	
	String firstPage = url2 + 1 + searchStr;
	String prevPage = url2 + prev + searchStr;
	String nextPage = url2 + next + searchStr;
	String lastPage = url2 + maxPage + searchStr;
	
%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연령대별 육아 상식</title>
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
     
     /* #content{
       margin-left: 0;
       margin-right: 0;
     } */

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
     #navCenter {
     	width: 90%;
     	margin-left: 140px;
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
	          <h2 id="title">정보공유방</h2>
	          <p>슬기로운 육아 상식</p>
    			</div>

			   <div class="row" style="width: 100%;">
			   		<div class="col-md-12" style="width: 100%;">
			   			<ul class="nav nav-tabs" id="navCenter">
							  <li class="nav-age">
							    <a class="nav-link active" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=0">전체</a>
							  </li>
							  <li class="nav-age">
							    <a class="nav-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=3">0~3세</a>
							  </li>
							  <li class="nav-age">
							    <a class="nav-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=4">4세</a>
							  </li>
							  <li class="nav-age">
							    <a class="nav-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=5">5세</a>
							  </li>
							  <li class="nav-age">
							    <a class="nav-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=6">6세</a>
							  </li>
							  <li class="nav-age">
							    <a class="nav-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=7">7세</a>
							  </li>
							</ul>
			   		</div>
			   </div>
			   
			   <div class="row">
                    <div class="col-md-12">
                        <table class="table table-sm" id="list-table">
                            <thead>
                              <tr>
                                <th scope="col">글번호</th>
                                <th scope="col">제목</th>
                                <th scope="col">연령대</th>
                                <th scope="col">조회수</th>
                              </tr>
                            </thead>
                            <tbody>
                            	
                            <%if(bList.isEmpty()){ %>
                            	<tr><td colspan="5">존재하는 게시글이 없습니다.</td></tr>
                            <%} else {%>
                            	<%for(Board board : bList){ %>
                            			<tr>
	                            			<td><%=board.getBoardNo() %></td>
	                            			<td><%=board.getBoardTitle() %></td>
	                            			<td><%=board.getCommonAge() %></td>
	                            			<td><%=board.getReadCount() %></td>
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
	        					<a class="page-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=<%=boardType%>&cp=1&age=<%=age%>">&lt;&lt;</a>
	        				</li>
	        				<li>
	        					<a class="page-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=<%=boardType%>&cp=<%=prev%>&age=<%=age%>">&lt;</a>
	        				</li>
                      	<%} %>
                      	
						<%for(int p = startPage; p<= endPage; p++) {%>
							<%if(p == currentPage) {%>
								<li><a class="page-link"><%=p %></a></li>
							<%}else { %>
								<li>
	        						<a class="page-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=<%=boardType%>&cp=<%=p%>&age=<%=age%>"><%=p %></a>
	        					</li>
							<%} %>
						<%} %>
						
						<%if(next < maxPage){ %>
		        			<!-- 다음 페이징바[>] -->
		        			<li>
		        				<a class="page-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=<%=boardType%>&cp=<%=next%>&age=<%=age%>">&gt;</a>
		        			</li>
		        			
		        			<!-- 마지막 페이지로 이동[>>] -->
		        			<li>
		        				<a class="page-link" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=<%=boardType%>&cp=<%=maxPage%>&age=<%=age%>">&gt;&gt;</a>
		        			</li>
		        		<% } %>
                      </ul>
                    </div>
                </div> 
    
            <!-- 검색창 -->
            <div class="row">
              <div class="col-md-12">
                <form action="<%=request.getContextPath() %>/searchKnowledge" method="GET" class="text-center" id="searchForm">
                <input type="hidden" name="type" value="<%=boardType%>">
                <input type="hidden" name="cp" value="<%=currentPage %>">
                  <select name="searchKey" class="form-control" style="width:100px; display: inline-block;">
                      <option value="all">제목+내용</option>
                      <option value="title">제목</option>
                      <option value="content">내용</option>
                  </select>
                  <input type="text" name="searchValue" class="form-control" style="width:25%; display: inline-block;" placeholder="검색할 키워드를 입력해주세요.">
                  <button class="form-control btn btn-primary" style="width:100px; display: inline-block;">검색</button>
              </form>
              </div>
          </div>
			</div>
        
    
    </div>
      
		<%@ include file="../common/footer.jsp" %>
		</div>
		
		<script>
/* 			$(".nav-age a").on("click", function() {
				$(".nav-age").find("a.active").removeClass("active");
				$(this).addClass("active");
			}); */
			
 			$(function(){
				$("#list-table td").click(function(){
					var boardNo = $(this).parent().children().eq(0).text();
					
					
					// 쿼리스트링을 이용하여 get방식으로 글번호를 전달
					location.href = "<%=request.getContextPath()%>/board/knowledgeView.do?type=<%=boardType%>&cp=<%=currentPage%>&no=" + boardNo;
					
				}).mouseenter(function(){
					$(this).parent().css("cursor", "pointer");
				
				});
			});
			
			$(function(){
				var searchKey = "<%= searchKey %>";
				var searchValue = "<%= searchValue %>";
				
				if(searchKey != "null" && searchValue != "null"){
					$.each($("select[name=searchKey] > option"), function(index, item){
						if($(item).val() == searchKey){
							$(item).prop("selected","true");
						} 
					});
					
					$("input[name=searchValue]").val(searchValue);
				}
					
			});

		</script>
</body>
</html>