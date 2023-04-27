package com.nttstory.story.service.impl;

import com.nttstory.story.dto.RoleRequestForm;
import com.nttstory.story.exception.ItemNotFoundException;
import com.nttstory.story.model.Role;
import com.nttstory.story.repository.RoleRepository;
import com.nttstory.story.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
    @Override
    public Role addRole(RoleRequestForm requestForm) {
        Role role= new Role();
        role.setName(requestForm.getName());
        return roleRepository.save(role);
    }
    @Override
    public Role updateRole(Long id, RoleRequestForm requestForm) {
        Role role = getRoleById(id).orElseThrow(() -> new ItemNotFoundException("No role found with id is " + id));
        role.setName(requestForm.getName());
        return roleRepository.save(role);
    }
    @Override
    public void deleteRole(Long id) {
        Role role = getRoleById(id).orElseThrow(() -> new ItemNotFoundException("No role found with id is " + id));
        roleRepository.delete(role);
    }
}
