package ar.lamansys.messages.infrastructure.input.rest;

import ar.lamansys.messages.application.GetCartStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final GetCartStatus getCartStatus;

    @GetMapping("/{productId}/price")
    public ResponseEntity<String> getUnitPrice(@PathVariable String productId) {
        float price = getCartStatus.getUnitPrice(productId);
        String responseMessage = String.format("El producto id: %s tiene un precio de $%.2f", productId, price);
        return ResponseEntity.ok(responseMessage);
    }
}
