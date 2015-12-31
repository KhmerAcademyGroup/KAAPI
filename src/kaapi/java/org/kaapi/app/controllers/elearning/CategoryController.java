package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/category")
public class CategoryController {

	@Autowired
	@Qualifier("CategoryService")
	CategoryService categoryService;

	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listCategory(Pagination pagination,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
		List<Category> lstcategory = categoryService.listCategory(pagination, keyword);

		Map<String, Object> map = new HashMap<String, Object>();
		if (lstcategory == null) {
			map.put("MESSAGE", "Not found!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		System.err.println(lstcategory.size());
		pagination.setTotalCount(categoryService.countCategory());
		pagination.setTotalPages(pagination.totalPages());
		map.put("MESSAGE", "LIST FOUND");
		map.put("RESP_DATA", lstcategory);
		map.put("STATUS", true);
		map.put("PAGINATION", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable("id") int id) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (categoryService.deleteCategory(id)) {
			map.put("MESSAGE", "CATEGORY HAS BEEN DELETED!");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("MESSAGE", "CATEGORY NOT DELETED!");
		map.put("STATUS", false);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/listvideoincategory", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listVideoIncategory(@RequestParam("category") int category,
			@RequestParam("page") int page) {

		System.err.println(category + " " + page);
		List<Video> listvideo = categoryService.listVideosInCategory(category, page, 10);
		Map<String, Object> map = new HashMap<String, Object>();
		if (listvideo == null) {
			map.put("MESSAGE", "Not found!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("MESSAGE", "CATEGORY FOUND!");
		map.put("STATUS", true);
		map.put("RES_DATA", listvideo);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/getcategory", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getCategory(@RequestParam("category") int category) {

		Category getcategory = categoryService.getCategory(category);
		Map<String, Object> map = new HashMap<String, Object>();
		if (getcategory == null) {
			map.put("MESSAGE", "Not found!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("MESSAGE", "CATEGORY FOUND!");
		map.put("STATUS", true);
		map.put("RES_DATA", getcategory);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody Category category) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (categoryService.updateCategory(category)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "UPDATE SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "UPDATE NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addCategory(@RequestBody Category category) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (categoryService.insertCategory(category)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "ADD SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "ADD NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	public void ViewCategory() {
	}

	public void CategoryVideo() {
	}

	public void ToAddCategory() {
	}

}
