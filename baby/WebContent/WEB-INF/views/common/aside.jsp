<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.member.model.vo.Member"%>
<%@page import="com.kh.baby.member.model.vo.General"%>
<%@page import="com.kh.baby.member.model.vo.Seller"%>

<%
   Member loginMember = (Member)session.getAttribute("loginMember");
    General loginGeneral = (General)session.getAttribute("loginGeneral");
    Seller loginSeller = (Seller)session.getAttribute("loginSeller");
      
    String babyGender = "";
    if(loginGeneral != null) {
       if(loginGeneral.getKidsHeight() != 0) {
         if(loginGeneral.getKidsGender().equals("f")) {
            babyGender = "여아";
         } else {
            babyGender = "남아";
         }
       }
    }
      
   boolean isSave = false;
   String saveId = "";

   Cookie[] cookies = request.getCookies();

   if (cookies != null) {
      for (Cookie c : cookies) {
         if ("saveId".equals(c.getName())) {
            saveId = c.getValue();
            isSave = true;
         }
      }
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
/* aside 스타일 */

body{
	cursor: default;
}
#aside {
   width: 20%;
   padding: 0 15px 0 15px;
}

#login{
   width: 100%
}

#login_btn {
   margin: 5px;
}

.form-signin {
   background-color: lightgray;
   width: 100%;
}

.form-signin>* {
   display: inline-block;
   margin: 10px;
}

#mypage {
   width: 100%;
   margin: 0;
}

#aside_menu {
       margin-top: 20px;
       margin-bottom: 20px;
       text-align: left;
}

/* 테이블 스타일 지정 */
#member_info{
   width: 100%;
   margin: 0;
}

#member_info * {
   border: 1px solid white;
}

#member_info th {
   width: 80px;
}
#member_info td {
   width: 250px;
}

#joinBtn, #findBtn{
	text-decoration: none;
	color: black;
	
}
#img {
	width: 170px;
}
</style>
</head>
<body>
   <div id="aside">
      <div id="aside_login">
         <%
            if (loginMember == null) {
         %>
         <form class="form-signin" method="post"
            action="<%=request.getContextPath()%>/member/login.do">
            <table>
               <tr>
                  <th colspan="2"><h1 class="h3 mb-3 font-weight-normal">로그인</h1></th>
               </tr>
               <tr>
                  <td><input type="text" name="inputId" id="inputId" class="form-control" placeholder="아이디" value="<%=saveId%>" required autofocus></td>
                  <td rowspan="2">
                     <button class="btn btn-lg btn-primary btn-block" type="submit" id="login_btn">LOGIN</button>
                  </td>
               </tr>
               <tr>
                  <td><input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="비밀번호" required></td>
               </tr>
               <tr>
                  <td><input type="checkbox" name="saveId" id="saveId" <%=isSave ? "checked" : ""%>><label for="saveId">아이디 저장하기</label></td>
               </tr>
            </table>

            <span>저희 홈페이지를<br>찾아주셔서 감사합니다. </span> <br> 
            <a href="<%=request.getContextPath()%>/member/signUpSelect.do" id="joinBtn">회원 가입</a> &nbsp;&nbsp;&nbsp;&nbsp; 
            <a href="<%=request.getContextPath()%>/member/searchSelectForm.do" id="findBtn">ID/PWD 찾기</a>
         </form>
         <%
            } else {
         %>
         <div class="form-signin" method="post">
            <h3><%=loginMember.getMemberNickname()%>님</h3>
            <a class="btn btn-primary" href="<%=request.getContextPath()%>/member/logout.do">Logout</a>
            <% if(loginMember.getMemberGrade().equals("G")) { %>
                  <table id="member_info">
                     <tr>
                        <th>주소</th>
                        <td>
                           <% if(!loginMember.getMemberAddress().equals(",,")) { %>
                              <%=loginMember.getMemberAddress()%>
                           <% } else { %>
                              <a class="btn btn-light btn-sm" href="<%=request.getContextPath()%>/mypage/updateAddressForm.do">주소 설정하기</a>
                           <% } %>
                        </td>
                     </tr>
      
                     <tr>
                        <th rowspan="4">아이 정보  </th>
                     <% if(loginGeneral.getKidsHeight() != 0) {%>
                        <td><%=loginGeneral.getKidsName()%>&nbsp;<%=babyGender%></td>
                     </tr>
                     <tr>
                        <td>생년월일 : <%=loginGeneral.getKidsBirth() %></td>
                     </tr>
                     <tr>
                        <td>키 : <%=loginGeneral.getKidsHeight() %>cm</td>
                     </tr>
                     <tr>
                        <td>몸무게 : <%=loginGeneral.getKidsWeight()%>kg</td>
                     <% } else { %>
                        <td><a class="btn btn-light btn-sm" href="<%=request.getContextPath()%>/mypage/insertBabyInfoForm.do">아이 정보 설정하기</a></td>
                     <% } %>
                     </tr>
                  </table>
                  <% } else if(loginMember.getMemberGrade().equals("S")) { %>
                  <table id="member_info">
                     <tr>
                        <th>주소</th>
                        <td>
                           <% if(!loginMember.getMemberAddress().equals(",,")) { %>
                              <%=loginMember.getMemberAddress()%>
                           <% } else { %>
                              <a class="btn btn-light btn-sm" href="<%=request.getContextPath()%>/mypage/updateAddressForm.do">주소 설정하기</a>
                           <% } %>
                        </td>
                     </tr>
      
                     <tr>
                        <th rowspan="2">회사 정보  </th>
                     <% if(loginSeller != null) {%>
                        <td>사업자등록번호 : <%=loginSeller.getCompanyNo() %></td>
                     </tr>
                     <tr>
                        <td>회사 전화번호 : <%=loginSeller.getCompanyPhone() %></td>
                     <% } else { %>
                        <td><a class="btn btn-light btn-sm" href="<%=request.getContextPath()%>">회사 정보 설정하기</a></td>
                     <% } %>
                     </tr>
                  </table>
                  <% } else {%>
                     <img id="img" src="<%=request.getContextPath()%>/resources/user.png">
                  <% } %>
         </div>
         <%if(loginMember.getMemberGrade().equals("G")) {%>
         <button type="button" class="btn btn-secondary btn-lg" id="mypage" onclick="location.href='<%=request.getContextPath()%>/mypage/myBoardList.do?cp=1';">MY PAGE</button>
         <%} else if(loginMember.getMemberGrade().equals("S")) { %>
         <button type="button" class="btn btn-secondary btn-lg" id="mypage" onclick="location.href='<%=request.getContextPath()%>/mypage/myBoardList.do?cp=1';">MY PAGE</button>
         <%} else if(loginMember.getMemberGrade().equals("A")) {%>
         <button type="button" class="btn btn-secondary btn-lg" id="mypage" onclick="location.href='<%=request.getContextPath()%>/adminPage/noticeBoard.do';">MY PAGE</button>
         <%
            } }
         %>
      </div>
         
         <div id="aside_menu">
            <% if(loginMember != null){ %>
            <%if(loginMember.getMemberGrade().equals("A")) {%>
            <%@ include file="/WEB-INF/views/adminPage/adminAside.jsp" %>
            <%} else { %>
            <%@ include file="/WEB-INF/views/mypage/mypage_aside.jsp" %>
            <%   } %>
            <% } %>
      </div>
   </div>
</body>
</html>