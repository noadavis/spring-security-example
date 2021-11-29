package springboot.v2.security.example.appdata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SystemInfo {
    @Value("${spring.application.name}")
    private String SystemName;
    @Value("${spring.application.shortname}")
    private String ShortName;
    @PostConstruct
    private void init() {
        System.out.println("SystemInfo init");
    }
    public String getSystemName() {
        return SystemName;
    }
    public String getShortName() {
        return ShortName;
    }
}
