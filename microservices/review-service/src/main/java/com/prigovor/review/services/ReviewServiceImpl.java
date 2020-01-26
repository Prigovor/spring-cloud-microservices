package com.prigovor.review.services;

import com.prigovor.api.core.review.Review;
import com.prigovor.api.core.review.ReviewService;
import com.prigovor.review.persistence.ReviewEntity;
import com.prigovor.review.persistence.ReviewRepository;
import com.prigovor.util.exceptions.InvalidInputException;
import com.prigovor.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewMapper mapper;
    private final ServiceUtil serviceUtil;
    private final ReviewRepository repository;

    @Override
    public Review createReview(final Review body) {
        try {
            final ReviewEntity entity = mapper.apiToEntity(body);
            final ReviewEntity newEntity = repository.save(entity);
            LOG.debug("createReview: created a review entity: {}/{}", body.getProductId(), body.getReviewId());
            return mapper.entityToApi(newEntity);
        } catch (DataIntegrityViolationException dive) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Review Id:" + body.getReviewId());
        }
    }

    @Override
    public List<Review> getReviews(final int productId) {
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);
        final List<ReviewEntity> entityList = repository.findByProductId(productId);
        final List<Review> list = mapper.entityListToApiList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));
        LOG.debug("getReviews: response size: {}", list.size());
        return list;
    }

    @Override
    public void deleteReviews(int productId) {
        LOG.debug("deleteReviews: tries to delete reviews for the product with productId: {}", productId);
        repository.deleteAll(repository.findByProductId(productId));
    }

    /* Injection */
    public ReviewServiceImpl(final ReviewMapper mapper,
                             final ServiceUtil serviceUtil,
                             final ReviewRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
        this.serviceUtil = serviceUtil;
    }

}