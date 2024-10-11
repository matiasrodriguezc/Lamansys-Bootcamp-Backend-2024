package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.entity.ProductInCart;
import ar.lamansys.messages.infrastructure.output.repository.ProductInCartRepository;
import liquibase.pro.packaged.P;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductInCartStorageImpl implements ProductInCartStorage {
    private final ProductInCartRepository productInCartRepository;

    @Override
    public ProductInCart save (ProductInCartBO productInCartBO) {
        ProductInCart productInCart = new ProductInCart(productInCartBO);
        productInCartRepository.save(productInCart);
        return productInCart;
    }

    @Override
    public boolean exists(String cartId, String productInCartId){
        return productInCartRepository.exists(cartId, productInCartId);
    }

    @Override
    @Transactional
    public void setQuantity(String cartId, String productInCartId, int newQuantity){
        productInCartRepository.setQuantity(productInCartId,cartId, newQuantity);
    }

    @Override
    public void delete(String cartId, String productInCartId){
        productInCartRepository.delete(cartId, productInCartId);
    }

    @Override
    public List<ProductInCartBO> findByCartId(String cartId){
        return productInCartRepository.findAllById(cartId);
    }

    @Override
    public void saveAll(List<ProductInCartBO> productInCartBOs) {
        List<ProductInCart> productInCartEntities = productInCartBOs.stream()
                .map(ProductInCart::new)
                .collect(Collectors.toList());
        productInCartRepository.saveAll(productInCartEntities);
    }
}