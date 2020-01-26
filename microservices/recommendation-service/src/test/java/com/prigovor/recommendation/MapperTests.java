package com.prigovor.recommendation;

import com.prigovor.api.core.recommendation.Recommendation;
import com.prigovor.recommendation.persistence.RecommendationEntity;
import com.prigovor.recommendation.services.RecommendationMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTests {

    private RecommendationMapper mapper = Mappers.getMapper(RecommendationMapper.class);

    @Test
    public void mapperTests() {
        assertNotNull(mapper);
        Recommendation api = new Recommendation(1, 2, "a", 4, "C", "adr");
        RecommendationEntity entity = mapper.apiToEntity(api);
        assertEquals(api.getProductId(), entity.getProductId());
        assertEquals(api.getRecommendationId(), entity.getRecommendationId());
        assertEquals(api.getAuthor(), entity.getAuthor());
        assertEquals(api.getRate(), entity.getRating());
        assertEquals(api.getContent(), entity.getContent());
        Recommendation api2 = mapper.entityToApi(entity);
        assertEquals(api.getProductId(), api2.getProductId());
        assertEquals(api.getRecommendationId(), api2.getRecommendationId());
        assertEquals(api.getAuthor(), api2.getAuthor());
        assertEquals(api.getRate(), api2.getRate());
        assertEquals(api.getContent(), api2.getContent());
        assertNull(api2.getServiceAddress());
    }

    @Test
    public void mapperListTests() {
        assertNotNull(mapper);
        Recommendation api = new Recommendation(1, 2, "a", 4, "C", "adr");
        List<Recommendation> apiList = Collections.singletonList(api);
        List<RecommendationEntity> entityList = mapper.apiListToEntityList(apiList);
        assertEquals(apiList.size(), entityList.size());
        RecommendationEntity entity = entityList.get(0);
        assertEquals(api.getProductId(), entity.getProductId());
        assertEquals(api.getRecommendationId(), entity.getRecommendationId());
        assertEquals(api.getAuthor(), entity.getAuthor());
        assertEquals(api.getRate(), entity.getRating());
        assertEquals(api.getContent(), entity.getContent());
        List<Recommendation> api2List = mapper.entityListToApiList(entityList);
        assertEquals(apiList.size(), api2List.size());
        Recommendation api2 = api2List.get(0);
        assertEquals(api.getProductId(), api2.getProductId());
        assertEquals(api.getRecommendationId(), api2.getRecommendationId());
        assertEquals(api.getAuthor(), api2.getAuthor());
        assertEquals(api.getRate(), api2.getRate());
        assertEquals(api.getContent(), api2.getContent());
        assertNull(api2.getServiceAddress());
    }

}