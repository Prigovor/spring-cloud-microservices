package com.prigovor.recommendation.services;

import com.prigovor.api.core.recommendation.Recommendation;
import com.prigovor.recommendation.persistence.RecommendationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true),
            @Mapping(target = "rate", source = "entity.rating")
    })
    Recommendation entityToApi(RecommendationEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "rating", source = "api.rate")
    })
    RecommendationEntity apiToEntity(Recommendation api);

    List<RecommendationEntity> apiListToEntityList(List<Recommendation> api);

    List<Recommendation> entityListToApiList(List<RecommendationEntity> entity);

}