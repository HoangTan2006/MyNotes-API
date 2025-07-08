package com.MyNotes.service.impl;

import com.MyNotes.entity.Role;
import com.MyNotes.exception.RoleException;
import com.MyNotes.repository.RoleRepository;
import com.MyNotes.service.RoleService;
import com.MyNotes.util.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Set<Role> getRoles(UserRole userRole) {
        Role role = roleRepository.findByRoleName(userRole.name()).orElseThrow(() -> new RoleException("Role not exists"));
        return Set.of(role);
    }
}
