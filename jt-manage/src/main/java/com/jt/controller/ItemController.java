package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;


	@RequestMapping("/query")
	public EasyUIData findItemByPage(Integer rows,Integer page) {
		return itemService.findItemByPage(page,rows);
	}
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult .OK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.OK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids) {


		int rows = itemService.deleteItem(ids);


		return SysResult.OK();


	}
	@RequestMapping("/instock")
	public SysResult instockItem(Long[] ids) {

		int rows = itemService.instockItem(ids);
		if(rows>0) {
			return SysResult.OK();
		}else {
			return SysResult.fail("该商品已下架");
		}
	}


	@RequestMapping("/reshelf")
	public SysResult reshelfItem(Long[] ids) {

		int rows = itemService.reshelfItem(ids);
		if(rows>0) {
			return SysResult.OK();
		}else {
			return SysResult.fail("该商品已上架");
		}
	}
	@RequestMapping("query/item/desc/{itemId}")
	public SysResult findItemDesc(@PathVariable Long itemId) {
		try {
		ItemDesc itemDesc = itemService.findItemDesc(itemId);
		return SysResult.OK(itemDesc);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
			}
	}
}	


