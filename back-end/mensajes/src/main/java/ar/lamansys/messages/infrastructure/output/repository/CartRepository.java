package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.infrastructure.output.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @Transactional(readOnly = true)
    @Query("SELECT c.id FROM Cart c WHERE c.clientId = :userId AND c.sellerId = :sellerId")
    String getId(@Param("userId") String userId, @Param("sellerId") String sellerId);

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(c) > 0 " +
            "FROM Cart c " +
            "WHERE c.id = :cartId AND c.clientId = :userId")
    boolean exists(@Param("cartId") String cartId, @Param("userId") String userId);

    @Transactional(readOnly = true)
    @Query("SELECT c.sellerId " +
            "FROM Cart c " +
            "WHERE c.id = :cartId AND c.clientId = :userId")
    String getSellerId(@Param("cartId") String cartId, @Param("userId") String userId);
}