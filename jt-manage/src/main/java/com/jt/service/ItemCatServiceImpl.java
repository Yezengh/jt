package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.anno.Cache_find;
import com.jt.enu.KEY_ENUM;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;

	/*
	 * @Autowired private Jedis jedis;
	 */
	@Override
	public ItemCat queryItemName(Long id) {
		return itemCatMapper.selectById(id);
	
	}
	/**1.根据parentId查询数据库的记录,返回ItemCatList集合
	 *  2.将itemCatList集合中的数据按照我们需要的数据封装到List<EasyUITree>
	 * 
	 * */
	@Override
	@Cache_find(key="ITEM_CAT",keyType=KEY_ENUM.AUTO)
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
	
		List<EasyUITree> itemTree = new ArrayList<>();
		List<ItemCat> findItemCatList = findItemCatList(parentId);
		for (ItemCat itemCat : findItemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCat.getId());
			easyUITree.setText(itemCat.getName());
			String state = itemCat.getIsParent()?"closed":"open";
			easyUITree.setState(state);
	
			List<ItemCat> child = findItemCatList(itemCat.getId());
			itemTree.add(easyUITree);
	}
		return itemTree;
	}
	public List<ItemCat> findItemCatList(Long parentId){
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}
	/*
	 * @Override
	 * 
	 * public List<EasyUITree> findItemCatByCache(Long parentId) { String key
	 * ="ITEM_CAT_"+parentId; String result = jedis.get(key); List<EasyUITree>
	 * treeList = new ArrayList<EasyUITree>(); if(StringUtils.isEmpty(result)) {
	 * //如果数据为null,则从数据库查询数据 treeList = findItemCatByParentId(parentId);
	 * //将数据转换为json String json=ObjectMapperUtil.toJson(treeList); jedis.setex(key,
	 * 7*24*3600, json);//设置超时时间,7天 System.out.println("业务查询数据库"); }else {
	 * //表示缓存中有数据 treeList = ObjectMapperUtil.toObj(result, treeList.getClass());
	 * System.out.println("业务查询redis缓存"); } return treeList; }
	 */
}
