package org.kaapi.app.controllers.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.ForumCategory;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.ForumCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forum/category")
public class ForumCategoryController {

	@Autowired
	ForumCategoryService forumCateService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String , Object>> listForumCate(Pagination pagination){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
		List<ForumCategory> list = forumCateService.listForumCate(pagination);
		if(list == null){
			map.put("MESSAGE", "RECORD NOT FOUND");
			map.put("STATUS", false);
		}else{
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("RES_DATA",list);
		}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	
	
}
