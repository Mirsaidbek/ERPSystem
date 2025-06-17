package dev.said.service.interfaces;

import dev.said.dto.asset.AssetCreateDto;
import dev.said.dto.asset.AssetDto;
import dev.said.enums.AssetStatus;

import java.util.List;

public interface AssetServiceI {
    AssetDto create(AssetCreateDto dto);

    AssetDto assignToUser(Long assetId, Long userId);

    AssetDto returnAsset(Long assetId);

    List<AssetDto> listAll();

    AssetDto updateStatus(Long assetId, AssetStatus status);
}
