package com.invento.ecom.viewModel;

import java.io.Serializable;

public class SearchProductRequest implements Serializable {
    private static final long serialVersionUID = 3272043328485252626L;

    private String productName;
    private String color;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
