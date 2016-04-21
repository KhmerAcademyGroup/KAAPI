package org.kaapi.app.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmStudentDetail;
import org.kaapi.app.services.KSHRDStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/kshrd")
public class KSHRDStudentController {
	
	@Autowired
	KSHRDStudentService kshrdService;
	
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> courses(
			@RequestParam(value ="page", required = false, defaultValue = "1") int page,
			@RequestParam(value ="item" , required = false , defaultValue = "10") int item ,
			@RequestBody FrmStudentDetail d
			){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(d.getFullname() == null){
				d.setFullname("");
			}
			if(d.getCourseId() == null){
				d.setCourseId("");
			}
			if(d.getShifId()==null){
				d.setShifId("");
			}
			if(d.getTypeId() == null){
				d.setTypeId("");
			}
			if(d.getIsPaid()==null){
				d.setIsPaid("");
			}
			if(d.getStatus()==null){
				d.setStatus("");
			}
			System.out.println(d.getFullname());
			System.out.println(d.getCourseId());
			System.out.println(d.getShifId());
			System.out.println(d.getTypeId());
			System.out.println(d.getIsPaid());
			System.out.println(d.getStatus());

			
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(kshrdService.countKSHRDStudent(d));
			pagin.setTotalPages(pagin.totalPages());
			System.out.println(pagin.getTotalCount());
			List<FrmStudentDetail>  arr = kshrdService.listKSHRDStudent(d, pagin);
			if(!arr.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", arr);
				map.put("PAGINATION", pagin);
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/update_student_status", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateStudentStatus(@RequestBody FrmStudentDetail d){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(kshrdService.updateStatus(d)){
				map.put("STATUS", true);
				map.put("MESSAGE", "STATUS HAS BEEN UPDATED TO TRUE");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "STATUS HAS NOT BEEN UPDATED TO FALSE");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/update_student_ispaid", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateStudentIsPaid(@RequestBody FrmStudentDetail d){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(kshrdService.updateIsPaid(d)){
				map.put("STATUS", true);
				map.put("MESSAGE", "ISPAID HAS BEEN UPDATED TO TRUE");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "ISPAID HAS NOT BEEN UPDATED TO FALSE");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}

}
