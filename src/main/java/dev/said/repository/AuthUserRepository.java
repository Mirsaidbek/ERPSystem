package dev.said.repository;

import dev.said.domains.AuthUser;
import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    @Query("select a from AuthUser a where upper(a.username) = upper(?1)")
    Optional<AuthUser> findByUsername(String username);

    @Query("select a.id from AuthUser a where a.username = ?1")
    Long findAuthIdByUsername(String username);

    @Transactional
    @Modifying
    @Query("update AuthUser a set a.language = coalesce(?2,a.language), a.active = coalesce(?3,a.active), a.role = coalesce(?4,a.role) where a.username = ?1")
    void updateAuthUser(String username, Language language, Active active, Role role);
}
