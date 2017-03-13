package com.tw.security.demo.aop.ownershipchecker;

import com.tw.security.demo.aop.TargetResourceId;
import com.tw.security.demo.domain.Rating;
import com.tw.security.demo.domain.User;
import com.tw.security.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.tw.security.demo.domain.UserRole.ROLE_ADMIN;
import static com.tw.security.demo.domain.UserRole.ROLE_DEALER;

@Component("RatingOwnershipChecker")
public class RatingOwnershipChecker implements OwnershipChecker {
    @Autowired
    private RatingService ratingService;

    public boolean isSafeToSkip(User currentUser) {
        return ROLE_DEALER.equals(currentUser.getRole()) || ROLE_ADMIN.equals(currentUser.getRole());
    }

    public void check(User currentUser, TargetResourceId ratingId) {
        Optional<Rating> rating = ratingService.findOne(Integer.valueOf(ratingId.get()));
        if (rating.isPresent()) {
            Rating actualRating = rating.get();

            if (!actualRating.getUserId().equals(currentUser.getId())) {
                throw new AccessDeniedException("Access denied as you are not the owner of the resource.");
            }
        }
    }
}
