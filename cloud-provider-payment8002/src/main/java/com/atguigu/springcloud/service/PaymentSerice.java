package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.Payment;

public interface PaymentSerice {

    public int create(Payment payment); //写

    public Payment getPaymentById(Long id);  //读取

}
