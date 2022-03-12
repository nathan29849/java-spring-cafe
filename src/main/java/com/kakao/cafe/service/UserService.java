package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UpdateUserForm;
import com.kakao.cafe.domain.dto.UserForm;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public int join(UserForm userForm) {
        User user = userForm.createUser();
        validateDuplicateUserId(user);
        userRepository.save(user);
        return user.getIndex();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    private void validateDuplicateUserId(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public UserForm findOneUser(int index) {
        User user = userRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        return new UserForm(user.getUserId(), user.getName(), user.getPassword(), user.getEmail());
    }

    public void update(UpdateUserForm updateUserForm, int index) {
        validatePassword(updateUserForm.getPassword(), index);
        User user = updateUserForm.createUser(updateUserForm.getNewPassword()); // 새로운 비밀번호로 User 객체 생성
        user.setIndex(index);
        userRepository.update(user, index);
    }

    private void validatePassword(String password, int index) {
        User user = userRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        if (!user.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
