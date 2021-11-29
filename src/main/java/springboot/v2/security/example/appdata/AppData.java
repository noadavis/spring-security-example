package springboot.v2.security.example.appdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import springboot.v2.security.example.user.Role;
import springboot.v2.security.example.entity.User;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Set;

public class AppData {
    @Autowired
    private SystemInfo systemInfo;
    private final PageInfo pageInfo;
    private boolean userAuth = false;
    private User userInfo;

    public AppData(Authentication authentication, String title, String icon, String backPage) {
        if (authentication != null) this.userAuth = authentication.isAuthenticated();
        if (this.userAuth) getUserInfo(authentication.getPrincipal());
        this.pageInfo = new PageInfo(title, icon, backPage);
    }

    @PostConstruct
    private void postConstruct() {
        System.out.println("AppData init");
    }

    private void getUserInfo(Object principal) {
        try {
            userInfo = (User) principal;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getUserId() {
        if (userInfo != null) return userInfo.getId();
        else return 0L;
    }

    public String getUserUsername() {
        if (userInfo != null) return userInfo.getUsername();
        else return "";
    }

    public String getUserFullname() {
        if (userInfo != null) return userInfo.getFullname();
        else return "";
    }

    public String getUserEmail() {
        if (userInfo != null) return userInfo.getEmail();
        else return "";
    }

    public Set<Role> getUserRoles() {
        if (userInfo != null) return userInfo.getRoles();
        else return Collections.emptySet();
    }

    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public boolean isUserAuth() {
        return userAuth;
    }

    public boolean checkPermissions() {
        return true;
    }
}
