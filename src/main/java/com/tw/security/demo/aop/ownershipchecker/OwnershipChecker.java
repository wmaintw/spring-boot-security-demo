package com.tw.security.demo.aop.ownershipchecker;

import com.tw.security.demo.aop.TargetResourceId;
import com.tw.security.demo.domain.User;

public interface OwnershipChecker {
    boolean isSafeToSkip(User currentUser);

    void check(User user, TargetResourceId targetResourceId);
}
