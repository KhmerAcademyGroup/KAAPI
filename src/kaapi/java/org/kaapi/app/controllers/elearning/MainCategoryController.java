package org.kaapi.app.controllers.elearning;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.kaapi.app.entities.MainCategory;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/elearning/maincategory")
public class MainCategoryController {

	@Autowired
	@Qualifier("MainCategoryService")
	MainCategoryService mainCategoryService;

	
	@RequestMapping(value = "/listmaincategory", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listMainCategory(
			@RequestParam(value = "name", required = false, defaultValue = "") String mainCategoryName) {
		List<MainCategory> list = mainCategoryService.listMainCategory(mainCategoryName);
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", list);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/getmaincategory/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getMainCategory(@PathVariable("id") String maincategoryid) {
		MainCategory list = mainCategoryService.getMainCategory(maincategoryid);
		Map<String, Object> map = new HashMap<String, Object>();
		if (list == null) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", list);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteMainCategory(@PathVariable("id") String maincategoryid) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (mainCategoryService.deleteMainCategory(maincategoryid)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "DELETE SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("MESSAGE", "DELETE NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateMainCategory(@RequestBody MainCategory maincategory) {

		System.err.println(maincategory.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		if (mainCategoryService.updateMainCategory(maincategory)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "UPDATE SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "UPDATE NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addMainCategory(@RequestBody MainCategory maincategory) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (mainCategoryService.insertMainCategory(maincategory)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "ADD SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "ADD NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}
	
	
	
	

	
	@RequestMapping(method = RequestMethod.POST, value = "/upload_image")
	public ResponseEntity<Map<String, Object>> insertPhoto(@RequestParam(value="LOGO_IMG",required=false) MultipartFile logo,@RequestParam(value="BACKGROUND" ,required = false) MultipartFile backgound, HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String logoname = logo.getOriginalFilename();
		String backgroundname=backgound.getOriginalFilename();
		String mainCategoryLogo = "";
		String mainCategoryBackground="";
		
		if (!logo.isEmpty() && !backgound.isEmpty()) {
			
			try {

				String logofile_ramdomname = UUID.randomUUID() + ".jpg";
				String backgroundfile_ramdomname = UUID.randomUUID() + ".jpg";
				
				
				
				byte[] logo_convert_byte_code = logo.getBytes();
				byte[] background_convert_byte_code =backgound.getBytes();

				// creating the directory to store file
				String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/image/maincategory");
				
				System.out.println(savePath);
				File path = new File(savePath);
				if (!path.exists()) {
					path.mkdir();
				}
				// creating the file on server
				File serverFilelogo = new File(savePath + File.separator + logofile_ramdomname);
				File serverFilebackground = new File(savePath + File.separator + backgroundfile_ramdomname);
				BufferedOutputStream streamlogo = new BufferedOutputStream(new FileOutputStream(serverFilelogo));
				BufferedOutputStream streambackground = new BufferedOutputStream(new FileOutputStream(serverFilebackground));
				streamlogo.write(logo_convert_byte_code);
				streambackground.write(background_convert_byte_code);
				streambackground.close();
				streamlogo.close();
				
				System.err.println(serverFilelogo.getAbsolutePath());
				System.err.println(serverFilebackground.getAbsolutePath());
				
				System.out.println("You are successfully uploaded file " + logoname +" and "+ backgroundname);

				mainCategoryLogo = "/resources/upload/image/maincategory/" + logofile_ramdomname;
				mainCategoryBackground ="/resources/upload/image/maincategory/" + backgroundfile_ramdomname;
			} catch (Exception e) {
				System.out.println("You are failed to upload " + logoname + " => " + e.getMessage());
			}
		} else {
			System.out.println("You are failed to upload " + logoname + " because the file was empty!");
		}
		
		if (mainCategoryLogo != "" && mainCategoryBackground != "") {
			map.put("STATUS", true);
			map.put("MESSAGE", "IMAGE HAS BEEN INSERTED");
			map.put("LOGO_IMG", mainCategoryLogo);
			map.put("BACKGROUND_IMAGE", mainCategoryBackground);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("STATUS", false);
			map.put("MESSAGE", "IMAGE HAS NOT BEEN INSERTED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	
	
	/*
	 * public void AddMainCategory(){} public void DeleteMainCategory(){} public
	 * void UpdateMainCategory(){} public void GetMainCategory(){} public void
	 * ListMainCategory(){}
	 */

}
