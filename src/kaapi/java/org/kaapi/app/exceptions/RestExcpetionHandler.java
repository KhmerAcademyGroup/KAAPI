package org.kaapi.app.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kaapi.app.entities.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExcpetionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String , Object>> resourceNotFoundException(
		ResourceNotFoundException resourceNotFoundExcpetion , HttpServletRequest request){
		Map<String , Object> map = new HashMap<String , Object>();
		ErrorDetails error = new ErrorDetails();
		error.setTimeStamp(new Date().getTime());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setTitle("Resource not found!");
		error.setDetails(resourceNotFoundExcpetion.getMessage());
		error.setDeveloperMessage(resourceNotFoundExcpetion.getClass().getName());
		map.put("Error", error);
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<Map<String , Object>> resourceConflictException(
			ResourceConflictException resourceConflictException , HttpServletRequest request){
		Map<String , Object> map = new HashMap<String , Object>();
		ErrorDetails error = new ErrorDetails();
		error.setTimeStamp(new Date().getTime());
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setTitle("Resource not found!");
		error.setDetails(resourceConflictException.getMessage());
		error.setDeveloperMessage(resourceConflictException.getClass().getName());
		map.put("Error", error);
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.CONFLICT);
	}

}
