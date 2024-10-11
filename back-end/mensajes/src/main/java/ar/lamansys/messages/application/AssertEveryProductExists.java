package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.domain.ProductInCartBO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AssertEveryProductExists {
    private final AssertProductExists assertProductExists;
    private final AssertStockExists assertStockExists;

    public void run(List<ProductInCartBO> products, String sellerId) throws UserNotExistsException, UserSessionNotExists, ProductNotExistsException, NotEnoughStockException {
        for (ProductInCartBO product : products) {
            String productId = product.getProductId();
            assertProductExists.run(productId, sellerId);
            List<ProductInCartBO> productInCartBOList = new ArrayList<>();
            productInCartBOList.add(product);
            assertStockExists.run(productInCartBOList, sellerId);
        }
    }
}

