<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.member.model.vo.Member"%>
<%@page import="com.kh.baby.member.model.vo.General"%>
<% 
Member tempMember = (Member)session.getAttribute("loginMember");
General tempGeneral = (General)session.getAttribute("loginGeneral");

String kidsBirth = "";
String year = "";
String month = "";

String day = "";

if(tempGeneral != null) {
	kidsBirth = tempGeneral.getKidsBirth() != null ? tempGeneral.getKidsBirth() : "";
	if(!kidsBirth.equals("")) {
		year = kidsBirth.substring(0, 4);
		month = kidsBirth.substring(5, 7);
		day = kidsBirth.substring(8, 10);
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>슬기로운 육아생활</title>
<style>
		/* content_main 스타일 */
    #content_main {
      width: 80%;
    }
    .content {
      width: 48%;
      float: left;
      margin-left: 20px;
    }
    .content > div {
      text-align: left;
      padding: 20px;
      margin-bottom: 20px;
      background-color: rgb(251, 251, 171);

      border-radius:2em;
      -moz-border-radius: 2em;
      -webkit-border-radius: 2em;
    }
    .content-title {
      font-size: 20px;
      color: black;
    }
    .best {
      font-size: 25px;
      font-weight: bold;
      background-color: lightcoral;
      /* clear: both; */
      
      border-radius: 10rem;
      
    }
    #content-addr, .content-write {
      float: right;
    }
    .content-best {
      font-size: 25px;
    }
    
    input[type="number"]::-webkit-outer-spin-button,
       input[type="number"]::-webkit-inner-spin-button {
           -webkit-appearance: none;
           margin: 0;
       }
       
     .birth{
     	width: 100px; 
     }
     
     #inputAge{
     	width: 50px;
     }
    
</style>
</head>
<body>
	<div id="content_main">
      <div class="content">
        <h1>INFORMATION</h1>
        <div>
          <a class="content-title" href="<%=request.getContextPath()%>/board/hospitalForm.do">지역별 병원 정보</a>
          <span id="content-addr">지역 : 전국</span> <br>
          <span class="best">&nbsp;BEST&nbsp;</span>
          <ol class="content-best" id="hos-best">
            <li></li>
            <li></li>
            <li></li>
          </ol>
        </div>
        <div style="height: 236px;">
          <a class="content-title" href="<%=request.getContextPath()%>/board/knowledgeForm.do?type=2&cp=1&age=0">아이 연령별 상식</a>
          <div>
          	<p style="font-weight: bold;">"생년월일을 입력해보세요!"</p>
          	<form>
          		<div style="margin-bottom: 10px;" id="calForm">
	          		<input class="birth" type="number" value="<%=year %>"> 년 &nbsp; 
		          	<input class="birth" type="number" value="<%=month %>">월 &nbsp;
		          	<input class="birth" type="number" value="<%=day %>"> 일 <br>
          		</div>
          		
          		<div style="margin-bottom: 10px;">
          			<button type="button" class="btn btn-secondary btn-sm" id="calAge">만 나이 계산하기</button> &nbsp;
					<svg width="2em" height="2em" viewBox="0 0 16 16" class="bi bi-arrow-right-circle" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
					  <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
					  <path fill-rule="evenodd" d="M7.646 11.354a.5.5 0 0 1 0-.708L10.293 8 7.646 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0z"/>
					  <path fill-rule="evenodd" d="M4.5 8a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5z"/>
					</svg>
	          		&nbsp;만&nbsp;<input type=text" id="inputAge">세 
          		</div>
	          		<button type="button"  class="btn btn-secondary btn-sm" id="submitBtn">아이 연령대 상식게시판으로 이동</button>


          	</form>
          </div>
        </div>
      </div>
      <div class="content">
        <h1>COMMUNITY</h1>
        <div>
          <a class="content-title" href="<%=request.getContextPath()%>/community/freeList.do">자유 게시판</a>
          <%if(tempMember != null) { %>
          <button type="button" class="btn btn-secondary content-write" onclick="location.href ='community/freeInsertForm.do?type=3'">글쓰기</button> 
          <%} %>
          <br><span class="best">&nbsp;BEST&nbsp;</span>
          <ol class="content-best" id="free-best">
            <li></li>
            <li></li>
            <li></li>
          </ol>
        </div>
        <div>
          <a class="content-title" href="<%=request.getContextPath()%>/community/promoList.do">홍보 게시판</a>
          <%if(tempMember != null && tempMember.getMemberGrade().equals("S")) { %>
          <button type="button" class="btn btn-secondary content-write" onclick="location.href ='community/promoInsertForm.do?type=4'">글쓰기</button> 
          <%} %>
          <br><span class="best">&nbsp;BEST&nbsp;</span>
          <ol class="content-best" id="sell-best">
            <li></li>
            <li></li>
            <li></li>
          </ol>
        </div>
      </div>
    </div>
    
    
    <script>
    	$(function() {
    		hos_best_list(1);
    		free_best_list(3);
    		sell_best_list(4);
    	});
    	
    	function hos_best_list(boardType) {
    		$.ajax({
    			url : "board/best_list.do",
    			data : {"type" : boardType},
    			dataType : "json",
    			success : function(list) {
	    			$("#hos-best").children().eq(0).html("<a href='<%=request.getContextPath()%>/board/hospitalView.do?type=1&cp=1&no="+list[0].boardNo+"'>"+list[0].boardTitle+"</a>");
	    			$("#hos-best").children().eq(1).html("<a href='<%=request.getContextPath()%>/board/hospitalView.do?type=1&cp=1&no="+list[1].boardNo+"'>"+list[1].boardTitle+"</a>");
	    			$("#hos-best").children().eq(2).html("<a href='<%=request.getContextPath()%>/board/hospitalView.do?type=1&cp=1&no="+list[2].boardNo+"'>"+list[2].boardTitle+"</a>");
    			},
    			error : function() {
    				console.log("ajax 통신 실패");
    			}
    		});
    	}
    	
    	function free_best_list(boardType) {
    		$.ajax({
    			url : "board/best_list.do",
    			data : {"type" : boardType},
    			dataType : "json",
    			success : function(list) {
	    			$("#free-best").children().eq(0).html("<a href='<%=request.getContextPath()%>/community/freeView.do?type=3&cp=1&no="+list[0].boardNo+"'>"+list[0].boardTitle+"</a>");
	    			$("#free-best").children().eq(1).html("<a href='<%=request.getContextPath()%>/community/freeView.do?type=3&cp=1&no="+list[1].boardNo+"'>"+list[1].boardTitle+"</a>");
	    			$("#free-best").children().eq(2).html("<a href='<%=request.getContextPath()%>/community/freeView.do?type=3&cp=1&no="+list[2].boardNo+"'>"+list[2].boardTitle+"</a>");
    			},
    			error : function() {
    				console.log("ajax 통신 실패");
    			}
    		});
    	}
    	
    	function sell_best_list(boardType) {
    		$.ajax({
    			url : "board/best_list.do",
    			data : {"type" : boardType},
    			dataType : "json",
    			success : function(list) {
	    			$("#sell-best").children().eq(0).html("<a href='<%=request.getContextPath()%>/community/promoView.do?type=4&cp=1&no="+list[0].boardNo+"'>"+list[0].boardTitle+"</a>");
	    			$("#sell-best").children().eq(1).html("<a href='<%=request.getContextPath()%>/community/promoView.do?type=4&cp=1&no="+list[1].boardNo+"'>"+list[1].boardTitle+"</a>");
	    			$("#sell-best").children().eq(2).html("<a href='<%=request.getContextPath()%>/community/promoView.do?type=4&cp=1&no="+list[2].boardNo+"'>"+list[2].boardTitle+"</a>");
    			},
    			error : function() {
    				console.log("ajax 통신 실패");
    			}
    		});
    	}
    	
    	var age=-1;
    	$("#calAge").on("click", function() {
    		var year = $("#calForm").children().eq(0).val();
    		var month = $("#calForm").children().eq(1).val();
    		var day = $("#calForm").children().eq(2).val();
    		
    		if(year == "" || month == "" || day == "") {
                swal("생년월일을 입력하세요");
                $("#calForm").focus();
             } else {
             $.ajax({
                url : "member/calAge.do",
                data : {"year" : year, "month" : month, "day" : day},
                success : function(result) {
                   $("#inputAge").val(result);
                   age = result;
                },
                error : function() {
                   console.log("ajax 통신 실패");
                }
             });
             }
    	});
    	
    	$("#submitBtn").on("click", function() {
    		if(age == 0 || age == 1 || age == 2 || age == 3) {
    			age = 3;
    		} else if(age == -1) {
    			age = 0;
    		} else if(age > 7){
    			age = 7;
    		}
    		
    		location.href ="board/knowledgeForm.do?type=2&cp=1&age=" + age;
    	});
    </script>
</body>
</html>