package dev.said.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuthUser extends Auditable<Long> {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.ORDINAL)
    private Active active;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder(builderMethodName = "childBuilder")
    public AuthUser(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy, boolean deleted, String username, String password, Language language, Role role, Active active) {
        super(id, createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.username = username;
        this.password = password;
        this.language = language;
        this.role = role;
        this.active = active;
    }

}