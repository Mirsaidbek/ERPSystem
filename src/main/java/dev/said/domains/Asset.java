package dev.said.domains;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type; // e.g., Laptop, Phone
    private String brand;
    private String model;
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedTo;

    private LocalDate assignedDate;
    private LocalDate returnDate;

    private boolean deleted;
}
