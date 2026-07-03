package com.projeto.e_commerce.order.dto;

import com.projeto.e_commerce.order.enums.OrderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPaymentDto {
    private Integer orderId;
    private OrderEnum status;
}