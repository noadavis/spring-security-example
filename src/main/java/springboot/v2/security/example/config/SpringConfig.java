package springboot.v2.security.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import springboot.v2.security.example.appdata.AppData;

@Configuration
public class SpringConfig {

    @Bean
    @Scope("prototype")
    public AppData appData(Authentication authentication, String title, String icon, String backPage) {
        return new AppData(authentication, title, icon, backPage);
    }

}
