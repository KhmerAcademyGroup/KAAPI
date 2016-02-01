package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.kaapi.app.entities.MainCategory;
import org.kaapi.app.forms.FrmAddMainCategory;
import org.kaapi.app.forms.FrmUpdateMainCategory;
import org.kaapi.app.services.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/elearning/maincategory")
public class MainCategoryController {

	@Autowired
	@Qualifier("MainCategoryService")
	MainCategoryService mainCategoryService;
	
	@RequestMapping(value = "/listmaincategory", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listMainCategory(
			@RequestParam(value = "name", required = false, defaultValue = "") String mainCategoryName) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		List<MainCategory> list = mainCategoryService.listMainCategory(mainCategoryName);
		if (list.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", list);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/getmaincategory/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getMainCategory(@PathVariable("id") String maincategoryid) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		MainCategory list = mainCategoryService.getMainCategory(maincategoryid);
		if (list == null) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", list);
		}catch(Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteMainCategory(@PathVariable("id") String maincategoryid) {

		Map<String, Object> map = new HashMap<String, Object>();
		try{
		if (mainCategoryService.deleteMainCategory(maincategoryid)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "DELETE SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("MESSAGE", "DELETE NOT SUCCESS");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateMainCategory(@RequestBody FrmUpdateMainCategory maincategory) {

		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		if (mainCategoryService.updateMainCategory(maincategory)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "UPDATE SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "UPDATE NOT SUCCESS");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> insertMainCategory(@RequestBody FrmAddMainCategory maincategory) {

		Map<String, Object> map = new HashMap<String, Object>();
		try{
		if (mainCategoryService.insertMainCategory(maincategory)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "ADD SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "ADD NOT SUCCESS");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}
	
	
	
	

	

	
	
	
	/*
	 * public void AddMainCategory(){} public void DeleteMainCategory(){} public
	 * void UpdateMainCategory(){} public void GetMainCategory(){} public void
	 * ListMainCategory(){}
	 */

}
