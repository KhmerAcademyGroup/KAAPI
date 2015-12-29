package org.kaapi.app.entities;

import javax.xml.bind.DatatypeConverter;

public class Encryption {

	public static String encode(String code){
		return DatatypeConverter.printBase64Binary(code.getBytes());
	}
	
	public static String decrytp(String encoded){
		return  new String(DatatypeConverter.parseBase64Binary(encoded));
	}
	
}
