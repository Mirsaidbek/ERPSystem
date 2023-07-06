package dev.said.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class EnterOut extends Auditable<Long> {

    @Column(nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime enteredAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime leftAt;

    @Builder(builderMethodName = "childBuilder")
    public EnterOut(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy, boolean deleted, Long userId, LocalDateTime enteredAt, LocalDateTime leftAt) {
        super(id, createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.userId = userId;
        this.enteredAt = enteredAt;
        this.leftAt = leftAt;
    }
}
