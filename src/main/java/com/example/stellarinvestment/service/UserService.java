package com.example.stellarinvestment.service;

import com.example.stellarinvestment.mail.Utility;
import com.example.stellarinvestment.model.AuthenticationType;
import com.example.stellarinvestment.model.User;
import com.example.stellarinvestment.repository.UserRepository;
import com.example.stellarinvestment.setting.EmailSettingBag;
import com.example.stellarinvestment.setting.SettingService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingService settingService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setEnabled(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
    }
    public boolean registerUser(User user) {
        boolean check = false;
        if(user != null) {
            if (userRepository.findByEmail(user.getEmail()) == null) {
                encodePassword(user);
                user.setEnabled(false);
                user.setCreatedTime(new Date());
                user.setAuthenticationType(AuthenticationType.DATABASE);

                String randomCode = RandomString.make(64);
                user.setVerificationCode(randomCode);

                userRepository.save(user);
                check = true;
            }
        }

        return check;
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public void sendVerificationEmail(HttpServletRequest request, User user)
            throws UnsupportedEncodingException, MessagingException {
        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

        String toAddress = user.getEmail();
        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());

        String verifyURL = Utility.getSiteURL(request) + "/auth/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("to Address: " + toAddress);
        System.out.println("Verify URL: " + verifyURL);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateAuthenticationType(User user, AuthenticationType type) {
        if (!user.getAuthenticationType().equals(type)) {
            userRepository.updateAuthenticationType(user.getId(), type);
        }
    }

    public void addNewCustomerUponOAuthLogin(String name, String email,
                                             AuthenticationType authenticationType) {
        User user = new User();
        user.setEmail(email);
        setName(name, user);

        user.setEnabled(true);
        user.setCreatedTime(new Date());
        user.setAuthenticationType(authenticationType);
        user.setPassword("");


        userRepository.save(user);
    }

    private void setName(String name, User user) {
        String[] nameArray = name.split(" ");
        if (nameArray.length < 2) {
            user.setFirstName(name);
            user.setLastName("");
        } else {
            String firstName = nameArray[0];
            user.setFirstName(firstName);

            String lastName = name.replaceFirst(firstName + " ", "");
            user.setLastName(lastName);
        }
    }
}