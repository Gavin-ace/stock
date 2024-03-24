package com.lzj.mapper;

import com.lzj.pojo.entity.SysRolePermission;

/**
* @author HAWEI
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2024-03-19 15:35:53
* @Entity com.lzj.pojo.entity.SysRolePermission
*/
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

}
