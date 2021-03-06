package com.shoppingcart.apps.model;

import java.util.Arrays;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import com.shoppingcart.apps.entity.Product;
@JsonIgnoreProperties(ignoreUnknown = true) 
public class ProductInfo {
    private String code;
    private String name;
    private double price;
    private byte[] image;
 
    private boolean newProduct=false;
 
    // Upload file.
    private CommonsMultipartFile fileData;
 
    public ProductInfo() {
    }
 
   
 
    
    public ProductInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public CommonsMultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }
 
    public boolean isNewProduct() {
        return newProduct;
    }
 
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}




	@Override
	public String toString() {
		return "ProductInfo [code=" + code + ", name=" + name + ", price=" + price + ", image=" + Arrays.toString(image)
				+ ", newProduct=" + newProduct + ", fileData=" + fileData + "]";
	}
 
}
