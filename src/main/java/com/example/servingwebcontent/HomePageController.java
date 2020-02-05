package com.example.servingwebcontent;

import com.example.servingwebcontent.rest.AddressBook;
import com.example.servingwebcontent.rest.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomePageController {

	@Autowired
	private AddressBookRepository addressBookRepo;

	@GetMapping("/home")
	public String greeting(Model model) {
		Iterable<AddressBook> books = addressBookRepo.findAll();
		List<Long> idList = new ArrayList<>();
		for (AddressBook book : books) idList.add(book.getId());
		model.addAttribute("idList", idList);
		return "addressBookSelect";
	}

    @GetMapping("/home/{id}")
    public String greeting(@PathVariable("id") Long id, Model model) {
        model.addAttribute("buddyInfos", addressBookRepo.findById(id).get().getBuddyInfos());
        return "addressBook";
    }
}
