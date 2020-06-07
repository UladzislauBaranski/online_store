package com.gmail.vladbaransky.servicemodule.model;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.gmail.vladbaransky.servicemodule.constant.validation.UserValidationMessages.*;
import static com.gmail.vladbaransky.servicemodule.constant.validation.UserValidationRules.*;

public class UserDTO {
    private Long id;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE, message = NAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = NAME_PATTERN_MESSAGE)
    private String name;

    @Size(min = MIN_NAME_SIZE, max = MAX_SURNAME_SIZE, message = SURNAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = SURNAME_PATTERN_MESSAGE)
    private String surname;

    @Size(min = MIN_NAME_SIZE, max = MAX_PATRONYMIC_SIZE, message = PATRONYMIC_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = PATRONYMIC_PATTERN_MESSAGE)
    private String patronymic;

    @Pattern(regexp = ADDRESS_PATTERN, message = ADDRESS_PATTERN_MESSAGE)
    private String address;

    @Pattern(regexp = TELEPHONE_PATTERN, message = TELEPHONE_PATTERN_MESSAGE)
    private String telephone;

    @NotEmpty(message = NOT_EMPTY_USERNAME_MESSAGE)
    @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE)
    private String username;

    private String password;

    @NotNull
    private RoleEnum role;
    private Boolean isExist = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(surname, userDTO.surname) &&
                Objects.equals(patronymic, userDTO.patronymic) &&
                Objects.equals(address, userDTO.address) &&
                Objects.equals(telephone, userDTO.telephone) &&
                Objects.equals(username, userDTO.username) &&
                Objects.equals(password, userDTO.password) &&
                role == userDTO.role &&
                Objects.equals(isExist, userDTO.isExist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, address, telephone, username, password, role, isExist);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isExist=" + isExist +
                '}';
    }
}
