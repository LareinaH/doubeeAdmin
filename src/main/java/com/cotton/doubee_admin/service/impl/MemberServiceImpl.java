package com.cotton.doubee_admin.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.doubee_admin.model.Member;

import com.cotton.doubee_admin.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017-05-10.
 */

@Service
@Transactional
public class MemberServiceImpl extends BaseServiceImpl<Member> implements MemberService {

    @Override
    public Member getByProviderId(long providerId) {

        Member model = new Member();
        model.setbProvider(1);
        model.setProviderId(providerId);

        return selectOne(model);
    }
}