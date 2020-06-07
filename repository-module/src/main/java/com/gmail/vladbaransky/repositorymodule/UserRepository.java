package com.gmail.vladbaransky.repositorymodule;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.repositorymodule.model.User;

import java.beans.IntrospectionException;
import java.util.List;

public interface UserRepository extends GenericDaoRepository<Long, User> {

    User getUserByUsername(String username);

    Integer updatePasswordById(Long id, String Password);

    Integer updateRoleById(Long id, RoleEnum role);
}
