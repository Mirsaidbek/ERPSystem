package dev.said.repository;

import dev.said.domains.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("select d from Document d where d.createdBy = ?1")
    List<Document> findAllByCreatedBy(Long id);

}
