package ar.lamansys.messages.domain;

import ar.lamansys.messages.infrastructure.output.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductBO {
    private String id;
    private String sellerId;
    private String name;
    private int stock;
    private float price;

    public ProductBO(Product product) {
        this.id = product.getId();
        this.sellerId = product.getSellerId();
        this.name = product.getName();
        this.stock = product.getStock();
        this.price = product.getPrice();
    }
}
