package com.learning.helper;

import java.util.Map;
import java.util.regex.Pattern;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.service.CaptchaValidatorService;

public class Validation {
	public static boolean emailValidation(String email) {
	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	    return email != null && Pattern.matches(emailRegex, email);
	}

    public static boolean passwordValidation(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && Pattern.matches(passwordRegex, password);
    }

    public static boolean priceValidation(String price) {
        String priceRegex = "^[0-9]+(\\.[0-9]{1,2})?$"; 
        return price != null && Pattern.matches(priceRegex, price);
    }

    public static boolean isValidPhoneNumber(String phone) {
        String regex = "^[1-9][0-9]{9}$";  
        return phone != null && Pattern.matches(regex, phone);
    }
    
    public static void validateUserInput(UserRegistrationDTO registrationDTO, Map<String, String> response, CaptchaValidatorService captchaValidatorService) {
    	response.put("errorCaptcha", !captchaValidatorService.isCaptchaValid(registrationDTO.getRecaptcha()) ? "Captcha invalid" : null);

        response.put("errorEmail", !emailValidation(registrationDTO.getEmail()) ? "Invalid Email Details" : null);

        response.put("errorPassword", !passwordValidation(registrationDTO.getPassword()) ? "Password must have at least 8 characters, including 1 lowercase, 1 uppercase, 1 digit, and 1 special character." : null);

        response.put("errorPhone", !isValidPhoneNumber(registrationDTO.getPhone()) ? "Invalid phone number! Phone number must have 10 digits." : null);
    
        response.values().removeIf(value -> value == null);
    }
    
}
