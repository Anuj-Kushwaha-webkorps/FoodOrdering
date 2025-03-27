package com.learning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.learning.entity.Admin;
import com.learning.entity.User;
import com.learning.helper.Validation;
import com.learning.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;

    public String saveAdmin(String name, String email, String password, String address, String phone,String captchaResponse, Model model) {
       
    	if (!captchaValidatorService.isCaptchaValid(captchaResponse)) {
    		String msgTxt = "Captcha verification failed";
    		model.addAttribute("msg", msgTxt);
            return "/user/error";
        }

    	if(!Validation.emailValidation(email)) {
            return "redirect:/user/register?error=Invalid+Email+Details";
    	}
    	
    	if(!Validation.passwordValidation(password)) {
    		String msg = "Password must have at least 8 character with atleast 1 small and capital letter, 1 digit and 1 Special character";
            return "redirect:/user/register?error=Invalid+password "+msg;
    	}
    	
    	if(!Validation.isValidPhoneNumber(phone)) {
    		String msg = "Invalide phone number ! phone number must have 10 digits.";
            return "redirect:/user/register?error=Invalid+phone+number "+msg;
    	}
    	
    	Optional<User> ad = userRepository.findByEmail(email);
    	
    	if(ad.isPresent()) {
    		return "redirect:/user/register?error=User+Already+Exist+With+Provided+Email";
    	}

    	User user = User.builder()
                .userId(java.util.UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .password(password) 
                .address(address)
                .phoneNumber(phone)
                .build();

        userRepository.save(user);
        return "redirect:/user/login";
    }
    
    public User authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional.get();
        }
        return null;
    }
}

