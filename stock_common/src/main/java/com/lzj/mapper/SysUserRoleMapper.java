package com.lzj.mapper;

import com.lzj.pojo.entity.SysUserRole;

/**
* @author HAWEI
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2024-03-19 15:35:53
* @Entity com.lzj.pojo.entity.SysUserRole
*/
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

}
