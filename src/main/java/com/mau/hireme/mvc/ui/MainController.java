package com.mau.hireme.mvc.ui;

import com.mau.hireme.HireMeApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {

    @GetMapping("")
    public String configuration(Model model) {
        return "home";
    }

}
