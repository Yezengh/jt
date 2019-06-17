package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{
	
	@Select("select * from tb_item order by updated desc limit #{start} ,#{rows}")
	List<Item> findItemByPage(@Param("start")Integer start, @Param("rows")Integer rows);
	
	@Update("update tb_item set status =2 where id=#{id}")
	int instockItem(Long id);
	@Update("update tb_item set status =1 where id=#{id}")
	int reshelfItem(Long id);
	
	@Select("select status from tb_item where id  =#{id}")
	int selectStatusById(Long id);
}
