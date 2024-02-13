package fr.uhuru.applicationForm.service;

import fr.uhuru.applicationForm.dto.UserDTO;
import fr.uhuru.applicationForm.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    User findUserByEmail(String email);
    List<UserDTO> findAllUsers();

}
