package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.servicemodule.model.AppUserPrincipal;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import java.util.List;

public interface UserService {

    UserDTO getUserByUsername(String username);

    AppUserPrincipal getCurrentUser();

    UserDTO getProfileCurrentUser();

    UserDTO addUser(UserDTO user);

    List<UserDTO> getUserByPage(int page);

    List<UserDTO> deleteById(List<Long> id);

    UserDTO findUsersById(Long id);

    List<Integer> updatePasswordById(List<Long> ids);

    String getBCryptPassword();

    List<Integer> updateRoleById(List<Long> ids, RoleEnum role);
}
