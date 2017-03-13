package com.tw.security.demo.controller;

import com.tw.security.demo.aop.annotation.CheckOwnership;
import com.tw.security.demo.aop.annotation.ParamToCheck;
import com.tw.security.demo.domain.Rating;
import com.tw.security.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Secured("ROLE_ADMIN")
    @RequestMapping
    public List<Rating> getAllRatings() {
        return ratingService.findAll();
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_DEALER", "ROLE_ADMIN"})
    @CheckOwnership(value = "RatingOwnershipChecker")
    @RequestMapping("/{ratingId}")
    public Rating getOneRating(@ParamToCheck @PathVariable("ratingId") Integer ratingId) throws Exception {
        Optional<Rating> rating = ratingService.findOne(ratingId);
        if (rating.isPresent()) {
            return rating.get();
        }

        throw new Exception("Rating not found");
    }
}
