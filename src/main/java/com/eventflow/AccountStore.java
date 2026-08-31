package com.eventflow;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.UUID;

public class AccountStore {
    private final Map<UUID, Account> accounts = new HashMap<>();

    public void storeAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Invalid account");
        }
        if (accounts.containsKey(account.getId())) {
            throw new IllegalArgumentException("An account with this id already exists");
        }

        accounts.put(account.getId(), account);
    }

    public Optional<Account> findAccount(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        return Optional.ofNullable(accounts.get(id));
    }
}
