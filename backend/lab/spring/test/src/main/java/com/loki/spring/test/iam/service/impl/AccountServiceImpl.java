package com.loki.spring.test.iam.service.impl;

import com.loki.spring.test.iam.model.AccountDAO;
import com.loki.spring.test.iam.dal.AccountDal;
import com.loki.spring.test.iam.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Loki
 * @since 2020-05-21
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDal, AccountDAO> implements IAccountService {

}
