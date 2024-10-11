package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.WishlistStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RefreshStock {

    private final ProductStorage productStorage;
    private final WishlistStorage wishlistStorage;
    private final NotifyAllUsersStockRefresh notifyAllUsersStockRefresh;
    private final AssertProductIsMine assertProductIsMine;

    public void run(String productId, int restockAmount)throws UserSessionNotExists, UserNotExistsException, ProductNotExistsException {
        assertProductIsMine.run(productId);
        int currentStock = productStorage.findByProductId(productId).getStock();
        currentStock += restockAmount;
        productStorage.setStock(productId, currentStock);
        notifyAllUsersStockRefresh.run(wishlistStorage.findClientByProductIdAndStock(productId, currentStock), productId); //se le informa a todos mis clientes que tenian ese producto en la wishlist que ahora esta disponible,
    }
}