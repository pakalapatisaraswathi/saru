package com.tcs.springmvc.controller;

import java.security.Provider.Service;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tcs.springmvc.entity.Productentity;
import com.tcs.springmvc.model.Product;
import com.tcs.springmvc.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class Productcontroller {
	@Autowired
	ProductService productservice;
	
     @GetMapping("/productform")
     public String getproduct(Model model) {
    	 Product product= new Product();
    	 product.setMadein("INDIA");
    	 product.setDiscountrate(10);
    	 product.setQuantity(10);
    	 model.addAttribute("product", product);
    	 return "add-product";
     }
/*@PostMapping("/saveproduct")
public String saveproduct(Product product) {
	
    productservice.saveproductDetails(product);
    System.out.println(product);
    return"success" ;
}*/
@GetMapping("/getallproducts")
public String getallproducts(Model model)
{
	List<Productentity> products=productservice.getallproducts();
	model.addAttribute("products",products);
	return "product-list";
}
@GetMapping("/search")
public String getsearchform() {
    return "searchform";
}
@PostMapping("/searchbyid")
public String searchbyid(@RequestParam long id,Model model ) {
	Productentity productentity= productservice.searchbyid(id);
	model.addAttribute("product", productentity);
    return "searchform" ;
}

@GetMapping("/delete/{id}")
public String deleteproductbyid(@PathVariable("id")long id) {
	productservice.deleteproductid(id);
    return "redirect:/getallproducts";
}

@GetMapping("/edit/{id}")
public String editById(@PathVariable Long id,Model model) {
	Product product=productservice.editById(id);
	model.addAttribute("product",product);
	model.addAttribute("id",id);
    return "editproductform";
 }
@PostMapping("/editproductsave/{id}")
public String updateData(@PathVariable Long id,Product product) {
   productservice.updateData(id,product);
    
    return "redirect:/allproducts";
}
@PostMapping("/saveproduct")
public String saveproduct(@Valid Product productmodel, BindingResult bindingResult, Model model) {
    
    HashMap<String, String> validationErrors = new HashMap<>();
    
    if (bindingResult.hasErrors()) {
        
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        
        model.addAttribute("validationErrors", validationErrors);
        
         
        return "add-product";
    }
    
    
    productservice.saveproductDetails(productmodel);
    
    
    return "redirect:/getallproducts";
}




}




