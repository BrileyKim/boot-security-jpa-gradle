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
	
	@GetMapping()
	public ModelAndView SelectList(ModelAndView mav) throws UnsupportedEncodingException{
		
		
		try {
			
			
	        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6440000/firePlug/getFrplgCnJSONDataForAPI"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=bYhQpkws1NzgWD6fE5nIDq%2B%2FlsRs4i9gnV0Y6VGGqP8D7uqWuiN3JDXrO7%2F%2Byx%2Bciv8hJYfL%2FcE%2BsbIiIypjXQ%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*검색어 구분(1=주소, 2=시설번호, 3=시군명)*/
	        urlBuilder.append("&" + URLEncoder.encode("searchKeyword","UTF-8") + "=" + URLEncoder.encode("홍성군", "UTF-8")); /*검색키워드*/
	        urlBuilder.append("&" + URLEncoder.encode("searchSigun","UTF-8") + "=" + URLEncoder.encode("44800", "UTF-8")); /*행정표준코드*/
	        urlBuilder.append("&" + URLEncoder.encode("searchFacltCd","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*소화전구분(01 = 지상, 02 = 지하, 03 = 급수탑, 04 = 저수조)*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        
		
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
