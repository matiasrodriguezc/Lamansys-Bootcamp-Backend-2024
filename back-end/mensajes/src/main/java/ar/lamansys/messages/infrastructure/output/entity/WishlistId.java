package ar.lamansys.messages.infrastructure.output.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class WishlistId implements Serializable {

    @Column(name = "product_id")
    private String productId;

    @Column(name = "client_id")
    private String clientId;
}

