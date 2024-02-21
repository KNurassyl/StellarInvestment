package com.example.stellarinvestment.controller;

import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.repository.RoleRepository;
import com.example.stellarinvestment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login_user";
    }

    @GetMapping(value = "/main")
    public String mainPage() {
        return "Unauth_Main";
    }

    @GetMapping(value = "/register")
    public String registerPage() {
        return "register_user";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code) {
        String redirect = "";
        if (userService.verify(code)) {
            redirect = "redirect:/auth/login/?verify_success";
        } else {
            redirect = "redirect:/auth/login/?verify_fail";
        }

        return redirect;
    }

    @PostMapping(value = "/register")
    public String registerPage(@RequestParam(name = "reg_email") String email,
                               @RequestParam(name = "reg_password") String password,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "surname") String surname,
                               @RequestParam(name = "role") String role,
                               HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        String redirect = "";

        User user = new User();
        user.setFirstName(name);
        user.setLastName(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.getRoles().add(roleRepository.findByName(role));

        if(userService.registerUser(user)) {
            userService.sendVerificationEmail(request, user);
            redirect = "redirect:/auth/login/?afterRegistration";
        } else {
            redirect = "redirect:/auth/login/?accountExist";
        }

        return redirect;
    }

}
