package com.becoder.config;

import com.becoder.entity.User;
import com.becoder.util.CommonUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        User loggedInUser = CommonUtils.getLoggedInUser();
//        return Optional.of(1);
        return Optional.of(loggedInUser.getId());
    }
}
