package com.shopify.monitor.shopifymonitor.mappings;

import com.shopify.monitor.shopifymonitor.api.vo.VariantVO;
import com.shopify.monitor.shopifymonitor.persistance.model.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopifyVariantMapper {
    List<Variant> map(List<VariantVO> variants);
}
