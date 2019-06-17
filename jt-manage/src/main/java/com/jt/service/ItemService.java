package com.jt.service;

import java.util.List;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

public interface ItemService {

	EasyUIData findItemByPage(Integer page,Integer rows);

	int saveItem(Item item, ItemDesc itemDesc);

	int updateItem(Item item, ItemDesc itemDesc);

	int deleteItem(Long[] ids);

	int instockItem(Long[] ids) ;

	int reshelfItem(Long[] ids);

	ItemDesc findItemDesc(Long itemId);

	Item findItemById(Long id);
	
}
