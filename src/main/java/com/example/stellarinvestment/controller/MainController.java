package com.example.stellarinvestment.controller;

import com.example.stellarinvestment.file.FileUploadUtil;
import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping(value = "/main")
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String authMainPage(HttpServletRequest request, Model model) {
        User authenticatedUser = userService.getCurrentAuthUser(request);
        model.addAttribute("user", authenticatedUser);
        return "main";
    }

    @GetMapping(value = "/forbidden")
    public String forbiddenPage() {
        return "forbiddenPage";
    }

    @GetMapping(value = "/account_details")
    public String viewAccountDetails(Model model, HttpServletRequest request) {
        String email = userService.getEmailOfAuthenticatedCustomer(request);
        User user = userService.getUserByEmail(email);

        model.addAttribute("user", user);
        return "profile_client";
    }

    @PostMapping("/update_password")
    public String updateUserPassword(@RequestParam(name = "currentPassword") String currentPassword,
                                     @RequestParam(name = "newPassword") String newPassword,
                                     @RequestParam(name = "email") String email,
                                     Model model) {
        User authenticatedUser = userService.getUserByEmail(email);
        String messagePassword = userService.updateAuthPassword(authenticatedUser, currentPassword, newPassword);
        System.out.println(messagePassword);
        model.addAttribute("messagePassword", messagePassword);

        return "redirect:/main/account_details";
    }

    @PostMapping("/update_account_details")
    public String updateAccountDetails(Model model, User user,
                                       HttpServletRequest request, @RequestParam("profile_url") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setPhotos(fileName);

            String uploadDir = "user-ava-photos/" + user.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        model.addAttribute("message", "Your account details have been updated.");

        userService.updateNameForAuthenticatedCustomer(user, request);

        return "redirect:/main/account_details";
    }


}