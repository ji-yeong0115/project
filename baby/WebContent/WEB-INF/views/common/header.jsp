<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.member.model.vo.Member"%>
<%
	Member loginMemberH = (Member)session.getAttribute("loginMember");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

	<style>
	    /* 전체 사이즈 지정 */
        .container-fluid {
            width: 1800px;
            /* height: 1000px; */
        }
        div {
            /* border: 1px solid black; */
            text-align: center;
        }
        /* header 스타일 */
        #header {
        	height: 160px;
        }
        #header img {
        	height: 100%;
        }
        /* navi 스타일 */
        .nav {
            width: 100%;
        }
        .nav-item { /* ul > li*/
            width: 20%;
            background-color: rgb(186, 234, 253);
        }
        .nav li>ul {
        position: absolute;
        width: 100%;
		z-index: 1;
        background-color: rgb(217, 244, 255);

        list-style-type: none;

        margin: 0;
        padding: 0;

        opacity: 0;
        }
        .nav ul>li>a {
            opacity: 0;
        }
        .nav ul>li {
            height: 0;
            line-height: 0;
        }
        .nav>li:hover ul, .nav>li:hover ul a {
            opacity: 1;
        }
        .nav li:hover ul li {
            height: 50px;
            line-height: 50px;
        }
        .nav li ul {
            transition-duration: 0.4s;
        }
   
	</style>
	<!-- sweetalert : alert창을 꾸밀 수 있게 해주는 라이브러리 https://sweetalert.js.org/ -->
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
		<script>
		/**** 이전 페이지에서 전달받은 내용을 alert 창으로 표시하기 ****/
		<% 
			String msg = (String)(request.getSession().getAttribute("msg"));
			String status = (String)(request.getSession().getAttribute("status"));
			String text = (String)(request.getSession().getAttribute("text"));
		%>
		<% if(msg != null) { %>
			swal({
				icon : "<%=status%>",
				title : "<%=msg%>",
				text : "<%=text != null ? text : ""%>"
			});
		<% // Session에 존재하는 특정 키 값의 속성 제거
			session.removeAttribute("msg"); // session = request.getSession()
			session.removeAttribute("status");
			session.removeAttribute("text");
		} %>
		</script>
</head>

<body>

        <div class="row">
            <div class="col-md-12" id="header">
              <a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/resources/아기.PNG"></a>
              <a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/resources/로고.png"></a>
              <a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/resources/아기2.PNG"></a>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <ul class="nav nav-pills nav-justified">
                  <li class="nav-item">
                    <a class="nav-link active" href="<%=request.getContextPath()%>">MAIN</a>
                  </li>
                  
                  <li class="nav-item">
                    <a class="nav-link" href="#">ABOUT</a>
                  </li>
                  
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">INFORMATION</a>
                      <ul>
                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/board/hospitalForm.do?type=1&cp=1">지역별 병원 정보</a></li>
                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=0">아이 연령대별 상식</a></li>
                      </ul>
                  </li>
                  
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">COMMUNITY</a>
                      <ul>
                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/community/freeList.do?type=3">자유게시판</a></li>
                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/community/promoList.do?type=4">홍보게시판</a></li>
                      </ul>
                  </li>
                  
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="<%=request.getContextPath()%>/mypage/babyInfoForm.do" role="button" aria-haspopup="true" aria-expanded="false">MY PAGE</a>
                    <ul>
                    	<% 
                    		String url = request.getContextPath() + "/mypage/babyInfoForm.do?memberNo=";
                    		if(loginMemberH != null) url += loginMemberH.getMemberNo();
                    	%>
                    	<%if(loginMemberH != null) {%>
                    		<%if(loginMemberH.getMemberGrade().equals("G")) { %>
	                        <li><a class="dropdown-item text-primary" href="<%=url%>">우리 아이 정보</a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/mypage/babyDiaryForm.do">육아 일기</a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=1">작성한 글</a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/member/updateMemberForm.do">개인 정보 관리</a></li>
                        <% } else if(loginMemberH.getMemberGrade().equals("S")) { %>
	                       	<li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/member/myCompanyInfoForm.do">우리 회사 정보</a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=1">작성한 글</a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/member/updateSellerForm.do">개인 정보 관리</a></li>
                        <% } else if(loginMemberH.getMemberGrade().equals("A")) { %>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/adminPage/reportBoard.do">신고게시판 </a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/adminPage/requestBoard.do">요청게시판</a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/adminPage/noticeBoard.do">공지사항</a></li>
	                        <li><a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/adminPage/memberView?type=0.do">회원 정보 조회</a></li>
	                        <li><a class="dropdown-item text-primary dropdown-toggle dropright" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">글쓰기</a>
		                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
						          <a class="dropdown-item" href="<%=request.getContextPath()%>/adminPage/adminWrite.do?type=0">공지사항</a>
						          <a class="dropdown-item" href="<%=request.getContextPath()%>/adminPage/adminWrite.do?type=1">병원게시판</a>
						          <a class="dropdown-item" href="<%=request.getContextPath()%>/adminPage/adminWrite.do?type=2">상식게시판</a>
						        </div>	
						    </li>
                        <% } }else {%>
                        <% } %>
                    </ul>
                  </li>
                </ul>
            </div>
        </div>

	<script>
<%-- 	$(function(){
		$("#list-table td").click(function(){
			var noticeNo = $(this).parent().children().eq(0).text();
			
			// 쿼리스트링을 이용하여 get방식으로 글번호를 전달
			location.href = "<%=request.getContextPath()%>/notice/view.do?no=" + noticeNo;
		}).mouseenter(function(){
			$(this).parent().css("cursor", "pointer");
		});
	}); --%>
	
	$("a[href='#']").on("click", function(e){
		e.preventeDefault(); 
		// href 속성값이 #인 a태그를 클릭한 경우 기본 이벤트를 제거하는 자바스크립트 코드 입니다.
		// a태그 기본이벤트 : href 속성에 작성된 주소로 요청보내기
	});
	</script>
	
	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>

</html>