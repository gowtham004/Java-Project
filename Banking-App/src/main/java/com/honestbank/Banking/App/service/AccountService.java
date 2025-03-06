package com.honestbank.Banking.App.service;

import com.honestbank.Banking.App.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id,double money);
    AccountDto withDraw(Long id,double money);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long id);
}
