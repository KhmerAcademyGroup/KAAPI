package org.kaapi.app.utilities;

import javax.xml.bind.DatatypeConverter;

public class Encryption {

	public static String encode(String code){
		String encoded = "";
		try{
			encoded = DatatypeConverter.printBase64Binary(code.getBytes());
		}catch(Exception e){
//			e.printStackTrace();
		}
		return encoded;
	}
	
	public static String decode(String encoded){
		String decoded = "";
		try{
			decoded =  new String(DatatypeConverter.parseBase64Binary(encoded));
		}catch(Exception e){
//			e.printStackTrace();
		}
		return decoded;
	}
//	public static void main(String[] args) {
//		System.out.println(new Encryption().encode("1"));
//	}
	
	/*MzQx*/
}
