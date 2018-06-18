package com.springsecurity.demo.dao;

import com.springsecurity.demo.entity.AccountRole;
import com.springsecurity.demo.entity.AccountRoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AccountRoleDao {
    @SelectProvider(type=AccountRoleSqlProvider.class, method="countByExample")
    long countByExample(AccountRoleCriteria example);

    @DeleteProvider(type=AccountRoleSqlProvider.class, method="deleteByExample")
    int deleteByExample(AccountRoleCriteria example);

    @Delete({
        "delete from account_role",
        "where account_id = #{accountId,jdbcType=INTEGER}",
          "and role_id = #{roleId,jdbcType=TINYINT}"
    })
    int deleteByPrimaryKey(@Param("accountId") Integer accountId, @Param("roleId") Byte roleId);

    @Insert({
        "insert into account_role (account_id, role_id)",
        "values (#{accountId,jdbcType=INTEGER}, #{roleId,jdbcType=TINYINT})"
    })
    int insert(AccountRole record);

    @InsertProvider(type=AccountRoleSqlProvider.class, method="insertSelective")
    int insertSelective(AccountRole record);

    @SelectProvider(type=AccountRoleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="account_id", property="accountId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.TINYINT, id=true)
    })
    List<AccountRole> selectByExample(AccountRoleCriteria example);

    @UpdateProvider(type=AccountRoleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AccountRole record, @Param("example") AccountRoleCriteria example);

    @UpdateProvider(type=AccountRoleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AccountRole record, @Param("example") AccountRoleCriteria example);
}