package com.steve.formulaforecast.service.Account;

import com.steve.formulaforecast.persistence.AccountRepository;
import com.steve.formulaforecast.persistence.entity.account.AccountEntity;
import com.steve.formulaforecast.service.Account.model.Account;
import com.steve.formulaforecast.service.authentication.model.AccountCreationRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.InstantSource;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountPersistenceService {

    private final AccountRepository accountRepository;
    private final InstantSource instantSource;

    AccountPersistenceService(AccountRepository accountRepository, InstantSource instantSource) {
        this.accountRepository = accountRepository;
        this.instantSource = instantSource;
    }

    @Transactional
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.selectByEmail(email).map(this::toModel);
    }

    @Transactional
    public Optional<Account> getAccountByUid(UUID accountUid) {
        return accountRepository.selectAccountByUid(accountUid).map(this::toModel);
    }

    @Transactional
    public Optional<Account> getAccountByPhoneNumber(String phoneNumber) {
        return accountRepository.selectByPhoneNumber(phoneNumber).map(this::toModel);
    }

    @Transactional
    public void createAccount(AccountCreationRequest accountCreationRequest) {
        accountRepository.insertAccount(
                accountCreationRequest.getAccountUid(),
                accountCreationRequest.getFirstName(),
                accountCreationRequest.getLastName(),
                accountCreationRequest.getEmail(),
                accountCreationRequest.getPhoneNumber(),
                accountCreationRequest.getPassword(),
                instantSource.instant());
    }

    private Account toModel(AccountEntity accountEntity) {
        return new Account(
                accountEntity.accountUid(),
                accountEntity.firstName(),
                accountEntity.lastName(),
                accountEntity.email()
        );
    }
}
