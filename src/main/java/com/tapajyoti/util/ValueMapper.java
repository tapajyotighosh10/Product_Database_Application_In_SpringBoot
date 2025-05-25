package com.tapajyoti.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapajyoti.dto.ProductRequestDTO;
import com.tapajyoti.dto.ProductResponseDTO;
import com.tapajyoti.entity.Product;
import jakarta.validation.Valid;

public class ValueMapper {

    public static Product convertToEntity(ProductRequestDTO productRequestDTO){
         Product product=new Product();
         product.setName(productRequestDTO.getName());
         product.setProductType(productRequestDTO.getProductType());
         product.setDescription(productRequestDTO.getDescription());
         product.setPrice(productRequestDTO.getPrice());
         product.setQuantity(productRequestDTO.getQuantity());
         product.setSupplierName(productRequestDTO.getSupplierName());
         product.setSupplierCode(productRequestDTO.getSupplierCode());
         return product;

    }

    public static ProductResponseDTO convertToDTO(Product product){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDesc(product.getDescription());
        productResponseDTO.setProductType(product.getProductType());
        productResponseDTO.setQuantity(product.getQuantity());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setSupplierName(product.getSupplierName());
        productResponseDTO.setSupplierCode(product.getSupplierCode());
        return productResponseDTO;
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
