package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.WishlistStorage;
import ar.lamansys.messages.infrastructure.output.entity.Wishlist;
import ar.lamansys.messages.infrastructure.output.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WishlistStorageImpl implements WishlistStorage {

    private final WishlistRepository wishlistRepository;

    @Override
    public void save(ProductInCartBO productInCart, String userId){
        wishlistRepository.save(new Wishlist(productInCart, userId));
    }

    @Override
    public List<String> findClientByProductIdAndStock(String productId, int stock) {
        List<Wishlist> wishlists = wishlistRepository.findByIdProductId(productId, stock);
        return wishlists.stream()
                .map(wishlist -> wishlist.getId().getClientId())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String clientId, String productId){
        wishlistRepository.deleteById(clientId, productId);
    }
}
