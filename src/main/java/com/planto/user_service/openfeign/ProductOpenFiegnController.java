//package com.planto.user_service.openfeign;
//
//import com.planto.user_service.response.Product;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//import java.util.List;
//
///**
// * @author Praful
// */
//@FeignClient(name = "product-service", url = "{product.service.url}")
//public interface ProductOpenFiegnController {
//    @GetMapping("/getProduct")
//    List<Product> getAllProducts(@RequestHeader("Authorization") String token);
//
//}
