package fr.uhuru.applicationForm.controller;

import fr.uhuru.applicationForm.dto.UserDTO;
import fr.uhuru.applicationForm.entity.User;
import fr.uhuru.applicationForm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AutoController {

    private UserService userService;

    public AutoController(UserService userService) {
        this.userService = userService;
    }

    // Method to handle home page request
    @GetMapping(value = "/index")
    public String home() {
        return "index";
    }

    // Method to handle login request
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    // Method to handle user registration form request
    @GetMapping(value = "/register")
    public String showRegistrationForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    // Method to handle user registration form submit request
    @PostMapping("register/save")
    public  String registration(@Valid @ModelAttribute("user") UserDTO userDTO,
                                BindingResult result,
                                Model model) {
        User existingUser = userService.findUserByEmail(userDTO.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "/register";
        }

        userService.saveUser(userDTO);
        return "redirect:/register?success";
    }

    // Method to handle list of users
    @GetMapping(value = "/users")
    public String users(Model model) {
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
