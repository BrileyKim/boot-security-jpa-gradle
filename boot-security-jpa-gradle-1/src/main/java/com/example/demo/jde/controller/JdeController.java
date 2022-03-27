package com.example.demo.jde.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.jde.dto.XmlDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jde")
@RequiredArgsConstructor
public class JdeController {
	
	private final ParsingXml parsingXml;

	@GetMapping()
	public ModelAndView SelectList(ModelAndView mav,@RequestParam(defaultValue = "1")String pageNo) {
		
		List<XmlDTO> list=parsingXml.test(pageNo);

		
		for(XmlDTO o:list) {
			System.out.println(o);
		}
		
		mav.addObject("list", list);
		mav.setViewName("/jde/list");
		
		return mav;
		
	}
	@GetMapping("map")
	public ModelAndView map(ModelAndView mav,@RequestParam(defaultValue = "1")String pageNo) {
		
		List<XmlDTO> list=parsingXml.test(pageNo);

		
		for(XmlDTO o:list) {
			System.out.println(o);
		}
		
		mav.addObject("list", list);
		mav.setViewName("/jde/map");
		
		return mav;
		
	}
}
