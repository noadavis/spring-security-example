package springboot.v2.security.example.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.v2.security.example.appdata.AppData;
import springboot.v2.security.example.user.UserForm;
import springboot.v2.security.example.entity.User;
import springboot.v2.security.example.user.CustomUserDetailService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class HomeController {

    private final CustomUserDetailService userService;
    private final ApplicationContext context;

    public HomeController(ApplicationContext context, CustomUserDetailService userService) {
        this.context = context;
        this.userService = userService;
    }

    @GetMapping("")
    public String home(Authentication authentication, Model model) {
        AppData appData = context.getBean(AppData.class, authentication, "Home", "home", "");
        model.addAttribute("appData", appData);
        return "pages/home";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return "redirect:/";
    }

    @GetMapping("/auth/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("step", "reg");
        model.addAttribute("message", "message!");
        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String register(@ModelAttribute UserForm userForm, Model model) {
        User userFromDb = userService.findByUsername(userForm.getUsername());
        if (userFromDb != null) {
            model.addAttribute("step", "done");
            model.addAttribute("message", "user exist!");
            return "auth/register";
        }
        if (!userForm.password.equals(userForm.passcheck)) {
            model.addAttribute("step", "reg");
            model.addAttribute("message", "passwords not equal!");
        } else {
            userService.createUser(userForm);
            model.addAttribute("step", "done");
            model.addAttribute("message", "user created!");
        }
        return "auth/register";
    }

}