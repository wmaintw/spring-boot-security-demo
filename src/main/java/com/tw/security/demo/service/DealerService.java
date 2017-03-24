package com.tw.security.demo.service;

import com.tw.security.demo.dao.DealerDao;
import com.tw.security.demo.domain.exception.BusinessException;
import com.tw.security.demo.domain.DealerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DealerService {

    @Autowired
    private DealerDao dealerDao;

    public Integer getServiceConsultantStoreId(Integer serviceConsultantId) throws BusinessException {
        Optional<DealerUser> dealerUser = dealerDao.findDealerUser(serviceConsultantId);
        if (!dealerUser.isPresent()) {
            throw new BusinessException("dealer user not found");
        }
        return dealerUser.get().getStoreId();
    }
}
