package com.example.demoAlpha.controllers;

import com.example.demoAlpha.model.Currencies;
import com.example.demoAlpha.model.Gifs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class LinkController {

	@GetMapping(value="/get_gif")
	public String getGif(@RequestParam(name="currency") String currency) {
		String resultLink;
		try {
			if (Currencies.isDollarGrows(currency)) {
				resultLink = Gifs.getRichGif();
			} else {
				resultLink = Gifs.getBrokeGif();
			}
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return resultLink;
	}
}