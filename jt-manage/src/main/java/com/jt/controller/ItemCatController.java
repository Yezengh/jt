package com.jt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;



	/**用户发起post请求携带了itemCatid */
	@RequestMapping("/queryItemName")
	public String queryItemName(Long itemCatId) {
		ItemCat item = itemCatService.queryItemName(itemCatId);
		String name = item.getName();
		return name; 
	}
	/**查询全部数据的商品分类信息*/
	@RequestMapping("/list")
	public List<EasyUITree> findItemCatByParentId (@RequestParam(value="id",defaultValue="0")Long parentId){
		//查询1级商品分类信息
		//System.out.println(parentId);
		return itemCatService.findItemCatByParentId(parentId);
		/*	public List<EasyUITree>findItemCatByCache(@RequestParam(value="id",defaultValue="0")Long parentId){
		return itemCatService.findItemCatByCache(parentId);
	}*/
	}
}