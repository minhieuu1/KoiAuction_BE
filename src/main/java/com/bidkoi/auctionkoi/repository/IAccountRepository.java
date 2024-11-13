package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.enums.Role;
import com.bidkoi.auctionkoi.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account,String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmailAndIdIsNot(String email,String id);
    boolean existsByPhoneAndIdIsNot(String phone,String id);
    Optional<Account> findByUsername(String username);
    Account findAccountById(String id);
    Account findAccountByUsername(String username);
    List<Account> findAllByRole(Role role);
    List<Account> findByRoleIsNot(Role role);
 }
