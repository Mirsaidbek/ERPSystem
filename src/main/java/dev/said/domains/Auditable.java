package dev.said.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;
import java.time.LocalDateTime;

//@Getter
//@Setter
//@NoArgsConstructor
@ToString
//@Builder
@MappedSuperclass
public class Auditable<ID extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime updatedAt;
    @CreatedBy
    private ID createdBy;
    @LastModifiedBy
    private ID updatedBy;

    @Column(columnDefinition = "boolean default 'f'")
    private boolean deleted;

    public Auditable() {
    }

    public Auditable(ID id, LocalDateTime createdAt, LocalDateTime updatedAt, ID createdBy, ID updatedBy, boolean deleted) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ID createdBy) {
        this.createdBy = createdBy;
    }

    public ID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(ID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
