package com.jt.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
		@Reference(timeout=3000,check = false)
		private DubboCartService cartService;
		
		/**
		 * 1.实现商品列表信息展现
		 * @return
		 */
		@RequestMapping("/show")
		public String findCartList(Model model,HttpServletRequest request) {
			//Long userId = 7L; //暂时写
			User user =  (User) request.getAttribute("user");
			Long userId = user.getId();
			List<Cart> cartList = cartService.findCartListByUserId(userId);
			model.addAttribute("cartList",cartList);
			return "cart";
		}
		/**
		 * 实现购物车数量的修改
		 * 规定: 如果url的参数中,使用了restFul风格获取数据时,
		 * 接受参数是对象并且属性匹配,则 可以使用对象接受
		 */
			@RequestMapping("/update/num/{itemId}/{num}")
			@ResponseBody
			public SysResult updateCartNum(Cart cart) {
				try {
					//Long userId = 7L;

					User user = UserThreadLocal.get();
					Long userId = user.getId();
					cart.setUserId(userId);
					cartService.updateCartNum(cart);
					return SysResult.OK();
				} catch (Exception e) {
					e.printStackTrace();
					return SysResult.fail();
				}
			}
			//删除购物车商品信息
			@RequestMapping("/delete/{itemId}")
			public String deleteCartNum(Cart cart) {
					//Long userId = 7L;
					//User user =  (User) request.getAttribute("user");
					User user = UserThreadLocal.get();
					Long userId = user.getId();
					cart.setUserId(userId);
					cartService.deleteCart(cart);
					return "redirect:/cart/show";
			}
			@RequestMapping("/add/{itemId}")
			public String insertCartItem(Cart cart) {
					//Long userId = 7L;
					//User user =  (User) request.getAttribute("user");
					User user = UserThreadLocal.get();
					Long userId = user.getId();
					cart.setUserId(userId);
					cartService.addCartItem(cart);
					return "redirect:/cart/show";
			}
}
