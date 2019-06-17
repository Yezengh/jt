package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jt.anno.Cache_find;
import com.jt.enu.KEY_ENUM;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Override
	//@Cache_find(key="ITEM",keyType=KEY_ENUM.AUTO)
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		//查询商品总记录数 total
		Integer total = itemMapper.selectCount(null);

		Integer start = (page-1)* rows;
		//分页查询商品记录数
		List<Item> itemList = itemMapper.findItemByPage(start,rows);

		EasyUIData easyUIData = new EasyUIData();
		easyUIData.setRows(itemList);
		easyUIData.setTotal(total);
		return easyUIData;
	}

	@Transactional//添加事务控制
	@Override
	public int saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		int rows = itemMapper.insert(item);
		
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
		return rows;
	}
	@Transactional()//propagation:事务的传播属性(默认值 REQUIRED 必须添加事务 事务合并) 
	//REQUIRES_NEW 必须新建一个事务)
	//supports 表示事务支持的 查询之前有事务时则合并事务
	@Override
	public int updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		int rows = itemMapper.updateById(item);
		itemDesc.setItemId(item.getId()).setUpdated(item.getCreated());
		itemDescMapper.updateById(itemDesc);
		return rows;
	}

	@Override
	public int deleteItem(Long[ ] ids) {
		int rows =0;
		for (Long integer : ids) {
			rows=itemMapper.deleteById(integer);
			itemDescMapper.deleteById(integer);
		}
		return rows;
	}
	@Transactional
	@Override
	public int instockItem(Long[] ids)  {
		int rows=0;
		for (Long id : ids) {
			int status = itemMapper.selectStatusById(id);
			if(status==1) {
				rows = itemMapper.instockItem(id);
			}else {
				continue;
				//return rows;
			}
		}
		return rows;
	}
	@Override
	public int reshelfItem(Long[] ids) {
		int rows=0;
		for (Long id : ids) {
			int status = itemMapper.selectStatusById(id);
			if(status==2) {
				rows = itemMapper.reshelfItem(id);
			}else {
				continue;
			}
		}
		return rows;
	}

	@Override
	public ItemDesc findItemDesc(Long itemId) {
		ItemDesc itemDesc = itemDescMapper.selectById(itemId);
		return itemDesc;
	}

	@Override
	public Item findItemById(Long id) {
		Item item = itemMapper.selectById(id);
		return item;
	}









}
