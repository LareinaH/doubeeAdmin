package com.cotton.doubee_admin.service.impl;

import com.cotton.base.service.BaseService;
import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.doubee_admin.model.AdVideo;
import com.cotton.doubee_admin.service.AdVideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017-05-09.
 */
@Service
@Transactional
public class AdVideoServiceImpl  extends BaseServiceImpl<AdVideo> implements AdVideoService {
}
