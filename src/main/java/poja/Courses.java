package poja;

import java.util.List;

public class Courses {

    private List<WebAutomation> WebAutomation;
    private List<Api> Api;
    private List<Mobile> mobile;

    public List<WebAutomation> getWebAutomation() {
        return WebAutomation;
    }

    public void setWebAutomation(List<WebAutomation> webAutomation) {
        WebAutomation = webAutomation;
    }

    public List<Api> getApi() {
        return Api;
    }

    public void setApi(List<Api> api) {
        Api = api;
    }

    public List<Mobile> getMobile() {
        return mobile;
    }

    public void setMobile(List<Mobile> mobile) {
        this.mobile = mobile;
    }
}
