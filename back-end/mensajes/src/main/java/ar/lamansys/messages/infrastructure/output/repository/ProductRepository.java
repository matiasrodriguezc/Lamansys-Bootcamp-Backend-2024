package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.domain.ProductBO;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<ProductBO> findBySellerId(@Param("sellerId") String sellerId);

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(p) > 0 " +
            "FROM Product p " +
            "WHERE p.sellerId = :sellerId AND p.id = :productId")
    boolean exists(@Param("productId") String productId, @Param("sellerId") String sellerId);


    @Transactional(readOnly = true)
    @Query("SELECT COUNT(p) > 0 " +
            "FROM Product p " +
            "WHERE p.sellerId = :sellerId AND p.id = :productId AND p.stock >= :stock")
    boolean enough(@Param("productId") String productId, @Param("stock") int stock, @Param("sellerId") String sellerId);


    @Transactional(readOnly = true)
    @Query("SELECT NEW ar.lamansys.messages.domain.ProductBO(p) " +
            "FROM Product p " +
            "WHERE p.id = :productId")
    ProductBO findByProductId(@Param("productId") String productId);

    @Transactional
    @Modifying
    @Query("UPDATE Product p " +
            "SET p.stock = :newQuantity " +
            "WHERE p.id = :productId")
    void setQuantity(@Param("productId") String productId, @Param("newQuantity") int newQuantity);
}