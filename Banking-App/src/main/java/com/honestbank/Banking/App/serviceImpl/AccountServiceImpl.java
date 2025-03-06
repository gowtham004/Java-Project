package com.honestbank.Banking.App.serviceImpl;

import com.honestbank.Banking.App.dto.AccountDto;
import com.honestbank.Banking.App.entity.Account;
import com.honestbank.Banking.App.mapper.AccountMapper;
import com.honestbank.Banking.App.repository.AccountRepository;
import com.honestbank.Banking.App.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(()->new RuntimeException("Acoount not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account Not Found"));
        double total = account.getAccountBalance() + amount;
        account.setAccountBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

//    @Override
//    public AccountDto deposit(Long id, double amount) {
//        try {
//            Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Not Found"));
//            double total = account.getAccountBalance() + amount;
//            account.setAccountBalance(total);
//            Account savedAccount = accountRepository.save(account);
//            return AccountMapper.mapToAccountDto(savedAccount);
//        } catch (Exception e) {
//            logger.error("Error during deposit operation for account ID: " + id, e);
//            throw e;
//        }
//    }

    @Override
    public AccountDto withDraw(Long id, double money) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Acoount not found"));
        if(account.getAccountBalance() < money){
            throw new RuntimeException("Insufficient amount");
        }
        double total = account.getAccountBalance() - money;
        account.setAccountBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts =  accountRepository.findAll();

        return accounts.stream().map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.deleteById(id);
    }

}
