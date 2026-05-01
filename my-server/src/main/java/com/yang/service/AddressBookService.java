package com.yang.service;

import com.yang.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    List<AddressBook> list(AddressBook addressBook);

    public void save(AddressBook addressBook);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void updateIsDefaultByUserId(AddressBook a);

    void deleteById(Long id);
}
