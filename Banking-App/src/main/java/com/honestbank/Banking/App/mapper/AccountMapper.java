package com.honestbank.Banking.App.mapper;

import com.honestbank.Banking.App.dto.AccountDto;
import com.honestbank.Banking.App.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getAccountBalance()
        );
        return account;
    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountBalance()
        );
        return accountDto;

    }
}

