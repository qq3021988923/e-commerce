package com.yang.service.impl;

import com.yang.entity.AddressBook;
import com.yang.mapper.AddressBookMapper;
import com.yang.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;


    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    @Override
    public void save(AddressBook addressBook) {
        addressBookMapper.insert(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    @Override //
    @Transactional  // 这个接口的请求参数为id 用户id
    public void updateIsDefaultByUserId(AddressBook a) {
        a.setIsDefault(0);
        addressBookMapper.updateIsDefaultByUserId(a);

        a.setIsDefault(1);
        addressBookMapper.update(a);
    }

    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }
}
