package ar.lamansys.messages.infrastructure.input.rest;

import ar.lamansys.messages.application.*;
import ar.lamansys.messages.application.exception.*;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.domain.ProductInCartBO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CreateCart createCart;
    private final AddProductToCart addProductToCart;
    private final SetProductInCart setProductInCart;
    private final DeleteProductInCart deleteProductInCart;
    private final GetCartStatus getCartStatus;
    private final CompletePurchase completePurchase;

    @PostMapping("/{sellerId}")
    public ResponseEntity<String> createCart(@PathVariable String sellerId, @RequestBody List<ProductInCartBO> products) throws UserSessionNotExists, UserNotExistsException, ListIsEmptyException, ProductNotExistsException, NotEnoughStockException {
        String cartId = createCart.run(sellerId, products);
        String response = "Carrito creado con éxito, id:" + cartId;
        return ResponseEntity.ok(response);
    }

     @PostMapping("/{cartId}/product")
     public ResponseEntity<String> addProductToCart(@PathVariable String cartId, @RequestBody ProductInCartBO product) throws  UserNotExistsException, UserSessionNotExists, CartNotExistsException, ProductNotExistsException, NotEnoughStockException {
        addProductToCart.run(cartId, product);
        String response = "Producto con id:" +  product.getProductId() + " agregado con éxito al Carrito.";
        return ResponseEntity.ok(response);
     }

    @PutMapping("/{cartId}/product")
    public ResponseEntity<String> modifyQuantity(@PathVariable String cartId, @RequestBody ProductInCartBO product) throws  UserNotExistsException, CartNotExistsException, UserSessionNotExists, ProductInCartNotExistsException, NotEnoughStockException {
        setProductInCart.run(cartId, product.getProductId(), product.getQuantity());
        String response = "Cantidad modificada del Producto con id:" + product.getProductId() + " con éxito.";
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}/product")
    public ResponseEntity<String> deleteProduct(@PathVariable String cartId, @RequestBody ProductInCartBO product) throws UserNotExistsException, CartNotExistsException, UserSessionNotExists, ProductInCartNotExistsException{
        deleteProductInCart.run(cartId, product.getProductId());
        String response = "Producto con id:" + product.getProductId() + " borrado con éxito del Carrito.";
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cartId}/stock-availability")
    public ResponseEntity<String> getStockAvailable(@PathVariable String cartId) throws  UserNotExistsException, CartNotExistsException, UserSessionNotExists, NotEnoughStockException {
        getCartStatus.checkStockAvailability(cartId);
        return ResponseEntity.ok("Todos los productos del carrito tienen stock disponible.");
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<String> getTotalCost(@PathVariable String cartId) throws CartNotExistsException, UserSessionNotExists{
        float total = getCartStatus.getTotalCost(cartId);
        String responseMessage = String.format("El carrito id: %s tiene un costo total de $%.2f", cartId, total);
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping("/{cartId}/complete-purchase")
    public ResponseEntity<String> completePurchase(@PathVariable String cartId) throws UserNotExistsException, CartNotExistsException, UserSessionNotExists, NotEnoughStockException, ProductInCartNotExistsException {
        completePurchase.run(cartId);
        String response = "Compra realizada con éxito del Carrito id:" + cartId;
        return ResponseEntity.ok(response);
    }
}