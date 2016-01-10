package org.kaapi.app.controllers.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.ForumCategory;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmAddForumCategory;
import org.kaapi.app.forms.FrmUpdateForumCategory;
import org.kaapi.app.services.ForumCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forum/category")
public class ForumCategoryController {

	@Autowired
	ForumCategoryService forumCateService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String , Object>> listForumCate(
			  @RequestParam(value = "page", required = false , defaultValue="1") int page 
			, @RequestParam(value="item" , required = false , defaultValue="20") int item){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(forumCateService.countForumCate());
			pagination.setTotalPages(pagination.totalPages());
			List<ForumCategory> list = forumCateService.listForumCate(pagination);
			if(list == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA",list);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/search" , method = RequestMethod.GET)
	public ResponseEntity<Map<String , Object>> searchForumCate(
			@RequestParam(value="name", required = false , defaultValue="") String categoryName , 
			@RequestParam(value = "page", required = false , defaultValue="1") int page ,
			@RequestParam(value="item" , required = false , defaultValue="20") int item){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(forumCateService.countSearchForumCate(categoryName));
			pagination.setTotalPages(pagination.totalPages());
			List<ForumCategory> list = forumCateService.searchForumCate(categoryName,pagination);
			if(list == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA",list);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{cid}" , method = RequestMethod.GET)
	public ResponseEntity<Map<String , Object>> getForumCate(@PathVariable("cid") String cid){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			ForumCategory forumCate = forumCateService.getForumCate(cid);
			if(forumCate == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA",forumCate);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Map<String , Object>> addForumCate(@RequestBody FrmAddForumCategory forumCate){
		System.out.println(forumCate.getCategoryName());
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCateService.addForumCategory(forumCate)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN INSERTED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN INSERTED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String , Object>> updateForumCate(@RequestBody FrmUpdateForumCategory forumCate){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			ForumCategory currentforumCate = forumCateService.getForumCate(forumCate.getCategoryId());
			if(currentforumCate == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
				return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
			}
			if(forumCateService.updateForumCate(forumCate)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	/*
	@RequestMapping(value="/{cid}" , method = RequestMethod.DELETE)
	public ResponseEntity<Map<String , Object>>deleteForumCate(@PathVariable("cid") String cid){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCateService.deleteForumCate(cid)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}*/
	
	
}
