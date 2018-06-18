package com.springsecurity.demo.dao;

import com.springsecurity.demo.entity.Account;
import com.springsecurity.demo.entity.AccountCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AccountDao {
    @SelectProvider(type=AccountSqlProvider.class, method="countByExample")
    long countByExample(AccountCriteria example);

    @DeleteProvider(type=AccountSqlProvider.class, method="deleteByExample")
    int deleteByExample(AccountCriteria example);

    @Delete({
        "delete from account",
        "where account_id = #{accountId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer accountId);

    @Insert({
        "insert into account (user_name, password, ",
        "first_name, last_name, ",
        "email, cellphone_no, ",
        "status, created_time, ",
        "last_updated)",
        "values (#{userName,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{firstName,jdbcType=VARCHAR}, #{lastName,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{cellphoneNo,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=TINYINT}, #{createdTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdated,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="accountId", before=false, resultType=Integer.class)
    int insert(Account record);

    @InsertProvider(type=AccountSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="accountId", before=false, resultType=Integer.class)
    int insertSelective(Account record);

    @SelectProvider(type=AccountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="account_id", property="accountId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="first_name", property="firstName", jdbcType=JdbcType.VARCHAR),
        @Result(column="last_name", property="lastName", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="cellphone_no", property="cellphoneNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_updated", property="lastUpdated", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Account> selectByExample(AccountCriteria example);

    @Select({
        "select",
        "account_id, user_name, password, first_name, last_name, email, cellphone_no, ",
        "status, created_time, last_updated",
        "from account",
        "where account_id = #{accountId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="account_id", property="accountId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="first_name", property="firstName", jdbcType=JdbcType.VARCHAR),
        @Result(column="last_name", property="lastName", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="cellphone_no", property="cellphoneNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_updated", property="lastUpdated", jdbcType=JdbcType.TIMESTAMP)
    })
    Account selectByPrimaryKey(Integer accountId);

    @UpdateProvider(type=AccountSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountCriteria example);

    @UpdateProvider(type=AccountSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Account record, @Param("example") AccountCriteria example);

    @UpdateProvider(type=AccountSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Account record);

    @Update({
        "update account",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "first_name = #{firstName,jdbcType=VARCHAR},",
          "last_name = #{lastName,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "cellphone_no = #{cellphoneNo,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "created_time = #{createdTime,jdbcType=TIMESTAMP},",
          "last_updated = #{lastUpdated,jdbcType=TIMESTAMP}",
        "where account_id = #{accountId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Account record);
}