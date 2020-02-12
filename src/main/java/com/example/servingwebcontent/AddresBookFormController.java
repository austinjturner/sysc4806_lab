package com.example.servingwebcontent;

import com.example.servingwebcontent.rest.AddressBook;
import com.example.servingwebcontent.rest.AddressBookRepository;
import com.example.servingwebcontent.rest.BuddyInfo;
import com.example.servingwebcontent.rest.BuddyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/address-book")
public class AddresBookFormController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
    }

    @Autowired
    private AddressBookRepository addressBookRepo;

    @Autowired
    private BuddyInfoRepository buddyInfoRepo;

    @PostMapping("/{id}/buddy2")
    public BuddyInfo addBuddy(@PathVariable("id") Long id, @RequestParam Map<String, String> body ) {

        BuddyInfo buddyInfo = new BuddyInfo(body.get("name"), body.get("address"), body.get("phonenumber"), Integer.parseInt(body.get("ranking")));
        Optional<AddressBook> oAddressBook = addressBookRepo.findById(id);
        if (!oAddressBook.isPresent()){
            throw new ResourceNotFoundException();
        }
        AddressBook addressBook = oAddressBook.get();

        buddyInfo = buddyInfoRepo.save(buddyInfo);
        addressBook.addBuddy(buddyInfo);

        addressBookRepo.save(addressBook);

        //return "addressBookSelect";
        return buddyInfo;
    }

}
