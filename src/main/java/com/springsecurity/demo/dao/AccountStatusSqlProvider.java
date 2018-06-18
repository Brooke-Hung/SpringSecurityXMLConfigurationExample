package com.springsecurity.demo.dao;

import com.springsecurity.demo.entity.AccountStatus;
import org.apache.ibatis.jdbc.SQL;

public class AccountStatusSqlProvider {

    public String insertSelective(AccountStatus record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("account_status");
        
        if (record.getStatusId() != null) {
            sql.VALUES("status_id", "#{statusId,jdbcType=TINYINT}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(AccountStatus record) {
        SQL sql = new SQL();
        sql.UPDATE("account_status");
        
        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("status_id = #{statusId,jdbcType=TINYINT}");
        
        return sql.toString();
    }
}