package com.MyNotes.service;

import com.MyNotes.entity.Role;
import com.MyNotes.util.UserRole;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoles(UserRole userRole);
}
