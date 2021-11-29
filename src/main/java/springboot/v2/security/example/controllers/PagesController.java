package springboot.v2.security.example.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.v2.security.example.appdata.AppData;
import springboot.v2.security.example.entity.User;
import springboot.v2.security.example.user.CustomUserDetailService;
import springboot.v2.security.example.user.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("pages")
public class PagesController {

    private final ApplicationContext context;
    private final CustomUserDetailService customUserDetailService;

    public PagesController(ApplicationContext context, CustomUserDetailService customUserDetailService) {
        this.context = context;
        this.customUserDetailService = customUserDetailService;
    }

    @GetMapping(value = "/create", produces="application/json")
    @ResponseBody
    public HashMap<String, String> createDefaultUsers() {
        List<String> msg = new ArrayList<>();

        User user = new User();
        user.setUsername("user");
        user.setFullname("User");
        user.setEmail("user@local.loc");
        user.setPassword("q");
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        msg.add(customUserDetailService.addUser(user));

        User editor = new User();
        editor.setUsername("editor");
        editor.setFullname("Editor");
        editor.setEmail("editor@local.loc");
        editor.setPassword("q");
        editor.setActive(true);
        editor.setRoles(Collections.singleton(Role.EDITOR));
        msg.add(customUserDetailService.addUser(editor));

        User admin = new User();
        admin.setUsername("admin");
        admin.setFullname("Admin");
        admin.setEmail("admin@local.loc");
        admin.setPassword("q");
        admin.setActive(true);
        admin.setRoles(Collections.singleton(Role.ADMIN));
        msg.add(customUserDetailService.addUser(admin));

        System.out.println("GET /pages/create");
        HashMap<String, String> map = new HashMap<>();
        map.put("value", String.join(", ", msg));
        return map;
    }

    @GetMapping("/info")
    public String userInfo(Authentication authentication, Model model) {
        AppData appData = context.getBean(AppData.class, authentication, "User info", "id-card-o", "");
        model.addAttribute("appData", appData);
        System.out.println("GET /pages/userinfo");
        return "pages/page-userinfo";
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/user")
    public String userPage(Authentication authentication, Model model) {
        AppData appData = context.getBean(AppData.class, authentication, "User page", "user", "");
        model.addAttribute("appData", appData);
        System.out.println("GET /pages/user");
        return "pages/page-user";
    }

    @PreAuthorize("hasAnyAuthority('EDITOR','ADMIN')")
    @GetMapping("/editor")
    public String editorPage(Authentication authentication, Model model) {
        AppData appData = context.getBean(AppData.class, authentication, "Editor page", "user", "");
        model.addAttribute("appData", appData);
        System.out.println("GET /pages/editor");
        return "pages/page-editor";
    }

}
