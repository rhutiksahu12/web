package com.invento.ecom.controller;

import com.invento.ecom.model.User;
import com.invento.ecom.service.AuthenticationService;
import com.invento.ecom.util.StringUtil;
import com.invento.ecom.viewModel.LoginRequest;
import com.invento.ecom.viewModel.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private StringUtil stringUtil;

    @RequestMapping(name = "/",value = "/",method = RequestMethod.GET)
    public ModelAndView getLoginPage(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("login");

        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }

    @RequestMapping(name = "/register",value = "/register",method = RequestMethod.GET)
    public ModelAndView getRegistrationPage(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("register");
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }

    @RequestMapping(name = "/login",value = "/login",method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute LoginRequest loginRequest, RedirectAttributes redirectAttributes, HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView("redirect:/customerDashboard");
        if(loginRequest!=null && loginRequest.getPassword()!=null && loginRequest.getPassword().length()>0 && loginRequest.getUserName()!=null && loginRequest.getUserName().length()>0){
            User user= authenticationService.authenticateUser(loginRequest);
            if(user!=null ){
                if(user.getUserType()==1){
                    modelAndView = new ModelAndView("redirect:/adminDashboard");

                }
                else{
                    modelAndView = new ModelAndView("redirect:/customerDashboard");
                }
                httpSession.setAttribute("user",user);
            }
            else{
                redirectAttributes.addFlashAttribute("errorMessage","Invalid Username/Password");
                modelAndView = new ModelAndView("redirect:/");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage","Kindly enter username and password");
            modelAndView = new ModelAndView("redirect:/");
        }
        return modelAndView;
    }

    @RequestMapping(name = "/register",value = "/register",method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute RegistrationRequest registrationRequest, RedirectAttributes redirectAttributes, HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView("redirect:/customerDashboard");
        if(registrationRequest!=null && stringUtil.isNotNull(registrationRequest.getUserName()) && stringUtil.isNotNull(registrationRequest.getPassword()) && stringUtil.isNotNull(registrationRequest.getEmail()) && stringUtil.isNotNull(registrationRequest.getName()) && stringUtil.isNotNull(registrationRequest.getPhoneNumber()) ){
            User user= authenticationService.registerUser(registrationRequest);
            if(user!=null ){
                modelAndView = new ModelAndView("redirect:/");
                redirectAttributes.addFlashAttribute("successMessage","User registered successfully");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage","AQll fields are mandatory kindly fill to register");
            modelAndView = new ModelAndView("redirect:/register");
        }
        return modelAndView;
    }

    @RequestMapping(name = "/logout",value = "/logout",method = RequestMethod.GET)
    public ModelAndView logout( HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        httpSession.removeAttribute("user");
        return modelAndView;
    }
}
