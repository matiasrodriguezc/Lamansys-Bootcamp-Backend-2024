package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AssertStockExists {
    private final ProductStorage productStorage;
    private final AddProductToWishlist addProductToWishlist;
    private final GetUserSession getUserSession;

    public void run(List<ProductInCartBO> products, String sellerId) throws UserNotExistsException, UserSessionNotExists {
        String userId = getUserSession.run();
        for (ProductInCartBO product : products) {
            String productId = product.getProductId();
            if (!productStorage.enough(productId, product.getQuantity(), sellerId)) {
                addProductToWishlist.run(product, userId);
                throw new NotEnoughStockException(productId);
            }
        }
    }


}
