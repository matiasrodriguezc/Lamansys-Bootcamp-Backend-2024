package ar.lamansys.messages.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInCartBO {
    private String productId;
    private int quantity;
    private String cartId;
    
    public ProductInCartBO(String cartId, String productId) {
        this.productId = productId;
        this.cartId = cartId;
    }

    public ProductInCartBO(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
