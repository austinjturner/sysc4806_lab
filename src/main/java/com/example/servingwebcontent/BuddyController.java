package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BuddyController {

	@GetMapping("/buddy")
	public String greeting(String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

}
