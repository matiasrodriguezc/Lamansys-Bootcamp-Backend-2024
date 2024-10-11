package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.CartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.entity.Cart;
import ar.lamansys.messages.infrastructure.output.repository.CartRepository;
import liquibase.pro.packaged.O;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartStorageImpl implements CartStorage {

    private final CartRepository cartRepository;

    @Override
    public Cart save(CartBO cartBO){
        Cart cart = new Cart(cartBO);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public String getCartId(String userId, String sellerId){
        return cartRepository.getId(userId, sellerId);
    }

    @Override
    public boolean exists(String cartId, String userId){
        return cartRepository.exists(cartId, userId);
    }

    @Override
    public String getSellerId(String cartId, String userId){
        return cartRepository.getSellerId(cartId, userId);
    }

    @Override
    public void deleteById(String cartId){
        cartRepository.deleteById(cartId);
    }
}
