package com.tw.security.demo.aop.ownershipchecker;

import com.tw.security.demo.aop.TargetResourceId;
import com.tw.security.demo.domain.Order;
import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.exception.BusinessException;
import com.tw.security.demo.service.DealerService;
import com.tw.security.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.tw.security.demo.domain.UserRole.ROLE_ADMIN;
import static com.tw.security.demo.domain.UserRole.ROLE_CUSTOMER;
import static com.tw.security.demo.domain.UserRole.ROLE_SERVICE_CONSULTANT;

@Component("OrderOwnershipChecker")
public class OrderOwnershipChecker implements OwnershipChecker {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DealerService dealerService;

    public boolean  isSafeToSkip(User currentUser) {
        return ROLE_ADMIN.equals(currentUser.getRole());
    }

    public void check(User currentUser, TargetResourceId idOfTargetResource) {
        Optional<Order> order = orderService.findOne(Integer.valueOf(idOfTargetResource.get()));
        if (order.isPresent()) {
            Order actualOrder = order.get();

            if (ROLE_SERVICE_CONSULTANT.equals(currentUser.getRole())) {
                Integer serviceConsultantStoreId = getTheStoreCurrentUserBelongsTo(currentUser);
                if (actualOrder.getStoreId().intValue() != serviceConsultantStoreId.intValue()) {
                    throw new AccessDeniedException("Access denied as you are not in charge of the resource.");
                }

                return;
            }

            if (ROLE_CUSTOMER.equals(currentUser.getRole()) ) {
                if (!actualOrder.getOwnerId().equals(currentUser.getId())) {
                    throw new AccessDeniedException("Access denied as you are not the owner of the resource.");
                }

                return;
            }
        }
    }

    private Integer getTheStoreCurrentUserBelongsTo(User serviceConsultant) {
        Integer serviceConsultantStoreId = -1;

        try {
            serviceConsultantStoreId = dealerService.getServiceConsultantStoreId(serviceConsultant.getId());
        } catch (BusinessException ignored) {
        }

        return serviceConsultantStoreId;
    }
}
