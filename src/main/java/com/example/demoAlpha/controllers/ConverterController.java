package com.example.demoAlpha.controllers;

import com.example.demoAlpha.model.Currencies;
import com.example.demoAlpha.model.Currency;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ConverterController {
	@GetMapping(value = "/")
	public String index(Model model) {
		String url;
		List<String> options = new ArrayList<>();
//		String currencies;
		List<Currency> currencies;
		try {
			currencies = Currencies.getAllCurrencies();
//			System.out.println(currencies);
			//allCurrencies.values().forEach(options::add);
//			url = MainLogic.mainLogic();
//			url = "src/main/resources/templates/images/initial_image.jpg";
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
//		model.addAttribute("options", options);
		model.addAttribute("currencies", currencies);
//		model.addAttribute("url", url);

		return "index";
	}
}
