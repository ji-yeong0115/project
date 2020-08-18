<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.community.model.vo.Attachment"%>
<%@page import="com.kh.baby.community.model.vo.PageInfo"%>
<%@page import="com.kh.baby.community.model.vo.Board"%>
<%@page import="java.util.List"%>
    
<%
	PageInfo pInfo = (PageInfo)request.getAttribute("pInfo");
	List<Board> freeList = (List<Board>)request.getAttribute("freeList");
	
	int currentPage = pInfo.getCurrentPage();
	int listCount = pInfo.getListCount();
	int maxPage = pInfo.getMaxPage();
	int startPage = pInfo.getStartPage();
	int endPage = pInfo.getEndPage();
	int boardType = pInfo.getBoardType();
	
	// 페이징바 자리
	int prev = (currentPage - 1)/pInfo.getLimit() * pInfo.getLimit(); // < 버튼
	int next = (currentPage + pInfo.getLimit()-1)/pInfo.getLimit() * pInfo.getLimit() + 1; // > 버튼
	
	String searchKey = request.getParameter("searchKey");
	String searchValue = request.getParameter("searchValue");
	
	String sortType = request.getParameter("sortType");
	
	String url2 = null;
	String searchStr = "";
	
	if(searchKey != null && searchValue != null){
		url2 = request.getContextPath() + "/communitySearch?type=" + boardType + "&cp=";
		searchStr = "&searchKey="+searchKey+"&searchValue="+searchValue;
	}else{
		url2 = request.getContextPath() + "/community/freeList.do?type=" + boardType + "&cp=";
	}
	

	
	String firstPage = url2 + 1 + searchStr;
	String prevPage = url2 + prev + searchStr;
	String nextPage = url2 + next + searchStr;
	String lastPage = url2 + maxPage + searchStr;
	
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
<title>자유게시판(커뮤니티) 글목록 조회</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

<style>
	
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
          @font-face { font-family: 'ImcreSoojin'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.3/ImcreSoojin.woff') 
          format('woff'); font-weight: normal; font-style: normal; }

          /* 타이틀 */
          #title{
            font-family: 'ImcreSoojin';
            float: left;
            margin-top: 20px;
          }

          /* 체크박스, 정렬순서 오른쪽으로 정렬 */
          #checkboxFloat > *{
            float: right;
          }
          
          /*분류 필터 오른쪽 정렬*/
          #filterFloat > *{
            float: right;
            display : inline-block;
          }
          
          .filterform {
          	float : left;
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
          
          /* ---------------------------------분류---------------------------------------*/
				
			 

					.select p {
					 width:100px;background: url("bg_selectbox.gif") no-repeat; font: 12px/20px "돋움", "dotum"; padding: 0px 14px; border: 1px solid rgb(171, 173, 179); height: 20px; color: rgb(51, 51, 51); overflow: hidden; cursor: pointer; font-size-adjust: none; font-stretch: normal;;margin:0 auto;
					}
					.select p.on {
					 background-position: right -160px; border: 1px solid rgb(0, 144, 227); color: rgb(0, 144, 227);
					}
					.select .sub {
					 padding: 6px 0px; border: 1px solid rgb(171, 173, 179); left: 0px; top: 0px; display: none; position: relative; z-index: 10; overflow-x: hidden; overflow-y: scroll; max-height: 168px; background-color: rgb(255, 255, 255);margin:0 auto;
					}
					.select .sub {
					 width: 100px;
					}
					.select .sub .optionList {
					 width: 100px; overflow: hidden; float: left; display: inline-block;cursor:pointer;font: 12px/20px "돋움", "dotum";
					}
					.select .sub .optionList:hover {
					 background-color: rgb(108, 108, 108);
					}
					.select .sub .optionList:hover li {
					 color: rgb(255, 255, 255);
					}
					.select .sub .optionList li {
					 padding: 5px 15px; color: rgb(51, 51, 51);
					}
					.select .sub .optionList li.name {
					 padding: 5px 0px 5px 15px; width: 210px; float: left; display: inline;
					}
					.select .sub .optionList li.price {
					 padding: 5px 10px; width: 70px; text-align: right; float: left; display: inline;
					}
					.select .sub .optionList li.remain {
					 padding: 5px 0px; width: 81px; float: left; display: inline;
					}
					 
          
			         
</style>

</head>
<body>
		<div class="container-fluid">
			<%@ include file="../common/header.jsp" %>
		
			<div class="row" id="content">
		  <%@ include file="../common/aside.jsp" %>
		  
		  
               <div id="content_main">
                  <div class="row">
                    <div class="col-md-12">
                        <h2 id="title">커뮤니티(자유게시판)</h2>
                    </div>
                </div>
             
                  <!-- 상단 분류 SELECT -->
           <%--     <div class="row padding">
                    <div class="col-md-12" id="checkboxFloat">
    
                      <div>
                        <form action="<%=request.getContextPath() %>/community/freeSort.do" method="get" id="freeSort">
                          <select name="sortType" id="sortType">
                            <option selected>전체보기</a></option>
                            <option value="질문" href="#">질문</option>
                            <option value="나눔">나눔</option>
                            <option value="수다">수다</option>
                          </select>
                        </form>
                      </div>
                  </div>   
                </div> --%>
                
              <div class="row padding">
              <div class="col-md-12" id="filterFloat">
                <form action="<%=request.getContextPath() %>/community/freeSort.do" method="get" id="freeSort">
                <input type="hidden" name="type" value="<%=boardType%>">
                <input type="hidden" name="cp" value="1">
                  <select name="sortType" class="form-control filterform" style="width:100px;" display: inline-block;>
                      <option value="전체보기">전체보기</option>
                      <option value="나눔">나눔</option>
                      <option value="수다">수다</option>
                      <option value="질문">질문</option>
                  </select> 
                  <button class="form-control btn btn-primary filterform" style="width:100px;" id="filterbtn">적용</button>
              </form>
             
          </div>
              </div> 
              
   
 
                

       
                <!-- 게시판 목록 -->
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-sm" id="list-table">
                            <thead>
                              <tr>
                                <th scope="col">글번호</th>
                                <th scope="col">분류</th>
                                <th scope="col">제목</th>
                                <th scope="col">작성자</th>
                                <th scope="col">조회수</th>
                                <th scope="col">좋아요</th>
                                <th scope="col">작성일</th>
                              </tr>
                            </thead>
                            <tbody>
                            
                            <%if (freeList.isEmpty()){ %>
                            	<tr><td colspan="6">존재하는 게시글이 없습니다.</td>
                            
                               <% } else {  %>
                               
                               
	              			<% for (Board board : freeList) { %>
	              			
	              			<%if(board.getBoardNotice().equals("Y")) {%>
                            	
                            	<th scope="row"><%=board.getBoardNo() %></th>
                                <td><%=board.getCategoryName() %></td>
                                <td><%=board.getBoardTitle() %></td>
                                <td><%=board.getMemberName() %></td>
                                <td><%=board.getReadCount() %></td>
                                <td><%=board.getBoardLike() %></td>
                                <td><%=board.getBoardCreateDate() %></td>
                              </tr>	
                            			
                            	<%} else {%>		
                                <th scope="row"><%=board.getBoardNo() %></th>
                                <td><%=board.getCategoryName() %></td>
                                <td><%=board.getBoardTitle() %></td>
                                <td><%=board.getMemberName() %></td>
                                <td><%=board.getReadCount() %></td>
                                <td><%=board.getBoardLike() %></td>
                                <td><%=board.getBoardCreateDate() %></td>
                              </tr>
                              
                             		<% } %>	
                           		<% } %>
	            		 			  <% } %>
                            </tbody>
                          </table>
                    </div>
                </div>
                
                  <%-- 로그인이 되어있는 경우 --%>
	      	<%if(loginMember != null && (loginMember.getMemberGrade().equals("G") ||loginMember.getMemberGrade().equals("A")) ){ %>
    
                <!-- 글쓰기 버튼 -->
                <div class="row">
                    <div class="col-md-12">
                      <button type="button"  id="requestBtn" class="btn btn-primary btn-sm float-right" onclick="location.href ='freeInsertForm.do?type=<%=boardType%>'">글쓰기</button>
                    </div>
                </div>
    
    			<% } %>
    
    	<% if (sortType ==null ) { %>
                <!-- 페이지 번호 -->
                   <% if(!freeList.isEmpty()) { %>
         				<div style="clear: both">
						<ul class="pagination">
							<%if(currentPage > pInfo.getLimit()) { %>
									<!-- 맨 처음 페이지로 이동 [<<] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=1">&lt;&lt;</a>
									</li>

									<!-- 이전 순번의 페이징바로 이동 [<] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=prev%>">&lt;</a>
									</li>
							<%} %>
									
							<!-- 5개의 페이지 목록 -->
							<%for(int p=startPage; p<=endPage; p++) { %>
									<%if(p == currentPage) { %>
											<li><a class="page-link"><%=p%></a></li>
									<%} else { %>
											<li><a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=p%>"><%=p%></a></li>
									<%} %>
							<%} %>
							
							<%if(next <= maxPage) { %>
									<!-- 다음 페이징바 [>] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=next%>">&gt;</a>
									</li>
									
									<!-- 마지막 페이지로 이동 [>>] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=maxPage%>">&gt;&gt;</a>
									</li>
							<%} %>
						</ul>	        
	        </div>
	        <% } } else { %>
	        
	        	 <!-- 페이지 번호 -->
                   <% if(!freeList.isEmpty()) { %>
         				<div style="clear: both">
						<ul class="pagination">
							<%if(currentPage > pInfo.getLimit()) { %>
									<!-- 맨 처음 페이지로 이동 [<<] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=1&sortType=<%=sortType%>">&lt;&lt;</a>
									</li>

									<!-- 이전 순번의 페이징바로 이동 [<] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=prev%>&sortType=<%=sortType%>">&lt;</a>
									</li>
							<%} %>
									
							<!-- 5개의 페이지 목록 -->
							<%for(int p=startPage; p<=endPage; p++) { %>
									<%if(p == currentPage) { %>
											<li><a class="page-link"><%=p%></a></li>
									<%} else { %>
											<li><a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=p%>&sortType=<%=sortType%>"><%=p%></a></li>
									<%} %>
							<%} %>
							
							<%if(next <= maxPage) { %>
									<!-- 다음 페이징바 [>] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=next%>&sortType=<%=sortType%>">&gt;</a>
									</li>
									
									<!-- 마지막 페이지로 이동 [>>] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/community/freeList.do?type=<%=boardType%>&cp=<%=maxPage%>&sortType=<%=sortType%>">&gt;&gt;</a>
									</li>
							<%} %>
						</ul>	        
	        </div>
	        <% } %>
	        
	        <% } %>
    
               <!-- 검색창 -->
            <div class="row">
              <div class="col-md-12">
                <form action="<%=request.getContextPath() %>/communitySearch" method="get" class="text-center" id="searchForm">
                <input type="hidden" name="type" value="<%=boardType%>">
                <input type="hidden" name="cp" value="<%=currentPage %>">
                  <select name="searchKey" class="form-control" style="width:100px; display: inline-block;">
                      <option value="title">글제목</option>
                      <option value="content">내용</option>
                      <option value="all">제목+내용</option>
                  </select>
                  <input type="text" name="searchValue" class="form-control" style="width:25%; display: inline-block;" placeholder="검색키워드를 입력해주세요.">
                  <button class="form-control btn btn-primary" style="width:100px; display: inline-block;" id="seachbtn">검색</button>
              </form>
              </div>
          </div>
              </div>
            </div>
           

		<%@ include file="../common/footer.jsp" %>
        </div>

       
		
    <script>
		// 게시글 상세보기 기능 (jquery를 통해 작업)
		$(function(){
			$("#list-table td").click(function(){
				var boardNo = $(this).parent().children().eq(0).text();
				console.log(boardNo);
				location.href = "<%=request.getContextPath()%>/community/freeView.do?type=<%=boardType%>&cp=<%=currentPage%>&no="+boardNo;
			}).mouseenter(function(){
				$(this).parent().css("cursor", "pointer");
			
			});
		});
		


		
		
		// 검색 기능
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
		
		
		
		// 분류 필터 기능
		$(function(){
			var sortType = "<%=sortType%>";
			
			if(sortType != "null" ){
				$.each($("select[name=sortType] > option"), function(index, item){
					if($(item).val() == sortType){
						$(item).prop("selected","true");
					} 
				});
			}
				
		}); 
		

		$(document).ready(function(){  
		  $(".select p").click(function(){    
		   $(".select p").addClass("on");  
		   $(".select .sub").slideToggle("fast");   
		   
		   	$(".select .sub").click(function(){
		   			console.log("클릭");
		   			
		   	});

		  });
		 
		  $(".select .sub").mouseleave(function() {
		   $(".select p").removeClass("on"); 
		  $(this).hide();
		 });


		});
	
		
		
		// 글자수 제한
        $("textarea").on("input", function() {
	           
	           var inputLength = $(this).val().length;
	     
	           $("#counter").text(inputLength);
	     
	           if(inputLength - 150 < 0) {
	             $("#counter").css("color", "black");
	           } else {
	             $("#counter").css("color", "red");
	           }
	           
	           if(inputLength > 150){
	        	   alert("최대 150자까지 입력 가능합니다.");	        	   
	           }
	         });
		
        
        
	</script>
</body>
</html>