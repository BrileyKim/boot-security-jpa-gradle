package com.example.demo.kgn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FirePlug {
	
	private String num;				//번호
	private String jibunNm;			//주소
	private String baseDt;			//기준일자
	private String availability;	//사용여부
	private String clsfldDt;		//폐전일자
	private String facltNo;			//시설번호
	private String refineLat;		//위도
	private String refineLogt;		//경도
	private String latlng;
}
