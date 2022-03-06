package com.example.demo.kgn.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.kgn.service.KgnService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/kgn")
@RequiredArgsConstructor
public class KgnController {
	
	private final KgnService kgnService;
	
	private String encodeValue(String value) {
		
		String returnValue = "";
		
		try {
			returnValue = URLEncoder.encode(value,
					StandardCharsets.UTF_8.toString());
			
		}catch(UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		return returnValue;
		
	}
	
	@GetMapping()
	public ModelAndView SelectList(ModelAndView mav) throws UnsupportedEncodingException{
		
		
		try {
			
			Map<String,String> requestParams = new LinkedHashMap<String,String>();
			requestParams.put("serviceKey","bYhQpkws1NzgWD6fE5nIDq%2B%2FlsRs4i9gnV0Y6VGGqP8D7uqWuiN3JDXrO7%2F%2Byx%2Bciv8hJYfL%2FcE%2BsbIiIypjXQ%3D%3D");
			requestParams.put("pageNo","1");
			requestParams.put("numOfRows","10");
			
		
			String encodedURL = requestParams.keySet().stream()
				      .map(key -> key + "=" + encodeValue(requestParams.get(key)))
				      .collect(Collectors.joining("&", "http://apis.data.go.kr/B551182/rprtHospService?", ""));
			
			
			//1. URL을 만들기 위한 StringBuilder
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/rprtHospService");
			
			//2. 오픈 API 요청 규격에 맞는 파라미터 생성, 발급받은 인증키
			urlBuilder.append("?"+URLEncoder.encode("serviceKey","UTF-8")+"=bYhQpkws1NzgWD6fE5nIDq%2B%2FlsRs4i9gnV0Y6VGGqP8D7uqWuiN3JDXrO7%2F%2Byx%2Bciv8hJYfL%2FcE%2BsbIiIypjXQ%3D%3D");
			urlBuilder.append("&"+URLEncoder.encode("pageNo","UTF-8")+"="+URLEncoder.encode("1","UTF-8"));
			urlBuilder.append("&"+URLEncoder.encode("numOfRows","UTF-8")+"="+URLEncoder.encode("10","UTF-8"));
			
			System.out.println("1 : "+urlBuilder.toString());
			System.out.println("2 : "+StringEscapeUtils.escapeJava(urlBuilder.toString()));
			System.out.println("3 : "+encodedURL);
			
			//3.URL 객체 생성
			URL url = new URL(encodedURL);
			
			//4.요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//5.통신을 위한 메소드 SET
			conn.setRequestMethod("GET");
			
			//6.통신을 위한 Content-type SET
			conn.setRequestProperty("Content-type", "application/json");
			
			//7.통신 응답 코드 확인 
			System.out.println("Response code: "+conn.getResponseCode());
			
			// 8. 전달받은 데이터를 BufferedReader 객체로 저장.
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        // 10. 객체 해제.
	        rd.close();
	        conn.disconnect();
	        // 11. 전달받은 데이터 확인.
	        System.out.println(sb.toString());
			
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		mav.setViewName("/kgn/list");
		
		return mav;
		
	}
	
}
