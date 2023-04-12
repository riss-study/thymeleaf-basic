package dev.riss.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic (Model model) {
        model.addAttribute("data", "Hello Spring By Riss!!");
        // 타임리프는 기본적으로 특수문자에 대한 이스케이프를 제공해줌 => 저 <> 문자 그대로 나옴
        // 언이스케이프는 th:utext 를 이용하면 됨
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped (Model model) {
        model.addAttribute("data", "Hello <b>Spring</b> By <b>Riss</b>!!");
        // 타임리프는 기본적으로 특수문자에 대한 이스케이프를 제공해줌 => 저 <> 문자 그대로 나옴
        // 언이스케이프는 th:utext 를 이용하면 됨 + [(${data})]
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable (Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 10);

        List<User> list=new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map=new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects (HttpSession session,
                                Model model,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        // 스프링부트 3.0 부터는  타임리프에서 제공하는 기본 객체 중
        // ${#request}, ${#response}, ${#session}, ${#servletContext} 는 제공하지 않는다.
        // ${#locale} 은 제공
        // 스프링부트 3.0 미만은 제공
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());

        return "basic/basic-objects";
    }

    // Java 8 Time Utility Object in Thymeleaf
    @GetMapping("/date")
    public String date (Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    @GetMapping("/link")
    public String link (Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }

    @GetMapping("/literal")
    public String literal (Model model) {
        model.addAttribute("data", "Spring By Riss!");
        return "basic/literal";
    }

    @GetMapping("/operation")
    public String operation (Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring By Riss!");
        return "basic/operation";
    }

    @GetMapping("/attribute")
    public String attribute (Model model) {
        return "basic/attribute";
    }

    @GetMapping("/each")
    public String each (Model model) {
        addUsers(model);
        return "basic/each";
    }

    @GetMapping("/condition")
    public String condition (Model model) {
        addUsers(model);
        return "basic/condition";
    }

    @GetMapping("/comments")
    public String comments (Model model) {
        model.addAttribute("data", "Spring By Riss!");
        return "basic/comments";
    }

    @GetMapping("/block")
    public String block (Model model) {
        addUsers(model);
        return "basic/block";
    }

    @GetMapping("/javascript")
    public String inline (Model model) {
        model.addAttribute("user", new User("user\"A\"", 10));
        addUsers(model);
        return "basic/javascript";
    }

    private void addUsers (Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("UserA", 10));
        list.add(new User("UserB", 20));
        list.add(new User("UserC", 30));

        model.addAttribute("users", list);
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello (String data) {
            return "hello " + data;
        }
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }

}
