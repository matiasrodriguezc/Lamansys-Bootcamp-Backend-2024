package ar.lamansys.messages.infrastructure.output.entity;

import ar.lamansys.messages.domain.CartBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "cart")
@Entity
public class Cart {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "seller_id")
    private String sellerId;

    public Cart(String clientId, String sellerId) {
        this.clientId = clientId;
        this.sellerId = sellerId;
    }

    public Cart(CartBO cart){
        this.id = cart.getClientId() + cart.getSellerId();
        this.clientId = cart.getClientId();
        this.sellerId = cart.getSellerId();
    }
}
