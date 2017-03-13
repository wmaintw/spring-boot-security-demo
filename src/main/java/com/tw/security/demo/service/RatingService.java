package com.tw.security.demo.service;

import com.tw.security.demo.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private List<Rating> allRatings = new ArrayList<>();

    public RatingService() {
        allRatings.add(new Rating(1, 5, "good", 1, 1));
        allRatings.add(new Rating(1, 3, "normal", 1, 2));
        allRatings.add(new Rating(3, 2, "poor", 2, 3));
    }

    public List<Rating> findAll() {
        return allRatings;
    }

    public Optional<Rating> findOne(Integer ratingId) {
        return allRatings.stream().filter(rating -> rating.getId().equals(ratingId)).findFirst();
    }
}
