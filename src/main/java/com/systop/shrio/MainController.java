package main.java.com.systop.shrio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/ywbg")
    public String ywbg(){
        return "redirect:/ywbg/main";
    }
}
