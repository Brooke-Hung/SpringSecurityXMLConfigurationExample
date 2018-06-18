package com.springsecurity.demo.dao;

import com.springsecurity.demo.entity.RoleAuthority;
import com.springsecurity.demo.entity.RoleAuthorityCriteria;
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

public interface RoleAuthorityDao {
    @SelectProvider(type=RoleAuthoritySqlProvider.class, method="countByExample")
    long countByExample(RoleAuthorityCriteria example);

    @DeleteProvider(type=RoleAuthoritySqlProvider.class, method="deleteByExample")
    int deleteByExample(RoleAuthorityCriteria example);

    @Delete({
        "delete from role_authority",
        "where role_id = #{roleId,jdbcType=TINYINT}",
          "and authority_id = #{authorityId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(@Param("roleId") Byte roleId, @Param("authorityId") Integer authorityId);

    @Insert({
        "insert into role_authority (role_id, authority_id)",
        "values (#{roleId,jdbcType=TINYINT}, #{authorityId,jdbcType=INTEGER})"
    })
    int insert(RoleAuthority record);

    @InsertProvider(type=RoleAuthoritySqlProvider.class, method="insertSelective")
    int insertSelective(RoleAuthority record);

    @SelectProvider(type=RoleAuthoritySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.TINYINT, id=true),
        @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.INTEGER, id=true)
    })
    List<RoleAuthority> selectByExample(RoleAuthorityCriteria example);

    @UpdateProvider(type=RoleAuthoritySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") RoleAuthority record, @Param("example") RoleAuthorityCriteria example);

    @UpdateProvider(type=RoleAuthoritySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") RoleAuthority record, @Param("example") RoleAuthorityCriteria example);
}