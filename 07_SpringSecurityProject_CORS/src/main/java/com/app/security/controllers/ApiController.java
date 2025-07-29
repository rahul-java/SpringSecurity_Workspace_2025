package com.app.security.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.dtos.ProductDto;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {

    @GetMapping("/route1")
    public String route1(){
        return "Menu Changed ::: This is protected route1.";
    }

    @GetMapping("/route2")
    public String route2(){
        return "Change Price ::: This is protected route2.";
    }
    
    @GetMapping("/route3")
    public String route3(Principal principal){
        return "Order Food ::: This is protected route3.";
    }
    
    @GetMapping("/route4")
    public String route4(){
        return "Pay Bill ::: This is protected route4.";
    }
    
    @GetMapping("/route5")
    @PreAuthorize("hasRole('ADMIN')")
    public String route5(){
        return "Drink Water ::: This is protected route5.";
    }
    
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
    	List<ProductDto> pdtoList=new ArrayList<ProductDto>();
    	ProductDto pdto1=new ProductDto();
    	pdto1.setProductId("1001");
    	pdto1.setTitle("iPhone 11");
    	pdto1.setPrice(50000);
    	pdto1.setDescription("2011");
    	pdtoList.add(pdto1);
    	
    	ProductDto pdto2=new ProductDto();
    	pdto2.setProductId("1002");
    	pdto2.setTitle("iPhone 12");
    	pdto2.setPrice(50000);
    	pdto2.setDescription("2012");
    	pdtoList.add(pdto2);
    	
    	ProductDto pdto3=new ProductDto();
    	pdto3.setProductId("1003");
    	pdto3.setTitle("iPhone 13");
    	pdto3.setPrice(50000);
    	pdto3.setDescription("2013");
    	pdtoList.add(pdto3);
    	
    	ProductDto pdto4=new ProductDto();
    	pdto4.setProductId("1004");
    	pdto4.setTitle("iPhone 14");
    	pdto4.setPrice(50000);
    	pdto4.setDescription("2014");
    	pdtoList.add(pdto4);
    	
    	ProductDto pdto5=new ProductDto();
    	pdto5.setProductId("1005");
    	pdto5.setTitle("iPhone 15");
    	pdto5.setPrice(50000);
    	pdto5.setDescription("2015");
    	pdtoList.add(pdto5);
    	System.out.println(pdtoList);
        return new ResponseEntity<>(pdtoList, HttpStatus.OK);
    }
}
