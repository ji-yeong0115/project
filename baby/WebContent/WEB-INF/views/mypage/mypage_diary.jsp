<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.mypage.model.vo.DiaryAttachment"%>
<%@page import="com.kh.baby.mypage.model.vo.Diary"%>
<%@page import="com.kh.baby.mypage.model.vo.PageInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
	List<Diary> dList = (List<Diary>)request.getAttribute("dList");
	ArrayList<DiaryAttachment> fList = (ArrayList<DiaryAttachment>)request.getAttribute("fList");
	PageInfo pInfo = (PageInfo)request.getAttribute("pInfo");

	if(fList != null && dList != null && pInfo != null) {
		
		for(int i=0; i<dList.size(); i++) {
			if(dList.get(i) !=null && fList.get(i) == null) {
				fList.set(i, new DiaryAttachment(dList.get(i).getDiaryNo(), "toys.png"));
			}
		}
	}
	
	int currentPage = pInfo.getCurrentPage();
	int listCount = pInfo.getListCount();
	int maxPage = pInfo.getMaxPage();
	int startPage = pInfo.getStartPage();
	int endPage = pInfo.getEndPage();
	int memberNo = pInfo.getMemberNo();
	
	int prev = (currentPage - 1)/pInfo.getLimit() * pInfo.getLimit(); // < 버튼
	int next = (currentPage + pInfo.getLimit()-1)/pInfo.getLimit() * pInfo.getLimit() + 1; // > 버튼
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>육아 일기</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

	<style>
	
    @font-face { font-family: 'ImcreSoojin'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.3/ImcreSoojin.woff') 
    format('woff'); font-weight: normal; font-style: normal; }
   
     @font-face { font-family: 'NEXON Lv1 Gothic OTF'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff')
     format('woff'); font-weight: normal; font-style: normal; }
          
          
     #content_main {
       width: 80%;
       padding-right: 15px;
     }

     #diary_form p {
       text-align: left;
     }

     #diary_form {
       width: 100%;
       float: left;
     }

     #diary_form > div {
       float: left;
     }

     #diary_btn {
       padding-top: 40px;
     }

     .boardImg{
       cursor : pointer;
     }

     #diary_content {
       background-color: rgb(190, 235, 253);
       padding: 15px;
       border: 15px solid white;
     }
     
     #diary_content > img {
     		width: 300px;
     }
     
     /* 페이지 번호 중앙으로 */
     .pagination {
       justify-content: center;
     }
	</style>

</head>
<body>
	
	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %>
		<!-- content-fluid & header -->
		
		<div class="row" id="content">
			<%@ include file="../common/aside.jsp" %>
			<!-- content & aside -->
			
				<div id="content_main">
				<!-- content-main -->
				
				<div class="row">
		       <div class="col-md-12">
		           <h2 id="title">육아 일기</h2>
		       </div>
		     </div>
		
		     <div class="row">
		     <form action="insertDiary.do" method="POST" enctype="multipart/form-data" id="diary_form">
		       <div class="col-md-2" style="margin-top: 15px;">
		         <div class="boardImg" id="titleImgArea">
		           <img id="titleImg" width="150" height="150" src="<%=request.getContextPath()%>/resources/photo.png">
		         </div>
		       </div>
		       <div class="col-md-9">
		         <p><span id="counter">0</span>/150</p>
		         <textarea rows="5" style="resize:none; width: 100%;" placeholder="오늘의 육아일기를 작성해보세요." name="diaryContent" maxlength="150"></textarea>
		       </div>
		       <div class="col-md-1" id="diary_btn">
		         <button class="btn btn-primary" type="reset">초기화</button> <br><br>
		         <button class="btn btn-primary">등록</button>
		       </div>
		       <!-- 파일 업로드 하는 부분 -->
		       <div id="fileArea">
		         <!--  multiple 속성
		           - input 요소 하나에 둘 이상의 값을 입력할 수 있음을 명시 (파일 여러개 선택 가능)
		         -->
		         <input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
		       </div>
		
		       <script>
	           $("textarea").on("input", function() {
		           
		           var inputLength = $(this).val().length;
		     
		           $("#counter").text(inputLength);
		     
		           if(inputLength - 150 < 0) {
		             $("#counter").css("color", "black");
		           } else {
		             $("#counter").css("color", "red");
		           }
		         });
		
		         $(function () {
		           $("#fileArea").hide();
		
		           $("#titleImgArea").click(function () {
		             $("#img1").click();
		           });
		         });
		
		         function LoadImg(value, num) {
		           if (value.files && value.files[0]) {
		             var reader = new FileReader();
		             reader.onload = function (e) {
		               switch (num) {
		                 case 1:
		                   $("#titleImg").attr("src", e.target.result);
		                   break;
		               }
		             }
		
		             reader.readAsDataURL(value.files[0]);
		           }
		         }
		       </script>
		     </form>
		     </div>
		
		     <div class="row">
		     	<% if(dList.isEmpty()) { %>
		     		<div class="col-md-12">
		     		<h2>작성하신 육아 일기가 없습니다. <hr> 육아일기를 작성해보세요 ! :)</h2>
		     		</div>
		     	<% } else { %>
		     		<% for(int i=0; i<fList.size(); i++) { %>
		       <div class="col-md-4" id="diary_content">
		         <img src="<%=request.getContextPath()%>/resources/diaryImages/<%=fList.get(i).getFileChangeName()%>">
		         <br><br>
						
		         <p><%=dList.get(i).getDiaryContent() %></p>
		         <span><%=dList.get(i).getDiaryCreateDate() %></span> 
		         <a href="<%=request.getContextPath()%>/mypage/updateDiaryForm.do?diaryNo=<%=dList.get(i).getDiaryNo()%>&diaryContent=<%=dList.get(i).getDiaryContent()%>&fileName=<%=fList.get(i).getFileChangeName()%>">수정</a>
		         <a href="<%=request.getContextPath()%>/mypage/deleteDiary.do?diaryNo=<%=dList.get(i).getDiaryNo()%>">삭제</a>
		       </div>
		       <% } %>
					<% } %>
		     </div>
		     
		     <% if(!dList.isEmpty()) { %>
		     <!-- 페이지 번호 -->
         <div style="clear: both">
						<ul class="pagination">
							<%if(currentPage > pInfo.getLimit()) { %>
									<!-- 맨 처음 페이지로 이동 [<<] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/mypage/babyDiaryForm.do?cp=1">&lt;&lt;</a>
									</li>

									<!-- 이전 순번의 페이징바로 이동 [<] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/mypage/babyDiaryForm.do?cp=<%=prev%>">&lt;</a>
									</li>
							<%} %>
									
							<!-- 5개의 페이지 목록 -->
							<%for(int p=startPage; p<=endPage; p++) { %>
									<%if(p == currentPage) { %>
											<li><a class="page-link"><%=p%></a></li>
									<%} else { %>
											<li><a class="page-link" href="<%=request.getContextPath()%>/mypage/babyDiaryForm.do?cp=<%=p%>"><%=p%></a></li>
									<%} %>
							<%} %>
							
							<%if(next <= maxPage) { %>
									<!-- 다음 페이징바 [>] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/mypage/babyDiaryForm.do?cp=<%=next%>">&gt;</a>
									</li>
									
									<!-- 마지막 페이지로 이동 [>>] -->
									<li>
											<a class="page-link" href="<%=request.getContextPath()%>/mypage/babyDiaryForm.do?cp=<%=maxPage%>">&gt;&gt;</a>
									</li>
							<%} %>
						</ul>	        
	        </div>
	        <% } %>
			<!-- content-main 끝 -->
			</div>
			
		<!-- content 끝 -->
		</div>
		
		<!-- footer -->
		<%@ include file="../common/footer.jsp" %>
		
	<!-- container-fluid 끝 -->
	</div>
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>