package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.entity.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, String> {

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(p) > 0 " +
            "FROM ProductInCart p " +
            "WHERE p.cartId = :cartId AND p.productId = :productId")
    boolean exists(@Param("cartId") String cartId, @Param("productId") String productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductInCart p " +
            "WHERE p.cartId = :cartId AND p.productId = :productInCartId")
    void delete(@Param("cartId") String cartId, @Param("productInCartId") String productInCartId);

    @Transactional(readOnly = true)
    @Query("SELECT NEW ar.lamansys.messages.domain.ProductInCartBO(p.productId, p.quantity, p.cartId)" +
            "FROM ProductInCart p " +
            "WHERE p.cartId = :cartId")
    List<ProductInCartBO> findAllById(@Param("cartId") String cartId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductInCart p " +
            "SET p.quantity = :quantity "+
            "WHERE p.productId = :productId AND p.cartId = :cartId")
    void setQuantity(@Param("productId") String productId, @Param("cartId") String cartId, @Param("quantity") int quantity);
}
