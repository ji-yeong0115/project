package com.kh.baby.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File originFile) {
		// 원본 파일명 - > 20200723102231_12345.png 
		long currentTime = System.currentTimeMillis();
		// 1970년 1월 1일 오전 9시(대한민국 기준) 기준으로
		// 현재 시간 까지의 ms를 반환
		
		int ranNum = (int)(Math.random()*100000);
		
		String str = "_" + String.format("%05d", ranNum);
		// String.format() : 문자열을 지정된 패턴 형식으로 변경하는 메소드
		// 								Printf에 쓰는 것처럼 쓰면 됨! d-십진수 정렬, 0-빈칸에0채워라
		// %05d : 오른쪽 정렬된 십진 정수(d) 5자리(5)형태로 변경. 빈자리는 0으로 채움(0)
		
		// 파일명 변경해도 확장자를 유지하기
		String name = originFile.getName(); //워본 파일 명
		String ext = null; // 확장자 저장할 String 변수
		
		int dot = name.lastIndexOf("."); //제일 뒤에서부터 시작해서 .을 찾겠다 -> .이 있는 인덱스를 반환함
		
		// 파일명에 .이 없을 경우 -1 반환됨
		if ( dot != -1 ) {
			ext = name.substring(dot); // "."포함 이후 문자열을 반환함
		} else {
			ext = "";
			
		}
  		
		SimpleDateFormat sft = new SimpleDateFormat("yyyyMMddHHmmss");
		 String fileName = sft.format(new Date(currentTime)) + str + ext;
		 
		 File newFile = new File(originFile.getParent(), fileName);
		 					// 기존파일의 이름을(originFile) 이렇게(fileName)변경하겠다
		 					//그냥 originFile만 넣으면 안됨
		 					//originFile.getParent() : 기본 파일이 저장된 폴더 경로 반환
		return newFile;
		
	}
	

	
	
}
