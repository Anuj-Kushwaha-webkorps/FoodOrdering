package com.learning.DTO;

import lombok.Data;

@Data
public class UserRegistrationDTO {
	private String name;
	private String email;
	private String Address;
	private String phone;
	private String password;
	private String recaptcha;
}
