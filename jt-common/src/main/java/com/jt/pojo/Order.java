package com.jt.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)//链式加载
@TableName("tb_order")
public class Order {
    private Long orderId;   //订单id
    private Long payment;   //实付金额
    private Integer paymentType; //支付类型
    private Long postFee; //邮费
    private Integer status;//状态
    private Date createdDateTime;//订单创建时间
    private Date paymentTimeDateTime;//付款时间
    private Date endTimeDateTime;//交易完成时间
    private String shippingName;//物流名称
    private Long shippingCode;//物流单号
    private Long userId;//用户id


}
