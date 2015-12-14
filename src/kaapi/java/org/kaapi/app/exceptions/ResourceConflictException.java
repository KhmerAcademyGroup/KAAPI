package org.kaapi.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException{

	private static final long serialVersionUID = -2827210610746600257L;
	
	public ResourceConflictException(){
		
	}
	
	public ResourceConflictException(String message ){
		super(message);
	}
	
	public ResourceConflictException(String message , Throwable cause){
		super(message  , cause);
	}
	
	

}
