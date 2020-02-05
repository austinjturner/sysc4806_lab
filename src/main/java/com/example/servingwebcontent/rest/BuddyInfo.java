package com.example.servingwebcontent.rest;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class BuddyInfo {
    private String name, phonenumber, address;
    private int age;
    private long id;
    private AddressBook addressBook;

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

    @ManyToOne
    @JsonIgnore
    public AddressBook getAddressBook(){
        return this.addressBook;
    }


    public void setAddressBook(AddressBook addressBook){
        this.addressBook = addressBook;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public BuddyInfo(String name, String phonenumber, String address) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    public BuddyInfo() {

    }

    public BuddyInfo(BuddyInfo b) {
        this.name = b.name;
        this.phonenumber = b.phonenumber;
        this.address = b.address;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BuddyInfo) {
            BuddyInfo b = (BuddyInfo) o;
            return (	this.name.equals(b.name) &&
                    this.address.equals(b.address) &&
                    this.phonenumber.equals(b.phonenumber));

        }
        return false;
    }

    @Override
    public String toString() {
        return "Name: "+name+"     Phone Number: "+phonenumber+"     Address: "+address;
    }

    public String buddyGreeting() {
        return "Greetings, " + name + "!";
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Transient
    public boolean isOver18() {
        return age > 18;
    }
}
