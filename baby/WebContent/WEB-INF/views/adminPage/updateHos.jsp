<%@page import="com.kh.baby.adminPage.model.vo.Hospital"%>
<%@page import="java.util.Arrays"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	Hospital board = (Hospital)request.getAttribute("hospital");
	int requestNo = (int)(request.getAttribute("requestNo"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역별 병원 정보 게시글 수정</title>
	<style>
	
			@font-face { font-family: 'InfinitySans-BoldA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-BoldA1.woff') 
	format('woff'); font-weight: normal; font-style: normal; }
	
		          /* content_main 스타일 */
          #content_main {
            width: 80%;
            padding-right: 15px;
            margin-top: 48px;
          }

          /* padding 제거 css */
          .padding-none{
            padding-left: 0;
            padding-right: 0;
          }

          /* margin 제거 css */
          .margin-none{
            margin-left: 0;
            margin-right: 0;
          }

          /* 목록, 다음글, 이전글, 정보 수정/삭제 요청, 신고 버튼 오른쪽 정렬 */
          .btn-right{
            float: right;
          }

          /* margin-top 20px */
          .top-margin{
            margin-top: 20px;
          }

          .top-btn-area{
            margin-left: 10px;
          }

          #title{
            font-family: 'InfinitySans-BoldA1';
          }
          
          .text-loca, .like-area, , .content-area{
            text-align: left;
          }

          .solid{
            border: 1px solid black;
          }

          .board-area{
            margin-bottom: 20px;
          }

          #heart-img{
            width: 45px;
            height: 50px;
          }
	</style>

</head>
<body>

	<%
		String [] ynRadio = new String[4];
		Arrays.fill(ynRadio, "");
		
		if(board.getHosNight().equals("Y")){
			ynRadio[0] = "checked";
		}else{
			ynRadio[1] = "checked";
		}
		
		if(board.getHosWeekend().equals("Y")){
			ynRadio[2] = "checked";
		}else{
			ynRadio[3] = "checked";
		}
	
	
	%>

	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %>
		
		<div class="row" id="content">
			<%@ include file="../common/aside.jsp" %>
			
				<div id="content_main"  style="padding-left: 15px">
				
				<form action="requestUpdate.do?type1&no=<%=board.getBoardNo() %>&requestNo=<%=requestNo%>" method="POST">
					<!-- 게시글 제목 -->
                  <div class="row margin-none">
                    <div class="col-md-12 padding-none">
                    	<h3>[게시글 수정 화면]</h3> <br>
						<table>
							<tr>
								<th class="h3" style="line-height:10px;">제목 : &nbsp;</th>
								<td style="width: 94%;"><input type="text" name="titleInfo" class="form-control" value="<%=board.getBoardTitle()%>"></td>
							</tr>
						</table>

                     </div>
                  </div>
          

                  <hr>
          
                  <!-- 게시글이 작성된 공간 -->
                  <div class="row board-area" style="width: 100%; margin: 0px;">
	                  <div class="col-md-4 top-margin board-area margin-none" style="margin: auto;">
	                  	<table>
	                  		<tr>
	                  			<th>영업시간 : </th>
	                  			<td>
	                  				<input type="text" name="hosTime" class="form-control" value="<%=board.getHosTime() %>">
	                  			</td>
	                  		</tr>
	                  		<tr>
	                  			<th>병원주소 : </th>
	                  			<td>
	                  				<input type="text" name="hosAddress" class="form-control" value="<%=board.getHosAddr()%>">
	                  			</td>
	                  		</tr>
	                  		<tr>
	                  			<th>야간진료 여부 : </th>
	                  			<td>
	                  				<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="nitghYN" id="nightY" value="Y" <%=ynRadio[0]%>>
									  <label class="form-check-label" for="nightY">야간진료 가능</label>
									</div>
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="nitghYN" id="nightN" value="N" <%=ynRadio[1]%>>
									  <label class="form-check-label" for="nightN">야간진료 불가능</label>
									</div>
								</td>
	                  		</tr>
	                  		<tr>
	  	                  		<th>주말진료 여부 : &nbsp;</th>
	                  			<td>

								<div class="form-check form-check-inline">
								  <input class="form-check-input" type="radio" name="weekYN" id="weekendY" value="Y" <%=ynRadio[2]%>>
								  <label class="form-check-label" for="weekendY">주말진료 가능</label>
								</div>
								<div class="form-check form-check-inline">
								  <input class="form-check-input" type="radio" name="weekYN" id="weekendN" value="N" <%=ynRadio[3]%>>
								  <label class="form-check-label" for="weekendN">주말진료 불가능</label>
								</div>									
	                  			</td>
	                  		</tr>
	                  	</table>
	                    
	                  </div>
                  </div>
          
                  <!-- 좋아요 이미지와 버튼 -->
                  <div class="row margin-none" style="margin-top: 20px;">
                    <div div class="col-md-12">
                      <button class="btn btn-primary btn-right" type="submit">수정</button>
                    </div>            
                  </div>
				</form>
				

                  <hr>     
                </div>
		</div>
	
		<%@ include file="../common/footer.jsp" %>
	</div>
</body>
</html>