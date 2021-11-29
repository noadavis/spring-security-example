package springboot.v2.security.example.appdata;

public class PageInfo {
    private final String Title;
    private final String Icon;
    private final String BackPage;
    public PageInfo(String title, String icon, String backPage) {
        Title = title;
        Icon = icon;
        BackPage = backPage;
    }
    public String getTitle() {
        return Title;
    }
    public String getIcon() {
        return Icon;
    }
    public String getBackPage() {
        return BackPage;
    }
}
