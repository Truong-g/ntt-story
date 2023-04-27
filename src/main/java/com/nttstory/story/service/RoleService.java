package com.nttstory.story.service;

import com.nttstory.story.dto.RoleRequestForm;
import com.nttstory.story.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> getRoleByName(String name);
    Optional<Role> getRoleById(Long id);
    Role addRole(RoleRequestForm requestForm);
    Role updateRole(Long id, RoleRequestForm requestForm);
    void deleteRole(Long id);
}
