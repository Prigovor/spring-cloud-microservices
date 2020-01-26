package com.prigovor.recommendation.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity, String> {

    List<RecommendationEntity> findByProductId(int productId);

}