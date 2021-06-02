package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.RoleUF;
import com.lyy.uploadfile.Entry.TablePrimaryKey;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Mapper.RoleMapper;
import com.lyy.uploadfile.Mapper.UserRoleMapper;
import com.lyy.uploadfile.Service.RoleService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    RoleMapper roleMapper;

    UserRoleMapper userRoleMapper;

    TablePrimaryKeyService tablePrimaryKeyService;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, UserRoleMapper userRoleMapper, TablePrimaryKeyService tablePrimaryKeyService) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.tablePrimaryKeyService = tablePrimaryKeyService;
    }

    @Override
    public List<RoleUF> getAll(int page, int limit) {
        int startPage = (page - 1) * limit, endPage = page * limit;
        List<RoleUF> all = roleMapper.getAll(startPage, endPage);
        return all;
    }

    @Override
    public Message deleteRole(long roleId) {
        int n = userRoleMapper.countByRoleId(roleId);
        if (n > 0) {
            return Message.fail("该角色已分配给用户， 请先调整用户角色");
        }
        roleMapper.deleteOne(roleId);
        return Message.success("删除成功");
    }

    @Override
    public Message insert(RoleUF roleUF) {
        Long id = tablePrimaryKeyService.get(RoleUF.class);
        roleUF.setId(id);
        int insert = roleMapper.insert(roleUF);
        return insert == 1 ? Message.success("获得新角色！") : Message.fail("创建失败");
    }

    @Override
    public Message changeStatus(long id, RoleUF.STATUS status) {
        int i = roleMapper.updateStatus(status.value(), id);
        return i == 1 ? Message.success("更新状态成功") : Message.fail("更新状态失败");
    }

    @Override
    public List<RoleUF> getAll() {
        return roleMapper.getList();
    }

}
