package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.UserRepository;
import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.repositorymodule.model.User;
import com.gmail.vladbaransky.servicemodule.impl.UserServiceImpl;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import com.gmail.vladbaransky.servicemodule.util.mail.SenderMessage;
import com.gmail.vladbaransky.servicemodule.util.password.impl.PasswordGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.vladbaransky.servicemodule.util.TestValue.getIdList;
import static com.gmail.vladbaransky.servicemodule.util.TestValue.getStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordGeneratorImpl passwordGenerator;
    @Mock
    private SenderMessage senderMessage;

    @BeforeEach
    public void setup() {
        this.userService = new UserServiceImpl(userRepository, passwordGenerator, senderMessage);
    }

    @Test
    public void getUsersByPage_returnUsers() {
        List<User> addedUserList = getAddedUserList();

        when(userRepository.getObjectByPage(0, 10)).thenReturn(addedUserList);
        List<UserDTO> returnedUserList = userService.getUserByPage(1);
        verify(userRepository, times(1)).getObjectByPage(0, 10);
        for (int i = 0; i < returnedUserList.size(); i++) {
            getAssertion(returnedUserList.get(i), addedUserList.get(i));
        }
    }

    @Test
    public void getUserByUsername_returnUser() {
        UserDTO user = getUserDTO();
        User addedUser = getAddedUser();

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(addedUser);
        UserDTO returnedUser = userService.getUserByUsername(user.getUsername());
        verify(userRepository, times(1)).getUserByUsername(user.getUsername());

        getAssertion(returnedUser, addedUser);
    }

    @Test
    public void addUser_returnUser() {
        UserDTO userDTO = getUserDTO();
        User user = getUser();
        User addedUser = getAddedUser();

        when(userRepository.addObject(user)).thenReturn(addedUser);
        UserDTO returnedUser = userService.addUser(userDTO);
        verify(userRepository, times(1)).addObject(user);

        getAssertion(returnedUser, addedUser);
    }

    @Test
    public void deleteUserById_returnStatus() {
        List<User> userById = getAddedUserList();
        List<Long> idList = getIdList();

        for (int i = 0; i < idList.size(); i++) {
            when(userRepository.getObjectById(idList.get(i))).thenReturn(userById.get(i));
            when(userRepository.delete(userById.get(i))).thenReturn(userById.get(i));
        }
        List<UserDTO> deletedUserDTOList = userService.deleteById(idList);
        for (int i = 0; i < idList.size(); i++) {
            verify(userRepository, times(1)).getObjectById(idList.get(i));
            verify(userRepository, times(1)).delete(userById.get(i));
        }
        for (int i = 0; i < idList.size(); i++) {
            getAssertion(deletedUserDTOList.get(i), userById.get(i));
        }
    }

    @Test
    public void updateRoleById_returnStatus() {
        List<User> userById = getAddedUserList();
        List<Long> idList = getIdList();
        List<Integer> status = getStatus();

        for (int i = 0; i < idList.size(); i++) {
            when(userRepository.getObjectById(idList.get(i))).thenReturn(userById.get(i));
            when(userRepository.updateRoleById(userById.get(i).getId(), userById.get(i).getRole())).thenReturn(status.get(i));
        }
        List<Integer> returnedStatus = userService.updateRoleById(idList, RoleEnum.ADMINISTRATION);
        for (int i = 0; i < idList.size(); i++) {
            verify(userRepository, times(1)).getObjectById(idList.get(i));
            verify(userRepository, times(1)).updateRoleById(userById.get(i).getId(), userById.get(i).getRole());
        }
        for (int i = 0; i < idList.size(); i++) {
            assertThat(returnedStatus.get(i)).isEqualTo(status.get(i));
        }
    }

    @Test
    public void updatePasswordById_returnStatus() {
        List<User> userById = getAddedUserList();
        String password = "admin";
        String bcryptPassword = "$2y$12$8PI0mf9NXsDjxnbYS7xGIuuPLwLlidD5tM241nZPFdhrMKVgt.LNe";
        List<Long> idList = getIdList();
        List<Integer> status = getStatus();

        for (int i = 0; i < idList.size(); i++) {
            when(passwordGenerator.generatePassword()).thenReturn(password);
            when(passwordGenerator.createBCryptPassword(password)).thenReturn(bcryptPassword);
            when(userRepository.updatePasswordById(userById.get(i).getId(), bcryptPassword)).thenReturn(status.get(i));
        }
        List<Integer> returnedStatus = userService.updatePasswordById(idList);
        for (int i = 0; i < idList.size(); i++) {
            verify(passwordGenerator, times(10)).generatePassword();
            verify(passwordGenerator, times(10)).createBCryptPassword(password);
            verify(userRepository, times(1)).updatePasswordById(userById.get(i).getId(), bcryptPassword);
        }
        for (int i = 0; i < idList.size(); i++) {
            assertThat(returnedStatus.get(i)).isEqualTo(status.get(i));
        }
    }

    private void getAssertion(UserDTO returnedUser, User addedUser) {
        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getId()).isEqualTo(addedUser.getId());
        assertThat(returnedUser.getName()).isEqualTo(addedUser.getName());
        assertThat(returnedUser.getSurname()).isEqualTo(addedUser.getSurname());
        assertThat(returnedUser.getPatronymic()).isEqualTo(addedUser.getPatronymic());
        assertThat(returnedUser.getUsername()).isEqualTo(addedUser.getUsername());
        assertThat(returnedUser.getRole()).isEqualTo(addedUser.getRole());
        assertThat(returnedUser.getExist()).isEqualTo(addedUser.getExist());
    }

    private List<User> getAddedUserList() {
        List<User> users = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            User user = getUser();
            user.setId(i);
            users.add(user);
        }
        return users;
    }

    private User getUser() {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setPatronymic("patronymic");
        user.setUsername("username@gmail.com");
        user.setPassword("password");
        user.setRole(RoleEnum.ADMINISTRATION);
        user.setExist(true);
        return user;
    }

    private User getAddedUser() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setSurname("surname");
        user.setPatronymic("patronymic");
        user.setUsername("username@gmail.com");
        user.setPassword("password");
        user.setRole(RoleEnum.ADMINISTRATION);
        user.setExist(true);
        return user;
    }

    public static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name");
        userDTO.setSurname("surname");
        userDTO.setPatronymic("patronymic");
        userDTO.setUsername("username@gmail.com");
        userDTO.setPassword("password");
        userDTO.setRole(RoleEnum.ADMINISTRATION);
        userDTO.setExist(true);
        return userDTO;
    }
}
