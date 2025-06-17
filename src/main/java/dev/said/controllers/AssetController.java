package dev.said.controllers;

import dev.said.dto.asset.AssetCreateDto;
import dev.said.dto.asset.AssetDto;
import dev.said.enums.AssetStatus;
import dev.said.service.interfaces.AssetServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetServiceI assetService;

    @PostMapping
    public ResponseEntity<AssetDto> create(@RequestBody AssetCreateDto dto) {
        AssetDto created = assetService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/assign/{id}")
    public ResponseEntity<AssetDto> assign(@PathVariable Long id, @RequestParam Long userId) {
        AssetDto assigned = assetService.assignToUser(id, userId);
        return ResponseEntity.ok(assigned);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<AssetDto> returnAsset(@PathVariable Long id) {
        AssetDto returned = assetService.returnAsset(id);
        return ResponseEntity.ok(returned);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<AssetDto> updateStatus(@PathVariable Long id, @RequestParam AssetStatus status) {
        AssetDto updated = assetService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("list-all")
    public ResponseEntity<List<AssetDto>> listAll() {
        List<AssetDto> assets = assetService.listAll();
        return ResponseEntity.ok(assets);
    }
}
