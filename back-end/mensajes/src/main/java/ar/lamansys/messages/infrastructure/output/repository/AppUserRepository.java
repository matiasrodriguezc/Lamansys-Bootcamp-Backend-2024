package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.infrastructure.output.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    @Query("SELECT u.username FROM AppUser u WHERE u.id = :contactId")
    String getUsername(@Param("contactId") String contactId);
}
