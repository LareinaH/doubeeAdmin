package com.cotton.doubee_admin.service;

import com.cotton.base.service.BaseService;
import com.cotton.doubee_admin.model.Member;

/**
 * Created by Administrator on 2017-05-10.
 */

public interface MemberService extends BaseService<Member> {


     Member getByProviderId(long providerId);
}