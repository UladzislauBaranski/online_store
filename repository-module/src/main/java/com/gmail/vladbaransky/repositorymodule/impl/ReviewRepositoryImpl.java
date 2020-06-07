package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.ReviewRepository;
import com.gmail.vladbaransky.repositorymodule.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends GenericDaoRepositoryImpl<Long, Review> implements ReviewRepository {

}
