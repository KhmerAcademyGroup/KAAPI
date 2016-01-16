package org.kaapi.app.test;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {

	public static void main(String[] args) {
		
			// Encrypt Password
			System.out.println(new BCryptPasswordEncoder().encode("!@#$KAAPI"));
			
			//EncryptPassword basic authentication
		  //  System.out.println(Base64.getUrlEncoder().encodeToString("KAAPI!@#$:!@#$KAAPI".getBytes()));

			/*String str = "61";


	        // encode data using BASE64
	        String encoded = DatatypeConverter.printBase64Binary(str.getBytes());
	        System.out.println("encoded value is \t" + encoded);

	        // Decode data 
	        String decoded = new String(DatatypeConverter.parseBase64Binary(encoded));
	        System.out.println("decoded value is \t" + decoded);

	        System.out.println("original value is \t" + str);*/
	        
	        
	        
	        
	        
	        
	        /* UUID uuid = UUID.randomUUID();
			 long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
			 System.out.println(Long.toString(l,Character.MAX_RADIX));
			 
			 UUID uuid1 = UUID.randomUUID();
			 long l1 = ByteBuffer.wrap(uuid1.toString().getBytes()).getLong();
			 System.out.println(Long.toString(l1,Character.MAX_RADIX));
			 
			 UUID uuid2 = UUID.randomUUID();
			 long l2 = ByteBuffer.wrap(uuid2.toString().getBytes()).getLong();
			 System.out.println(Long.toString(l2,Character.MAX_RADIX));
			 
			 
			 UUID uuid3 = UUID.randomUUID();
			 long l3 = ByteBuffer.wrap(uuid3.toString().getBytes()).getLong();
			 System.out.println(Long.toString(l3,Character.MAX_RADIX));
			 */
			 
			 /*UUID uuid4 = UUID.fromString("1");
			 long l4 = ByteBuffer.wrap(uuid4.toString().getBytes()).getLong();
			 System.out.println(Long.toString(l4,Character.MAX_RADIX));
		*/
		
	}
	
}

