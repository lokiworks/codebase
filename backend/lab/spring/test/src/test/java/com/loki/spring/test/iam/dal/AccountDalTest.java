package com.loki.spring.test.iam.dal;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.truth.Truth;
import com.loki.spring.test.iam.model.AccountDAO;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

/**
 * @author Loki
 * @date 2020/5/21 18:50
 */


@ActiveProfiles("unt")
@MybatisTest
@Rollback
// use a real database
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountDalTest {
    @Resource
    AccountDal accountDal;

    @Test
    public void insert_insertAndSelect() {
        // given
        {
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.setName("n1");
            accountDal.insert(accountDAO);
        }
        // when
        Wrapper<AccountDAO> queryWrapper = new QueryWrapper<AccountDAO>().lambda().eq(AccountDAO::getName, "n1").select(AccountDAO::getName);
        AccountDAO actual = accountDal.selectOne(queryWrapper);


        Truth.assertThat(actual).isNotNull();
        Truth.assertThat(actual.getName()).isEqualTo("n1");

    }
}