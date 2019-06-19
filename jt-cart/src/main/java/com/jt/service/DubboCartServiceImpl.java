package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service(timeout=3000)
public class DubboCartServiceImpl implements DubboCartService {
		@Autowired
		private CartMapper cartMapper;

		@Override
		public List<Cart> findCartListByUserId(Long userId) {
			QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("user_id", userId);
			List<Cart> cartList = cartMapper.selectList(queryWrapper);
			
			return cartList;
		}
		/**
		 * 更新数据信息: num/updated(更新时间)
		 * 判断条件:  where user_id and item_id
		 */
		@Transactional//事务控制
		@Override
		public void updateCartNum(Cart cart) {
			Cart updateCart = new Cart();
			updateCart.setNum(cart.getNum()).setUpdated(new Date());
			UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
			cartMapper.update(cart, updateWrapper);
		}
		/**
		 * 删除购物车数据信息
		 * 条件: userid和itemid
		 */
		@Transactional
		@Override
		public void deleteCart(Cart cart) {
			QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
			cartMapper.delete(queryWrapper);
		}
		/**
		 * 新增业务实现
		 * 1.用户第一次新增的商品可以直接入库
		 * 2.用户添加的商品不是第一次入库,应该只做数量修改,不做数据的新增
		 */
		@Override
		public void addCartItem(Cart cart) {
			QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
			Cart rows = cartMapper.selectOne(queryWrapper);
			if(rows==null) {
				//用户第一次购买商品,可以直接入库
				cart.setCreated(new Date());
				cartMapper.insert(cart);
			}else {
				int num = cart.getNum() + rows.getNum();
				rows.setNum(num).setUpdated(new Date());
				cartMapper.updateById(rows);
			}
			
		}
}
