package com.learning.helper;

import java.util.regex.Pattern;

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
    
}
