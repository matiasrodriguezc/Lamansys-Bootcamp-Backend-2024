package ar.lamansys.messages.infrastructure.output.entity;

import ar.lamansys.messages.domain.ProductBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private int stock;

    @Column(name = "price")
    private float price;

    public Product(ProductBO productBO){
        sellerId = productBO.getSellerId();
        name = productBO.getName();
        stock = productBO.getStock();
        price = productBO.getPrice();
    }

}
