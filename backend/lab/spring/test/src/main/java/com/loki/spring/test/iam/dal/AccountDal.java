package com.loki.spring.test.iam.dal;

import com.loki.spring.test.iam.model.AccountDAO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Loki
 * @since 2020-05-21
 */
@Mapper
public interface AccountDal extends BaseMapper<AccountDAO> {

}
