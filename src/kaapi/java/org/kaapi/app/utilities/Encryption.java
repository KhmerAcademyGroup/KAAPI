package org.kaapi.app.utilities;

import javax.xml.bind.DatatypeConverter;

public class Encryption {

	public static String encode(String code){
		return DatatypeConverter.printBase64Binary(code.getBytes());
	}
	
	public static String decode(String encoded){
		return  new String(DatatypeConverter.parseBase64Binary(encoded));
	}
	public static void main(String[] args) {
		System.out.println(new Encryption().encode("1"));
	}
	
	/*MzQx*/
}
