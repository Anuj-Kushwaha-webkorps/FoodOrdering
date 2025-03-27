package com.learning.jwt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class errorController {

	 @GetMapping("/error")
	    public String error() {
	        System.out.println("Error for auth");
	        return "redirect:"; 
	    }
}
