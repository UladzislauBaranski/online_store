package com.gmail.vladbaransky.servicemodule.impl;

import com.gmail.vladbaransky.repositorymodule.UserRepository;
import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.repositorymodule.model.User;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.AppUserPrincipal;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import com.gmail.vladbaransky.servicemodule.util.converter.UserConverter;
import com.gmail.vladbaransky.servicemodule.util.mail.SenderMessage;
import com.gmail.vladbaransky.servicemodule.util.password.impl.PasswordGeneratorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final static String SUBJECT = "New password";
    private final static String SEND_TO_MAIL = "vlad_baransky@mail.ru";
    private final static String MAIN_USER = "vladbaransky13@gmail.com";
    private static final int OBJECT_BY_PAGE = 10;

    private final UserRepository userRepository;
    private final PasswordGeneratorImpl passwordGenerator;
    private final SenderMessage senderMessage;

    public UserServiceImpl(UserRepository userRepository, PasswordGeneratorImpl passwordGenerator, SenderMessage senderMessage) {
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
        this.senderMessage = senderMessage;
    }

    @Override
    @Transactional
    public List<UserDTO> getUserByPage(int page) {
        int startPosition = (page - 1) * OBJECT_BY_PAGE;
        List<User> users = userRepository.getObjectByPage(startPosition, OBJECT_BY_PAGE);
        return users.stream()
                .map(UserConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = UserConverter.getDTOFromObject(user);
        }
        return userDTO;
    }

    @Override
    public AppUserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (AppUserPrincipal) authentication.getPrincipal();
        } else {
            return null;
        }
    }

    @Override
    public UserDTO getProfileCurrentUser() {
        AppUserPrincipal currentUser = getCurrentUser();
        String username = currentUser.getUsername();
        return getUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDTO addUser(UserDTO userDTO) {
        User user = UserConverter.getObjectFromDTO(userDTO);
        User userResult = userRepository.addObject(user);
        return UserConverter.getDTOFromObject(userResult);
    }

    @Override
    @Transactional
    public List<UserDTO> deleteById(List<Long> ids) {
        List<UserDTO> list = new ArrayList<>();
        for (Long id : ids) {
            User user = userRepository.getObjectById(id);
            if (!user.getUsername().equals(MAIN_USER)) {
                User deletedUser = userRepository.delete(user);
                UserDTO deletedUserDTO = UserConverter.getDTOFromObject(deletedUser);
                list.add(deletedUserDTO);
            } else {
                logger.info("User wasn't deleted");
            }
        }
        return list;
    }

    @Transactional
    @Override
    public List<Integer> updateRoleById(List<Long> ids, RoleEnum role) {
        List<Integer> list = new ArrayList<>();
        for (Long id : ids) {
            User user = userRepository.getObjectById(id);
            if (!user.getUsername().equals(MAIN_USER)) {
                Integer result = userRepository.updateRoleById(id, role);
                list.add(result);
            } else {
                logger.info("User's role wasn't refactored");
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<Integer> updatePasswordById(List<Long> ids) {
        List<Integer> resultList = new ArrayList<>();
        for (Long id : ids) {
            String password = passwordGenerator.generatePassword();
            Integer result = userRepository.updatePasswordById(id, passwordGenerator.createBCryptPassword(password));
            resultList.add(result);
            senderMessage.sendMessage(SEND_TO_MAIL, SUBJECT, password);
        }
        return resultList;
    }

    @Override
    public String getBCryptPassword() {
        String password = passwordGenerator.generatePassword();
        return passwordGenerator.createBCryptPassword(password);
    }

    @Transactional
    @Override
    public UserDTO findUsersById(Long id) {
        User users = userRepository.getObjectById(id);
        return UserConverter.getDTOFromObject(users);
    }
}
