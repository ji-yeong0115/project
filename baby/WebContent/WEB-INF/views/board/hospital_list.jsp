<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.board.model.vo.Board"%>
<%@page import="java.util.List"%>
<%@page import="com.kh.baby.board.model.vo.Page"%>
<%@page import="org.apache.tomcat.util.descriptor.web.LoginConfig"%>
<%@page import="com.kh.baby.board.model.vo.RequestBoard"%>
<%

	Page pInfo = (Page)request.getAttribute("pInfo");
	List<Board> bList = (List<Board>)request.getAttribute("bList");
	
	int currentPage = pInfo.getCurrentPage();
	int listCount = pInfo.getListCount();
	int maxPage = pInfo.getMaxPage();
	int startPage = pInfo.getStartPage();
	int endPage = pInfo.getEndPage();
	int boardType = pInfo.getBoardType();

	
	String searchKey = request.getParameter("searchKey");
	String searchValue = request.getParameter("searchValue");
	
	String url2 = null;
	String searchStr = "";
	
	if(searchKey != null && searchValue != null){
		url2 = request.getContextPath() + "/searchHos?type=" + boardType + "&cp=";
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
<title>지역별 병원 정보</title>
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
     
			.aSt{
				color : black;
			}
 	
 		.aSt:hover { 
 			color: blue; text-decoration: none; font-weight: bold;
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
                        <h2 id="title">지역별 병원 정보</h2>
                        <p>슬기로운 병원 정보</p>
                    </div>
                	</div>
    
                <!-- 야간진료, 주말진료, select -->
                <div class="row padding">
                    <div class="col-md-12" id="checkboxFloat">
	                    <table>
	                    	
	                    	<tr>
	                    		<td>
	                        		<div>
	                        		    <a href="<%=request.getContextPath() %>/board/hospitalForm.do?type=1&cp=<%=currentPage %>&yn=1" class="aSt">전체게시글</a> &nbsp;&nbsp;	                      
                          				<a href="<%=request.getContextPath() %>/timeFilter.do?type=1&cp=<%=currentPage %>&yn=2" class="aSt">야간진료</a> &nbsp; &nbsp;	                      
                          				<a href="<%=request.getContextPath() %>/timeFilter.do?type=1&cp=<%=currentPage %>&yn=3" class="aSt">주말진료</a> &nbsp; &nbsp;
                          				<a href="<%=request.getContextPath() %>/timeFilter.do?type=1&cp=<%=currentPage %>&yn=4" class="aSt">야간/주말진료</a>  &nbsp; &nbsp;
                        			</div>
	                    		</td>
		                    </tr>
	                    
	                    </table>
    				</div>
    			</div>
    
                <!-- 게시판 목록 -->
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-sm" id="list-table">
                            <thead>
                              <tr>
                                <th scope="col">글번호</th>
                                <th scope="col">제목</th>
                                <th scope="col">병원주소</th>
                                <th scope="col">좋아요</th>
                                <th scope="col">조회수</th>
                              </tr>
                            </thead>
                            <tbody>
                            	
                            <%if(bList.isEmpty()){ %>
                            	<tr><td colspan="5">존재하는 게시글이 없습니다.</td></tr>
                            <%} else {%>
                            	<%for(Board board : bList){ %>
                        					<%if(board.getBoardNotice().equals("Y")){ %>
	                            		<tr style="background-color: rgb(255, 255, 185);">
	                            			<td><%=board.getBoardNo() %></td>
	                            			<td><%=board.getBoardTitle() %>
	                            				[<%=board.getReplyCount() %>]
	                            			</td>
	                            			<td></td>
	                            			<td></td>
	                            			<td><%=board.getReadCount() %></td>
	                            		</tr>	
	                            		<%} else { %>
	                            			<tr>
		                            			<td><%=board.getBoardNo() %></td>
		                            			<td><%=board.getBoardTitle() %>
		                            				[<%=board.getReplyCount() %>]
		                            			</td>
		                            			<td><%=board.getHosAddress() %></td>
		                            			<td><%=board.getLikeCount() %></td>
		                            			<td><%=board.getReadCount() %></td>
	                            		</tr>	
	                            		<%} %>
                            		
                            		<%} %>
                            	
                            	<%} %>

                            </tbody>
                          </table>
                    </div>
                </div>
    
                <!-- 병원추가요청 버튼 -->
                <%if(loginMember != null && loginMember.getMemberGrade().equals("G")){ %>
                <div class="row">
                    <div class="col-md-12">
                      <button type="button"  id="requestBtn" class="btn btn-primary btn-sm float-right" data-toggle="modal" data-target="#en-modal">정보 추가 요청</button>
                    </div>
                </div>
                <%} %>
    
                <!-- 페이지 번호 -->
               <div class="row">
                    <div class="col-md-12">
                      <ul class="pagination">
                      	<%if(currentPage > 10) { %>
                      		<li>
	        					<a class="page-link" href="<%=firstPage%>/board/hospitalForm.do?type=<%=boardType%>&cp=1">&lt;&lt;</a>
	        				</li>
	        				<li>
	        					<a class="page-link" href="<%=prevPage%>">&lt;</a>
	        				</li>
                      	<%} %>
                      	
						<%for(int p = startPage; p<= endPage; p++) {%>
							<%if(p == currentPage) {%>
								<li><a class="page-link"><%=p %></a></li>
							<%}else { %>
								<li>
	        						<a class="page-link" href="<%=url2 + p + searchStr%>"><%=p %></a>
	        					</li>
							<%} %>
						<%} %>
						
						<%if(next < maxPage){ %>
		        			<!-- 다음 페이징바[>] -->
		        			<li>
		        				<a class="page-link" href="<%=nextPage%>">&gt;</a>
		        			</li>
		        			
		        			<!-- 마지막 페이지로 이동[>>] -->
		        			<li>
		        				<a class="page-link" href="<%=lastPage%>">&gt;&gt;</a>
		        			</li>
		        		<% } %>
                      </ul>
                    </div>
                </div> 
    
            <!-- 검색창 -->
            <div class="row">
              <div class="col-md-12">
                <form action="<%=request.getContextPath() %>/searchHos" method="get" class="text-center" id="searchForm">
                <input type="hidden" name="type" value="<%=boardType%>">
                <input type="hidden" name="cp" value="<%=currentPage %>">
                  <select name="searchKey" class="form-control" style="width:150px; display: inline-block;" >

                      <option value="title">병원주소</option>
                      <option value="content">병원이름</option>
                      <option value="all" selected>병원이름+병원주소</option>
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

        <!-- Modal -->
	        <div class="modal fade" id="en-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	          <div class="modal-dialog">
	              <div class="modal-content" style="width: 400px;">
	              <div class="modal-header">
	                  <h5 class="modal-title" id="exampleModalLabel">정보 추가 요청</h5>
	                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                  <span aria-hidden="true">&times;</span>
	                  </button>
	              </div>
	              <div class="modal-body">
	                  <form action="CreateInfoForm.do" id="information-en" method="POST">
	                  <input type="hidden" name="type" value="<%=boardType%>">
	                  <input type="hidden" name="requestType" value="C">
	                  <input type="hidden" name="cp" value="<%=currentPage %>">
	                      <input type="text" name="hospitalEn" id="hospitalEn" class="input-wid" placeholder="병원명을 입력해주세요."> <br><br>
	                      <input type="text" name="locationEn" id="locationEn" class="input-wid" placeholder="주소를 입력해주세요."> <br> <br>
	  
	                      <label id="label-title">요청 내용 입력</label> <br>
	                      <textarea name="infoText" id="textArea" cols="40" rows="5" style="resize: none; margin-bottom: 10px;" maxlength="150"></textarea>
	                      <p style="margin-top: 10px;"><span id="counter">0</span>/150<p>
	                      <div class="modal-footer" style="clear: both;">
	                          <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
	                          <button type="submit" class="btn btn-primary" id="inforBtn">요청</button>
	                      </div>
	                  </form>
	              </div>
	              </div>
	          </div>
	      </div>
      
		
    <script>
    
		$(function(){
			$("#list-table td").click(function(){
				var boardNo = $(this).parent().children().eq(0).text();
				
				
				// 쿼리스트링을 이용하여 get방식으로 글번호를 전달
				location.href = "<%=request.getContextPath()%>/board/hospitalView.do?type=<%=boardType%>&cp=<%=currentPage%>&no=" + boardNo;
				
			}).mouseenter(function(){
				$(this).parent().css("cursor", "pointer");
			
			});
		});
		
		// 정보 등록 요청 유효성 검사
		$("#inforBtn").on("click", function() {
			
			if ($("#locationEn").val().trim().length == 0) {
				alert("주소를 입력해주세요.");
				$("#locationEn").focus();
				return false;
			}

			if ($("#hospitalEn").val().trim().length == 0) {
				alert("병원 이름을 입력해주세요.");
				$("#hospitalEn").focus();
				return false;
			}
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