package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
        @Reference(timeout = 3000,check = false)
        private DubboOrderService dubboOrderService;
        @Reference(timeout = 3000,check = false)
        private DubboCartService dubboCartService;

        @RequestMapping("/create")
        public String findOrder(Model model){
            Long userId = UserThreadLocal.get().getId();
            List<Cart> carts = dubboCartService.findCartListByUserId(userId);
            model.addAttribute("carts",carts);
            return "order-cart";
        }

}
