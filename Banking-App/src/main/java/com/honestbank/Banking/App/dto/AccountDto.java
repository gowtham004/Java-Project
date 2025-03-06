package com.honestbank.Banking.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private double accountBalance;

//    public AccountDto() {
//    }
//
//    public AccountDto(Long id, String accountHolderName, double accountBalance) {
//        this.id = id;
//        this.accountHolderName = accountHolderName;
//        this.accountBalance = accountBalance;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getAccountHolderName() {
//        return accountHolderName;
//    }
//
//    public void setAccountHolderName(String accountHolderName) {
//        this.accountHolderName = accountHolderName;
//    }
//
//    public double getAccountBalance() {
//        return accountBalance;
//    }
//
//    public void setAccountBalance(double accountBalance) {
//        this.accountBalance = accountBalance;
//    }
}
