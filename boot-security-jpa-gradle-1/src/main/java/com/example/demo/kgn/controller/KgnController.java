package com.example.demo.kgn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.kgn.dto.FirePlug;
import com.example.demo.kgn.service.KgnService;

import lombok.RequiredArgsConstructor;
import com.google.gson.Gson;

@Controller
@RequestMapping("/kgn")
@RequiredArgsConstructor
public class KgnController {
	
	private final KgnService kgnService;
	
	@GetMapping()
	public ModelAndView SelectList(ModelAndView mav){
		
		List<FirePlug> resultList = new ArrayList<FirePlug>();
		
		try {
			resultList = kgnService.SelectList();
  	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("resultList", resultList);
		mav.setViewName("/kgn/list");
		
		return mav;
		
	}
	
	@GetMapping("/map")
	public ModelAndView OpenKakaoMap(ModelAndView mav) {
		
		List<FirePlug> resultList = new ArrayList<FirePlug>();
		String json="";
		try{
			
			resultList = kgnService.SelectList();
			
			json = new Gson().toJson(resultList);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		mav.setViewName("/kgn/map");
		mav.addObject("resultList",json);
		
		return mav;
	}
	
	
}
