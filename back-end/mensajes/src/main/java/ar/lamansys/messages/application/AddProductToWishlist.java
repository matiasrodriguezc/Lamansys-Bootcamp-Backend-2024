package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.WishlistStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AddProductToWishlist {

    private final WishlistStorage wishlistStorage;
    private final NotifyUserCartChanges notifyUserCartChanges;
    private final ProductStorage productStorage;

    public void run(ProductInCartBO product, String clientId)throws UserNotExistsException, UserSessionNotExists {
        wishlistStorage.save(product, clientId);
        String text = "El producto con id: " + product.getProductId() + " se ha agregado a la Lista de Deseos.\n" + "Se le informará cuando el stock sea suficiente para que proceda con la compra.";
        String sellerId = productStorage.findByProductId(product.getProductId()).getSellerId();
        notifyUserCartChanges.run(text, sellerId);
    }
}
