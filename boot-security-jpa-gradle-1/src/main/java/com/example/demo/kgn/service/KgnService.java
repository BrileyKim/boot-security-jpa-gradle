package com.example.demo.kgn.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.example.demo.kgn.dto.FirePlug;

import lombok.RequiredArgsConstructor;


@Transactional
@Service
@RequiredArgsConstructor
public class KgnService {
	
	
	/**
	 * url을 통해 공공데이터 API에 접근하여 문자열 데이터를 얻은 후 파싱한다.
	 * @author 김가남
	 * @history 2022.03.21
	 * */
	public List<FirePlug> SelectList(){
		
		List<FirePlug> resultList = new ArrayList<FirePlug>();
		
		try {
			
			StringBuilder urlBuilder = SetUrl();
			HttpURLConnection conn = GetConnection(urlBuilder);
			String rawData = CreateRawData(conn);
			resultList = BeginJSONParsing(rawData);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	/**
	 * 공공 데이터 API에 접근할 url을 setting한다
	 * @author 김가남
	 * @history 2022.03.21
	 * */
	public StringBuilder SetUrl() {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6440000/firePlug/getFrplgCnJSONDataForAPI");;
		
        try {
        	
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=bYhQpkws1NzgWD6fE5nIDq%2B%2FlsRs4i9gnV0Y6VGGqP8D7uqWuiN3JDXrO7%2F%2Byx%2Bciv8hJYfL%2FcE%2BsbIiIypjXQ%3D%3D");/*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*검색어 구분(1=주소, 2=시설번호, 3=시군명)*/
			urlBuilder.append("&" + URLEncoder.encode("searchKeyword","UTF-8") + "=" + URLEncoder.encode("홍성군", "UTF-8")); /*검색키워드*/
			urlBuilder.append("&" + URLEncoder.encode("searchSigun","UTF-8") + "=" + URLEncoder.encode("44800", "UTF-8")); /*행정표준코드*/
			urlBuilder.append("&" + URLEncoder.encode("searchFacltCd","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*소화전구분(01 = 지상, 02 = 지하, 03 = 급수탑, 04 = 저수조)*/
        
        
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        return urlBuilder;
		
	}
	
	/**
	 * 설정한 url을 통해 공공데이터에 접근한다.
	 * @author 김가남
	 * @history 2022.03.21
	 * */
	public HttpURLConnection GetConnection(StringBuilder urlBuilder) {
		
		HttpURLConnection conn = null;
		
		try {
			
			URL url = new URL(urlBuilder.toString());
		
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 공공데이터 API로 부터 rawData를 문자열 형태로 받는다. 
	 * @author 김가남
	 * @history 2022.03.21
	 * */
	public String CreateRawData(HttpURLConnection conn) {
		
		BufferedReader rd;
		String line;
		StringBuilder sb = new StringBuilder();
		
		try {
			
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
			    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	/**
	 * 문자열 형태로 수집한 rawData를 파싱한다.
	 * @author 김가남
	 * @history 2022.03.21
	 * */
	public List<FirePlug> BeginJSONParsing(String rawData) {
		
		List<FirePlug> resultList = new ArrayList<FirePlug>();
		
		try {
		
		JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(rawData);
        
        JSONArray parse_listArr = (JSONArray)obj.get("list");
        
        for(int i=0 ; i < 10; i++) {
        	
        	FirePlug fp = new FirePlug();
        	
        	JSONObject firePlug = (JSONObject) parse_listArr.get(i);
        	
        	fp.setNum(String.valueOf(i+1));
        	fp.setJibunNm(String.valueOf(firePlug.get("jibunNm")));
        	fp.setBaseDt(String.valueOf(firePlug.get("baseDt")));
        	fp.setFacltNo(String.valueOf(firePlug.get("facltNo")));
        	fp.setRefineLat(String.valueOf(firePlug.get("refineLat")));
        	fp.setRefineLogt(String.valueOf(firePlug.get("refineLogt")));
        	fp.setLatlng("new kakao.map.LatLng("+String.valueOf(firePlug.get("refineLat"))+", "+String.valueOf(firePlug.get("refineLogt"))+")");
        	
        	String availability = String.valueOf(firePlug.get("availability"));
        	
        	if("Y".equals(availability)) {
        		fp.setAvailability("O"); 
        	}else {
        		fp.setAvailability("X");
        	}
        	
        	String clsfldDt = String.valueOf(firePlug.get("clsfldDt"));
        	
        	if("null".equals(clsfldDt)) {
        		fp.setClsfldDt("");
        	}else {
        		fp.setClsfldDt(clsfldDt);
        	}

        	resultList.add(fp);
        }

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
		
	}
	

}
