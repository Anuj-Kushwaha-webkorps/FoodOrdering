package com.learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/error")
public class ErrorController {

	    public String error() {
	        System.out.println("Error for auth");
	        return "redirect:"; 
	    }
}
