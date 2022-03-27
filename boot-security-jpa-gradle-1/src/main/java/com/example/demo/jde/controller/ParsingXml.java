package com.example.demo.jde.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.jde.dto.XmlDTO;

@Configuration
public class ParsingXml {

  	 // tag���� ������ �������� �Լ�
	public static String getTagValue(String tag, Element eElement) {

           	//����� ������ result ���� ����
           	String result = "";

	    	NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

	    	result = nlList.item(0).getTextContent();

	    	return result;
	}

	// tag���� ������ �������� �Լ�
	public static String getTagValue(String tag, String childTag, Element eElement) {

		//����� ������ result ���� ����
		String result = "";

		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

		for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {

			//result += nlList.item(i).getFirstChild().getTextContent() + " ";
			result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
		}

		return result;
	}

	public static  List<XmlDTO> test(String pageNo) {

		// ������ ���� apiŰ�� �߰�
		String key = "";
		List<XmlDTO> list=new ArrayList<>();
		try{
			// parsing�� url ����(API Ű �����ؼ�)
			String url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=oSoBJGtc68jclWngoupQOM1BqBUWYcscCdXIm1HnnwL9ZChOBrpKi7sbouYUGSKv8bRZ543fCtQI0hVqsgI8Kw%3D%3D&pageNo="+pageNo+"&numOfRows=50";
	        String numOfRows="";
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			// ���� ù��° �±�
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result
			
			// �Ľ��� tag
			NodeList nList = doc.getElementsByTagName("item");
			
			for(int temp = 0; temp < nList.getLength(); temp++){
				XmlDTO xml=new XmlDTO();
				Node nNode = nList.item(temp);
			
				Element eElement = (Element) nNode;
				
				xml.setAddr(getTagValue("addr", eElement));
				xml.setYadmNm(getTagValue("yadmNm", eElement));
				xml.setRecuClCd(getTagValue("recuClCd", eElement));
				xml.setTelno(getTagValue("telno", eElement));
				xml.setXPosWgs84(getTagValue("XPosWgs84", eElement));
				xml.setYPosWgs84(getTagValue("YPosWgs84", eElement));
				/*
				 * System.out.println("addr : " + getTagValue("addr", eElement));
				 * System.out.println("yadmNm : " + getTagValue("yadmNm", eElement));
				 * System.out.println("recuClCd : " + getTagValue("recuClCd", eElement));
				 * System.out.println("telno : " + getTagValue("telno", eElement));
				 * System.out.println("XPosWgs84 : " + getTagValue("XPosWgs84", eElement));
				 * System.out.println("YPosWgs84" + getTagValue("YPosWgs84", eElement));
				 */	
				list.add(xml);
			}
			
		} catch (Exception e){	
			e.printStackTrace();
		}
		
		return list;
	}
	
	
}
