package ar.lamansys.messages.infrastructure.input.rest;

import ar.lamansys.messages.application.FetchUserStock;
import ar.lamansys.messages.application.RefreshStock;
import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.domain.ProductBO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/stock")
public class StockController {

    private final FetchUserStock fetchUserStock;
    private final RefreshStock refreshStock;

    @GetMapping("/{userId}")
    public ResponseEntity<String> fetchUserStock(@PathVariable String userId) throws UserNotExistsException {
        StringBuilder jsonResponse = new StringBuilder("Los productos en venta del usuario " + userId + " son:\n");
        List<ProductBO> products = fetchUserStock.run(userId);
        for (ProductBO product : products) {
            jsonResponse.append(product.getName())
                    .append(", precio: ").append(product.getPrice())
                    .append(", stock: ").append(product.getStock())
                    .append("\n");
        }
        return ResponseEntity.ok(jsonResponse.toString());
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable String productId, @RequestBody int quantity) throws UserSessionNotExists, UserNotExistsException, ProductNotExistsException {
        refreshStock.run(productId, quantity);
        String response = "Stock del Producto con id:" + productId + " renovado con éxito.";
        return ResponseEntity.ok(response);
    }
}