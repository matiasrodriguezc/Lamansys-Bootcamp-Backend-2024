package ar.lamansys.messages.infrastructure.output.entity;
import ar.lamansys.messages.domain.ProductInCartBO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_in_cart")
@Entity
public class ProductInCart {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "quantity")
    private int quantity;

    public ProductInCart(ProductInCartBO productInCart) {
        this.productId = productInCart.getProductId();
        this.cartId = productInCart.getCartId();
        this.quantity = productInCart.getQuantity();
    }
}
