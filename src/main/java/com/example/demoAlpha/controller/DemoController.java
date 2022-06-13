package com.example.demoAlpha.controller;

import com.example.demoAlpha.DemoAlphaApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.example.demoAlpha.DemoAlphaApplication.*;

@RestController
@RequestMapping("/user")
public class DemoController {

    @GetMapping("/name")
    public String getName() {
        try {
            if (getRubRateForToday() < getRubRateForYesterday())
                return (getRichGif());
            else
                return(getBrokeGif());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
         return null;
    }

    @GetMapping("/address")
    public String getAddress() {
        return "Russia!";
    }
}
