package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository {
    private final List<User> userStore = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User save(User user) {
        for (int i = 0; i < userStore.size(); i++) {
            if (userStore.get(i) == null) {
                return store(user, (long)i);
            }
        }
        return store(user, (long) userStore.size());
    }

    private User store(User user, Long id) {
        user.setId(id);
        userStore.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) throws IndexOutOfBoundsException{
        return Optional.ofNullable(userStore.get(Math.toIntExact(id)));
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userStore.stream()
                .filter(user -> user.compareById(userId))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStore);
    }

    @Override
    public void clearStore() {
        userStore.clear();
    }

    @Override
    public int size() {
        return userStore.size();
    }

    @Override
    public void update(User user, Long id) {
        userStore.set(Math.toIntExact(id), user);
    }
}
