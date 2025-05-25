package com.tapajyoti.controller;


import com.tapajyoti.dto.ApiResponse;
import com.tapajyoti.dto.ProductRequestDTO;
import com.tapajyoti.dto.ProductResponseDTO;
import com.tapajyoti.service.ProductService;
import com.tapajyoti.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    public static final String SUCCESS = "Success";

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProducts(@Valid @RequestBody ProductRequestDTO productRequestDTO) {

        ProductResponseDTO productResponseDTO = productService.createNewProduct(productRequestDTO);

        ApiResponse<ProductResponseDTO> responseDTO = new ApiResponse<>();

        responseDTO.setStatus(SUCCESS);
        responseDTO.setResults(productResponseDTO);

        log.info("ProductController::createNewProduct response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<ApiResponse> getAllProducts() {

        List<ProductResponseDTO> productResponseDTOList = productService.getAllProducts();

        ApiResponse<List<ProductResponseDTO>> responseDto = new ApiResponse<>();
        responseDto.setStatus(SUCCESS);
        responseDto.setTotalCount(productResponseDTOList.size());
        responseDto.setResults(productResponseDTOList);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {

       ProductResponseDTO productResponse = productService.getProductById(id);

        ApiResponse<ProductResponseDTO> responseDto = new ApiResponse<>();
        responseDto.setStatus(SUCCESS);
        responseDto.setResults(productResponse);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
