package ar.lamansys.messages.application;

import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdateProductStock {
    private final ProductInCartStorage productInCartStorage;
    private final ProductStorage productStorage;

    public void run(String cartId) {
        productInCartStorage.findByCartId(cartId).forEach(productInCartBO -> {
            int newQuantity = productStorage.findByProductId(productInCartBO.getProductId()).getStock() - productInCartBO.getQuantity();
            productStorage.setStock(productInCartBO.getProductId(), newQuantity);
        });
    }
}