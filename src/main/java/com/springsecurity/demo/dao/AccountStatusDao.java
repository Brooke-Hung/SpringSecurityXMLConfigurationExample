package com.springsecurity.demo.dao;

import com.springsecurity.demo.entity.AccountStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AccountStatusDao {
    @Delete({
        "delete from account_status",
        "where status_id = #{statusId,jdbcType=TINYINT}"
    })
    int deleteByPrimaryKey(Byte statusId);

    @Insert({
        "insert into account_status (status_id, code, ",
        "description)",
        "values (#{statusId,jdbcType=TINYINT}, #{code,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR})"
    })
    int insert(AccountStatus record);

    @InsertProvider(type=AccountStatusSqlProvider.class, method="insertSelective")
    int insertSelective(AccountStatus record);

    @Select({
        "select",
        "status_id, code, description",
        "from account_status",
        "where status_id = #{statusId,jdbcType=TINYINT}"
    })
    @Results({
        @Result(column="status_id", property="statusId", jdbcType=JdbcType.TINYINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR)
    })
    AccountStatus selectByPrimaryKey(Byte statusId);

    @UpdateProvider(type=AccountStatusSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AccountStatus record);

    @Update({
        "update account_status",
        "set code = #{code,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR}",
        "where status_id = #{statusId,jdbcType=TINYINT}"
    })
    int updateByPrimaryKey(AccountStatus record);
}