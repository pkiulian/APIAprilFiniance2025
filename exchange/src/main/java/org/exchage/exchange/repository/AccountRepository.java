package org.exchage.exchange.repository;


import java.util.Optional;

import org.exchage.exchange.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByOwnerId(Long ownerId);
}