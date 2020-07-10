package com.invento.ecom.service;

import com.invento.ecom.model.Product;
import com.invento.ecom.util.StringUtil;
import com.invento.ecom.viewModel.AddProductRequest;
import com.invento.ecom.viewModel.SearchProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductService {
    @Autowired
    private StringUtil stringUtil;

    private static List<Product> productList = new ArrayList<Product>();

    public List<Product> getProductListForSearch(SearchProductRequest searchProductRequest){
        List<Product> productList = null;
        if(searchProductRequest!=null && this.productList!=null && !this.productList.isEmpty()){
            if(stringUtil.isNotNull(searchProductRequest.getColor())){
                List<Product> products = new ArrayList<Product>();
                for(Product product:this.productList){
                    if(product!=null && stringUtil.isNotNull(product.getColor()) && product.getColor().equals(searchProductRequest.getColor())){
                        products.add(product);
                    }
                }
                if(products!=null && !products.isEmpty()){
                    productList = new ArrayList<Product>();
                    productList.addAll(products);
                }
            }
            if(stringUtil.isNotNull(searchProductRequest.getProductName())){
                List<Product> products = new ArrayList<Product>();
                for(Product product:this.productList){
                    if(product!=null && stringUtil.isNotNull(product.getProductName()) && product.getProductName().equals(searchProductRequest.getProductName()) && (productList==null || (productList!=null && !productList.contains(product)))){
                        products.add(product);
                    }
                }

                if(products!=null && !products.isEmpty()){
                    if(productList==null) {
                        productList = new ArrayList<Product>();
                    }
                    productList.addAll(products);
                }
            }

            if(productList==null && !stringUtil.isNotNull(searchProductRequest.getProductName()) && !stringUtil.isNotNull(searchProductRequest.getColor())){
                productList = this.getProductList();
            }
        }
        return productList;
    }

    public Product getSingleProductByProductID(int productID){
        Product product = null;
        if(productID>0 && productList!=null && !productList.isEmpty()){
            for(Product productSearch:productList){
                if(productSearch!=null && productSearch.getProductID() == productID){
                    product = productSearch;
                    break;
                }
            }
        }
        return product;
    }

    public boolean addProduct(AddProductRequest addProductRequest){
        boolean added =false;
        if(addProductRequest!=null && addProductRequest.getProductName()!=null && addProductRequest.getColor()!=null && addProductRequest.getPrice()!=null && addProductRequest.getQuantity()!=null){
            Product product = new Product();
            product.setProductID(productList.size()+1);
            product.setProductName(addProductRequest.getProductName());
            product.setColor(addProductRequest.getColor());
            product.setPrice(addProductRequest.getPrice());
            product.setQuantity(addProductRequest.getQuantity());

            productList.add(product);
        }
        return added;
    }

    public boolean updateProduct(AddProductRequest addProductRequest){
        boolean updated =false;
        if(addProductRequest!=null && addProductRequest.getProductID()>0 && addProductRequest.getProductName()!=null && addProductRequest.getColor()!=null && addProductRequest.getPrice()!=null && addProductRequest.getQuantity()!=null){
            if(productList!=null && !productList.isEmpty()) {
                Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductID, product -> product));
                if(productMap!=null && productMap.containsKey(addProductRequest.getProductID())) {
                    Product product = productMap.get(addProductRequest.getProductID());
                    product.setProductName(addProductRequest.getProductName());
                    product.setColor(addProductRequest.getColor());
                    product.setPrice(addProductRequest.getPrice());
                    product.setQuantity(addProductRequest.getQuantity());
                    updated = true;
                }
            }
        }
        return updated;
    }

    public boolean deleteProduct(AddProductRequest addProductRequest){
        boolean deleted = false;
        if(addProductRequest!=null && addProductRequest.getProductID()>0){
            Product product = new Product();
            product.setProductID(addProductRequest.getProductID());
            if(productList!=null && productList.remove(product)){
                deleted = true;
            }
        }
        return deleted;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
