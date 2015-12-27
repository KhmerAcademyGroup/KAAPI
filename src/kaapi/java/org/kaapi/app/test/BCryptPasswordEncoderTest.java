package org.kaapi.app.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
}
