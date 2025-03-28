package com.learning.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {

	private Integer userId;
	private String name;
	private String email;
	private String Address;
	private String phoneNo;
	private String password;
}
