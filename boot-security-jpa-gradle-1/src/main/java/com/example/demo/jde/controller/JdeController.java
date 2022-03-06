package com.example.demo.jde.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jde")
@RequiredArgsConstructor
public class JdeController {

	@GetMapping()
	public ModelAndView SelectList(ModelAndView mav) {
		
		mav.setViewName("/jde/list");
		//asasasqwewq
		return mav;
		
	}
}
