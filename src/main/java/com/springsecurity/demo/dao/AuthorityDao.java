package com.springsecurity.demo.dao;

import com.springsecurity.demo.entity.Authority;
import com.springsecurity.demo.entity.AuthorityCriteria;
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

public interface AuthorityDao {
    @SelectProvider(type=AuthoritySqlProvider.class, method="countByExample")
    long countByExample(AuthorityCriteria example);

    @DeleteProvider(type=AuthoritySqlProvider.class, method="deleteByExample")
    int deleteByExample(AuthorityCriteria example);

    @Delete({
        "delete from authority",
        "where authority_id = #{authorityId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer authorityId);

    @Insert({
        "insert into authority (name, description)",
        "values (#{name,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="authorityId", before=false, resultType=Integer.class)
    int insert(Authority record);

    @InsertProvider(type=AuthoritySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="authorityId", before=false, resultType=Integer.class)
    int insertSelective(Authority record);

    @SelectProvider(type=AuthoritySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR)
    })
    List<Authority> selectByExample(AuthorityCriteria example);

    @Select({
        "select",
        "authority_id, name, description",
        "from authority",
        "where authority_id = #{authorityId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR)
    })
    Authority selectByPrimaryKey(Integer authorityId);

    @UpdateProvider(type=AuthoritySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Authority record, @Param("example") AuthorityCriteria example);

    @UpdateProvider(type=AuthoritySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Authority record, @Param("example") AuthorityCriteria example);

    @UpdateProvider(type=AuthoritySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Authority record);

    @Update({
        "update authority",
        "set name = #{name,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR}",
        "where authority_id = #{authorityId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Authority record);
}