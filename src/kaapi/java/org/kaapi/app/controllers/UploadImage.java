package org.kaapi.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kaapi.app.utilities.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/uploadimage")
public class UploadImage {

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public ResponseEntity<Map<String, Object>> uploadImageCategory(
			@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/image/category");
			UploadFile fileName = new UploadFile();
			if (file == null) {
				map.put("STATUS", false);
				map.put("MESSAGE", "IMAGE HAS NOT BEEN INSERTED");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			} else {
				String CategoryImage = fileName.sigleFileUpload(file, savePath);
				map.put("STATUS", true);
				map.put("MESSAGE", "IMAGE HAS BEEN INSERTED");
				map.put("IMG_CATE", CategoryImage);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/maincategory")
	public ResponseEntity<Map<String, Object>> uploadImageMainCategory(
			@RequestParam(value = "LOGO_IMG1", required = false) MultipartFile file1,
			@RequestParam(value = "LOGO_IMG", required = false) MultipartFile file, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		MultipartFile[] c = { file1, file };
		String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/image/maincategory");
		UploadFile fileName = new UploadFile();
		if (file != null && file1 != null) {
			String[] CategoryImage = fileName.multipleFileUpload(c, savePath);
			map.put("IMG_LOGO", CategoryImage[0]);
			map.put("IMG_BG", CategoryImage[1]);
			map.put("STATUS", true);
			map.put("MESSAGE", "IMAGE HAS BEEN INSERTED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("STATUS", false);
			map.put("MESSAGE", "IMAGE HAS NOT BEEN INSERTED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

}
