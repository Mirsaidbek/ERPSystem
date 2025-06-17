package dev.said.dto.asset;

import dev.said.enums.AssetStatus;

import java.time.LocalDate;

public class AssetDto {
    private Long id;
    private String name;
    private String type;
    private String brand;
    private String model;
    private LocalDate purchaseDate;
    private AssetStatus status;
    private Long assignedToUserId;
    private LocalDate assignedDate;
    private LocalDate returnDate;
}
