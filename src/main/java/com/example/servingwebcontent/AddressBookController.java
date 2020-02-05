package com.example.servingwebcontent;

import com.example.servingwebcontent.rest.AddressBook;
import com.example.servingwebcontent.rest.AddressBookRepository;
import com.example.servingwebcontent.rest.BuddyInfo;
import com.example.servingwebcontent.rest.BuddyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/address-book")
public class AddressBookController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
    }

    @Autowired
	private AddressBookRepository addressBookRepo;

    @Autowired
    private BuddyInfoRepository buddyInfoRepo;

	@PostMapping
	public AddressBook addAddressBook() {
		AddressBook newAddressBook = new AddressBook();
        newAddressBook = addressBookRepo.save(newAddressBook);
		return newAddressBook;
	}

	@GetMapping
    public Iterable<AddressBook> getAllAddressBooks() {
	    return addressBookRepo.findAll();
    }

    @GetMapping("/{id}")
    public AddressBook getAddressBookById(@PathVariable("id") Long id){
	    Optional<AddressBook> oAddressBook = addressBookRepo.findById(id);
	    if (oAddressBook.isPresent()){
	        return oAddressBook.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping("/{id}/buddy")
    public BuddyInfo addBuddy(@PathVariable("id") Long id, @RequestBody BuddyInfo buddyInfo) {

        Optional<AddressBook> oAddressBook = addressBookRepo.findById(id);
        if (!oAddressBook.isPresent()){
            throw new ResourceNotFoundException();
        }
        AddressBook addressBook = oAddressBook.get();

        buddyInfo = buddyInfoRepo.save(buddyInfo);
        addressBook.addBuddy(buddyInfo);

        addressBookRepo.save(addressBook);

        return buddyInfo;
    }

    @DeleteMapping("/{id}/buddy/{buddyId}")
    public BuddyInfo removeBuddy(@PathVariable("id") Long id, @PathVariable("buddyId") Long buddyId) {
	    buddyInfoRepo.deleteById(buddyId);

        return null;
    }
}
