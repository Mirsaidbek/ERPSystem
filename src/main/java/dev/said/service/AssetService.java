package dev.said.service;

import dev.said.dto.asset.AssetCreateDto;
import dev.said.dto.asset.AssetDto;
import dev.said.enums.AssetStatus;
import dev.said.service.interfaces.AssetServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService implements AssetServiceI {
    @Override
    public AssetDto create(AssetCreateDto dto) {
        return null;
    }

    @Override
    public AssetDto assignToUser(Long assetId, Long userId) {
        return null;
    }

    @Override
    public AssetDto returnAsset(Long assetId) {
        return null;
    }

    @Override
    public List<AssetDto> listAll() {
        return List.of();
    }

    @Override
    public AssetDto updateStatus(Long assetId, AssetStatus status) {
        return null;
    }
}
