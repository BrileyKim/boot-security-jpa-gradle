package com.example.demo.jde.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class XmlDTO {
	
	private String addr;//주소
	private String yadmNm;//요양병원명
	private String recuClCd;//요양종별코드
	private String telno; //전화번호
	private String XPosWgs84; //x좌표
	private String YPosWgs84; //y좌표
	

}
