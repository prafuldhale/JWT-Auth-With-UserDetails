//package com.planto.user_service.openfeign;
//
//import com.planto.user_service.security.JwtUtils;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
///**
// * @author Praful
// */
//@Configuration
//public class FeignClientConfig {
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return new FeignClientInterceptor();
//    }
//}
////a
//class FeignClientInterceptor implements RequestInterceptor {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            // Assuming your UserDetails implementation has a method to get the token
//            String token = jwtUtils.generateTokenFromUsername(userDetails); // Replace with actual method to get token
//            requestTemplate.header("Authorization", "Bearer " + token);
//        }
//    }
//}