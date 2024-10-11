package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.ProductBO;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductStorageImpl implements ProductStorage {

    private final ProductRepository productRepository;

    @Override
    public List<ProductBO> findBySeller(String sellerId){
        return productRepository.findBySellerId(sellerId);
    }

    @Override
    public boolean exists(String productId, String sellerId){
        return productRepository.exists(productId, sellerId);
    }

    @Override
    public boolean enough(String productId, int quantity, String sellerId){
        return productRepository.enough(productId, quantity, sellerId);
    }

    @Override
    public ProductBO findByProductId(String productId){
        return productRepository.findByProductId(productId);
    }

    @Override
    public void setStock(String productId, int newQuantity){
        productRepository.setQuantity(productId, newQuantity);
    }
}