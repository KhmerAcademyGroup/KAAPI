package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Video;
import org.kaapi.app.forms.FrmAddCategory;
import org.kaapi.app.forms.FrmUpdateCategory;
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
@RequestMapping("api/elearning/category")
public class CategoryController {

	@Autowired
	@Qualifier("CategoryService")
	CategoryService categoryService;

	@RequestMapping(value="/listall", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listCategoryForAddVideo() {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Category> list = categoryService.listCategory();
			if (list.isEmpty()) {
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			} else {
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA", list);
			}
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listCategory(
			@RequestParam(value = "name", required = false, defaultValue = "") String categoryName,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "item", required = false, defaultValue = "20") int item) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(categoryService.countCategory());
			pagination.setTotalPages(pagination.totalPages());
			List<Category> list = categoryService.listCategory(pagination, categoryName);
			if (list == null) {
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			} else {
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA", list);
				map.put("PAGINATION", pagination);
			}
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable("id") String id) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (categoryService.deleteCategory(id)) {
				map.put("MESSAGE", "CATEGORY HAS BEEN DELETED!");
				map.put("STATUS", true);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}
			map.put("MESSAGE", "CATEGORY NOT DELETED!");
			map.put("STATUS", false);
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/listvideoincategory", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listVideoIncategory(
			@RequestParam(value = "categoryId") String categoryId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "item", required = false, defaultValue = "20") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Video> listvideo = categoryService.listVideosInCategory(categoryId, page, item);
			if (listvideo == null) {
				map.put("MESSAGE", "Not found!");
				map.put("STATUS", false);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
			}
			map.put("MESSAGE", "CATEGORY FOUND!");
			map.put("STATUS", true);
			map.put("RES_DATA", listvideo);
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/{cid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCategory(@PathVariable("cid") String categoryId) {

		Category getcategory = categoryService.getCategory(categoryId);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (getcategory == null) {
				map.put("MESSAGE", "Not found!");
				map.put("STATUS", false);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
			}
			map.put("MESSAGE", "CATEGORY FOUND!");
			map.put("STATUS", true);
			map.put("RES_DATA", getcategory);
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody FrmUpdateCategory category) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (categoryService.updateCategory(category)) {
				map.put("STATUS", true);
				map.put("MESSAGE", "UPDATE SUCCESS");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}
			map.put("STATUS", false);
			map.put("MESSAGE", "UPDATE NOT SUCCESS");
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertCategory(@RequestBody FrmAddCategory category) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (categoryService.insertCategory(category)) {
				map.put("STATUS", true);
				map.put("MESSAGE", "ADD SUCCESS");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}
			map.put("STATUS", false);
			map.put("MESSAGE", "ADD NOT SUCCESS");
		} catch (Exception e) {
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	public void ViewCategory() {
	}

	public void CategoryVideo() {
	}

	public void ToAddCategory() {
	}

}
