package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentSerice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentSerice paymentSerice;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentSerice.create(payment);
        log.info("插入结果" + result);
        if(result > 0){
            return new CommonResult(200,"success" + serverPort,result);
        }else{
            return new CommonResult(444,"error" + serverPort,null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult get(@PathVariable("id")Long id){
        Payment payment = paymentSerice.getPaymentById(id);
        log.info("查询结果" + payment + "333");
        if(payment != null){
            return new CommonResult(200,"success" + serverPort,payment);
        }else{
            return new CommonResult(444,"error" + serverPort,null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> list = discoveryClient.getServices();
        for(String element : list){
            System.out.println("element" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance : instances){
            log.info(instance.getHost() + instance.getServiceId() + instance.getPort() + instance.getUri());
        }
        return this.discoveryClient;
    }
}
