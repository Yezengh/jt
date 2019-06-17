package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;

@Controller
public class ItemController {
		/**
		 * 根据商品的id查询后台服务器的数据
		 * 业务步骤:
		 * 1.在前台service中实现httpClient调用
		 * 2.后台根据itemId查询数据库返回对象的json串
		 * 3.前台将json转化为item对象
		 * 4.将item的对象保存request域中
		 * 5/返回页面逻辑名称
		 */
		@Autowired
		private ItemService itemService;
		@RequestMapping("/items/{itemId}")
		public String findItemById(@PathVariable Long itemId,Model model) {
			Item item = itemService.findItemById(itemId);
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			model.addAttribute("item",item);
			model.addAttribute("itemDesc",itemDesc);
			return "item";
		}
}
