package com.tapajyoti.service;

import com.tapajyoti.dto.ProductRequestDTO;
import com.tapajyoti.dto.ProductResponseDTO;
import com.tapajyoti.entity.Product;
import com.tapajyoti.exception.ProductNotFoundException;
import com.tapajyoti.exception.ProductServiceBusinessException;
import com.tapajyoti.repository.ProductRepository;
import com.tapajyoti.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDTO createNewProduct(@Valid ProductRequestDTO productRequestDTO) throws ProductServiceBusinessException {

        ProductResponseDTO productResponseDTO;

        try {
            log.info("Product service createNewProduct method started execution...");
            Product product = ValueMapper.convertToEntity(productRequestDTO);
            log.debug("ProductService:createNewProduct request parameters {}", ValueMapper.jsonAsString(productRequestDTO));
            Product productResults = productRepository.save(product);
            productResponseDTO = ValueMapper.convertToDTO(productResults);
            log.debug("ProductService:createNewProduct received response from Database {}", ValueMapper.jsonAsString(productRequestDTO));

        } catch (Exception ex) {
            log.info("Exception occurred while persisting product to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while create a new product");

        }
        log.info("ProductService:createNewProduct execution ended.");
        return productResponseDTO;

    }

    @Cacheable(value = "product")
    public List<ProductResponseDTO> getAllProducts() throws ProductServiceBusinessException {
        List<ProductResponseDTO> productResponseDTOS = null;

        try {
            log.info("productService:getAllProduct started execution.");
            List<Product> productList = productRepository.findAll();
            if (!productList.isEmpty()) {
                productResponseDTOS = productList
                        .stream()
                        .map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                productResponseDTOS = Collections.emptyList();
            }
            log.debug("ProductService:getProducts retrieving products from database  {}", ValueMapper.jsonAsString(productResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving products from database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch all products from Database");
        }
        log.info("ProductService:getProducts execution ended.");
        return productResponseDTOS;
    }

    @Cacheable(value = "product")
    public ProductResponseDTO getProductById(Long productId) {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("productService:getProductById started execution..");

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

            productResponseDTO = ValueMapper.convertToDTO(product);

            log.debug("ProductService:getProductById retrieving product from database for id {} {}", productId, ValueMapper.jsonAsString(productResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", productId, ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database " + productId);
        }
        log.info("ProductService:getProductById execution ended.");
        return productResponseDTO;
    }

}
