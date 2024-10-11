package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetCartStatus {
    private final AssertCartExists assertCartExists;
    private final CartStorage cartStorage;
    private final GetUserSession getUserSession;
    private final ProductInCartStorage productInCartStorage;
    private final AssertStockExists assertStockExists;
    private final ProductStorage productStorage;

    public void checkStockAvailability(String cartId) throws UserNotExistsException,UserSessionNotExists, CartNotExistsException, NotEnoughStockException {
        String userId = getUserSession.run();
        assertCartExists.run(cartId, userId);
        List<ProductInCartBO> products = productInCartStorage.findByCartId(cartId);
        assertStockExists.run(products, cartStorage.getSellerId(cartId, userId));
    }

    public Float getUnitPrice(String productId) {
        return productStorage.findByProductId(productId).getPrice();
    }

    public Float getTotalCost(String cartId) throws UserSessionNotExists, CartNotExistsException {
        assertCartExists.run(cartId, getUserSession.run());
        float totalCost = 0;
        for (ProductInCartBO productInCartBO : productInCartStorage.findByCartId(cartId)){
            float unitPrice = productStorage.findByProductId(productInCartBO.getProductId()).getPrice();
            totalCost += unitPrice*productInCartBO.getQuantity();
        }
        return totalCost;
    }

}