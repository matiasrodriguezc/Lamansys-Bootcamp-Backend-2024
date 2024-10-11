package ar.lamansys.messages.infrastructure.output.entity;

import ar.lamansys.messages.domain.ProductInCartBO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wishlist")
@Entity
public class Wishlist {

    @EmbeddedId
    private WishlistId id;

    @Column(name = "quantity")
    private int quantity;

    public Wishlist(ProductInCartBO productInCart, String clientId) {
        this.id = new WishlistId(productInCart.getProductId(), clientId);
        this.quantity = productInCart.getQuantity();
    }
}
