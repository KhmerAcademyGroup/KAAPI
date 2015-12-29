package org.kaapi.app.test;




import javax.xml.bind.DatatypeConverter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {

	public static void main(String[] args) {
		
			// Encrypt Password
			//System.out.println(new BCryptPasswordEncoder().encode("123"));
			
			// Encrypt Password basic authentication
			//System.out.println(Base64.getUrlEncoder().encodeToString("123:123".getBytes()));
		
			String str = "428";

	        // encode data using BASE64
	        String encoded = DatatypeConverter.printBase64Binary(str.getBytes());
	        System.out.println("encoded value is \t" + encoded);

	        // Decode data 
	        String decoded = new String(DatatypeConverter.parseBase64Binary(encoded));
	        System.out.println("decoded value is \t" + decoded);

	        System.out.println("original value is \t" + str);
		
		
	}
	
}
