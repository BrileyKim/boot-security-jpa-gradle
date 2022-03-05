package com.example.demo.lsj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/lsj")
@RequiredArgsConstructor
public class LsjController {
	
	@GetMapping()
	public ModelAndView SelectList(ModelAndView mav) {
		
		mav.setViewName("/lsj/list");
		System.out.println("ffffff");
		
		return mav;
		
	}

}
