package com.example.servingwebcontent.rest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class AddressBook {
    private long id;
    private List<BuddyInfo> buddyInfos;

    /**
     * Gets the id of this Buddy.BuddyInfo.
     * @return the id
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the id of this Buddy.BuddyInfo to the specified value.
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }


    public void addBuddy(BuddyInfo b) {
        buddyInfos.add(b);
        b.setAddressBook(this);
    }

    public void removeBuddy() {
        buddyInfos.remove(0);
    }

    public AddressBook() {
        buddyInfos = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = "";
        for (BuddyInfo b : buddyInfos)
            s += b.toString() + "\n";
        return s;
    }

    public void setBuddyInfos(List<BuddyInfo> buddyInfos){
        this.buddyInfos = buddyInfos;
    }

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL)
    public List<BuddyInfo> getBuddyInfos(){
        return this.buddyInfos;
    }

    public int size() {
        return buddyInfos.size();
    }

    public void clear() {
        buddyInfos.clear();
    }

}