package com.catalogoprodutos.controller;

import java.util.List;
import java.util.Optional;

import com.catalogoprodutos.dto.ProductDTO;
import com.catalogoprodutos.model.Erro;
import com.catalogoprodutos.model.Product;
import com.catalogoprodutos.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @ApiOperation("Retorna uma lista de produtos")
    @GetMapping
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }
    
    @ApiOperation(value = "Adiciona um novo produto")
    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        try {
            if (!verify(product)) {
                throw new Exception();
            }
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new Erro(400));
        }

    }
    @ApiOperation(value = "Modifica dados de um produto")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        Optional<Product> optional = productRepository.findById(id);
        try {

            if (optional.isPresent()) {
                Product product = optional.get();
                Product productUpdated = productDTO.update(product);
                if (!verify(product)) {
                    throw new Exception();
                }
                productRepository.save(productUpdated);
                return ResponseEntity.ok(productUpdated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Erro(400));
        }

    }

    @ApiOperation(value = "Deleta um produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Busca um produto pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<Product> searchProduct(@PathVariable String id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    public boolean verify(Product product) {
        if (product.getPrice().compareTo(Double.valueOf("0.00")) < 0) {
            return false;
        }
        return true;
    }

    @ApiOperation(value = "Busca um produto com filtro")
    @GetMapping("/search")
    public List<Product> search(@RequestParam(defaultValue = "0.00") Double min_value,
            @RequestParam(defaultValue = "9999999.99") Double max_value, @RequestParam(required = false) String q) {
        List<Product> products;

        if (q == null) {
            products = productRepository.findByValue(min_value, max_value);
        } else {
            products = productRepository.findByValue(min_value, max_value, q);
        }
        return products;
    }

}
