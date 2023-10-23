package com.shopify.monitor.shopifymonitor.mappings;

import com.shopify.monitor.shopifymonitor.api.vo.ProductVO;
import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopifyProductMapper {

    @Mapping(target = "siteName", source = "siteName")
    Product mapProduct(ProductVO product, String siteName);
    List<Product> map(List<ProductVO> products, @Context String siteName);

    default Product mapContext(ProductVO productVO, @Context String siteName) {
        return mapProduct(productVO, siteName);
    }
}
