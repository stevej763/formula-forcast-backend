package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.account.AccountAuthenticationUserDetailsEntity;
import com.steve.formulaforecast.persistence.entity.account.AccountEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends Repository<AccountEntity, Long> {

    @Query("""
            SELECT account_uid, first_name, last_name, email, phone_number, created_at
            FROM account
            WHERE email = :email
            """)
    Optional<AccountEntity> selectByEmail(String email);

    @Query("""
            SELECT account_uid, first_name, last_name, email, phone_number, password, created_at
            FROM account
            WHERE email = :email
            """)
    Optional<AccountAuthenticationUserDetailsEntity> selectUserDetailsByEmail(String email);

    @Query("""
            SELECT account_uid, first_name, last_name, email, phone_number, created_at
            FROM account
            WHERE email = :email
            """)
    Optional<AccountEntity> selectByPhoneNumber(String phoneNumber);

    @Query("""
            SELECT account_uid, first_name, last_name, email, phone_number, created_at
            FROM account
            WHERE account_uid = :accountUid
            """)
    Optional<AccountEntity> selectAccountByUid(UUID accountUid);

    @Modifying
    @Query("""
            INSERT INTO account(account_uid, first_name, last_name, email, phone_number, password, created_at)
                      VALUES (:accountUid, :firstName, :lastName, :email, :phoneNumber, :password, :createdAt)
            """)
    int insertAccount(
            UUID accountUid,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String password,
            Instant createdAt);
}
