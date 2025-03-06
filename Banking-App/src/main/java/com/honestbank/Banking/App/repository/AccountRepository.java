package com.honestbank.Banking.App.repository;

import com.honestbank.Banking.App.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
