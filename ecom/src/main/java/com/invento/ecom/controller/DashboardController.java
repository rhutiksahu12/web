package com.invento.ecom.controller;

import com.invento.ecom.service.AuthenticationService;
import com.invento.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProductService productService;

    @RequestMapping(name = "/adminDashboard",value = "/adminDashboard",method = RequestMethod.GET)
    public ModelAndView adminDashboard(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("adminDashboard");

        modelAndView.addObject("userList",authenticationService.getUserList());
        modelAndView.addObject("productList",productService.getProductList());

        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }

    @RequestMapping(name = "/customerDashboard",value = "/customerDashboard",method = RequestMethod.GET)
    public ModelAndView customerDashboard(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("customerDashboard");

        modelAndView.addObject("productList",productService.getProductList());
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }
}
