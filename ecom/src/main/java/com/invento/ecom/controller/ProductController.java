package com.invento.ecom.controller;

import com.invento.ecom.model.Product;
import com.invento.ecom.service.ProductService;
import com.invento.ecom.util.StringUtil;
import com.invento.ecom.viewModel.AddProductRequest;
import com.invento.ecom.viewModel.SearchProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StringUtil stringUtil;

    @RequestMapping(name = "/product",value = "/product",method = RequestMethod.GET)
    public ModelAndView getProduct(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("productListing");
        List<Product> productList = productService.getProductList();
        if(productList==null){
            productList = new ArrayList<Product>();
        }
        modelAndView.addObject("productList",productList);

        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }

    @RequestMapping(name = "/addProduct",value = "/addProduct",method = RequestMethod.GET)
    public ModelAndView addProduct(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("addProduct");

        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }

    @RequestMapping(name = "/editProduct",value = "/editProduct",method = RequestMethod.GET)
    public ModelAndView addProduct(@RequestParam(name = "productID")int productID, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("editProduct");

        Product product = productService.getSingleProductByProductID(productID);

        modelAndView.addObject("singleProduct",product);

        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }

    @RequestMapping(name = "/deleteProduct",value = "/deleteProduct",method = RequestMethod.GET)
    public ModelAndView deleteProduct(@RequestParam(name = "productID")int productID, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/product");

        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductID(productID);
        productService.deleteProduct(addProductRequest);


        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
            modelAndView.addObject("successMessage",redirectAttributes.getFlashAttributes().get("successMessage"));
        }
        if(redirectAttributes.getFlashAttributes()!=null && redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            modelAndView.addObject("errorMessage",redirectAttributes.getFlashAttributes().get("errorMessage"));
        }
        return modelAndView;
    }

    @RequestMapping(name = "/addProduct",value = "/addProduct",method = RequestMethod.POST)
    public ModelAndView addProductPOST(AddProductRequest addProductRequest,RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        if(addProductRequest!=null && stringUtil.isNotNull(addProductRequest.getProductName())&& stringUtil.isNotNull(addProductRequest.getColor())&& stringUtil.isNotNull(addProductRequest.getPrice())&& stringUtil.isNotNull(addProductRequest.getQuantity())){
            productService.addProduct(addProductRequest);
        }
        else{
            modelAndView = new ModelAndView("redirect:/addProduct");
            redirectAttributes.addFlashAttribute("errorMessage","Kindly provide all fields while adding product");
        }
        return modelAndView;
    }

    @RequestMapping(name = "/editProduct",value = "/editProduct",method = RequestMethod.POST)
    public ModelAndView editProductPOST(AddProductRequest addProductRequest,RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        if(addProductRequest!=null && stringUtil.isNotNull(addProductRequest.getProductName())&& stringUtil.isNotNull(addProductRequest.getColor())&& stringUtil.isNotNull(addProductRequest.getPrice())&& stringUtil.isNotNull(addProductRequest.getQuantity())){
            productService.updateProduct(addProductRequest);
        }
        else{
            modelAndView = new ModelAndView("redirect:/editProduct?productID="+addProductRequest.getProductID());
            redirectAttributes.addFlashAttribute("errorMessage","Kindly provide all fields while adding product");
        }
        return modelAndView;
    }

    @RequestMapping(name = "/searchProduct",value = "/searchProduct",method = RequestMethod.POST)
    public ModelAndView getProductForSearch(@ModelAttribute SearchProductRequest searchProductRequest, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("productListing");
        if(searchProductRequest!=null){
            List<Product> productList = productService.getProductListForSearch(searchProductRequest);
            if(productList==null){
                productList = new ArrayList<Product>();
            }
            modelAndView.addObject("productList",productList);
        }
        return modelAndView;
    }
}
