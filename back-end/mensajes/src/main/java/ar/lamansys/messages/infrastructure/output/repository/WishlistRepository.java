package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.infrastructure.output.entity.Wishlist;
import ar.lamansys.messages.infrastructure.output.entity.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {

    @Transactional(readOnly = true)
    @Query("SELECT w "+
            "FROM Wishlist w "+
            "WHERE w.id.productId = :productId AND w.quantity <= :stock")
    List<Wishlist> findByIdProductId(@Param("productId") String productId, @Param("stock") int stock);


    @Transactional
    @Modifying
    @Query("DELETE FROM Wishlist w " +
            "WHERE w.id.clientId = :clientId AND w.id.productId = :productId")
    void deleteById(@Param("clientId") String clientId, @Param("productId") String productId);
}
