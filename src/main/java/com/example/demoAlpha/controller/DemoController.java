package com.example.demoAlpha.controller;

import com.example.demoAlpha.DemoAlphaApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.example.demoAlpha.DemoAlphaApplication.*;

@Controller
@RequestMapping("/user")
public class DemoController {

    @GetMapping("/gif")
    public String getName(Model model) {
        try {
            if (getRubRateForToday() < getRubRateForYesterday()) {
                model.addAttribute("URL", getRichGif());
                System.out.println(getRichGif());
            } else {
                model.addAttribute("URL", getBrokeGif());
                System.out.println(getBrokeGif());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "gif";
    }

    @GetMapping("/address")
    public String getAddress(Model model) {
        model.addAttribute("message", "Hello world!");
        return "gif";
    }
}
