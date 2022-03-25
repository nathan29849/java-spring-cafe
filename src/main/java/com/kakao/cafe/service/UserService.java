package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.LoginedUser;
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

    public Long join(UserForm userForm) {
        User user = userForm.createUser();
        validateDuplicateUserId(user);
        userRepository.save(user);
        return user.getId();
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

    public UserForm findOneUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        return new UserForm(user.getUserId(), user.getName(), user.getPassword(), user.getEmail());
    }

    public void update(UpdateUserForm updateUserForm, Long id) {
        validatePassword(updateUserForm.getPassword(), id);
        User user = updateUserForm.createUser(updateUserForm.getNewPassword()); // 새로운 비밀번호로 User 객체 생성
        user.setId(id);
        userRepository.update(user, id);
    }

    private void validatePassword(String password, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        if (!user.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    public UserForm validateLoginUser(String userId, Object value) {
        LoginedUser loginedUser = (LoginedUser) value;
        User compareUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        return getLoginUserForm(loginedUser, compareUser);
    }

    private UserForm getLoginUserForm(LoginedUser loginedUser, User compareUser) {
        if (compareUser.getId().equals(loginedUser.getId())) {
            UserForm loginUserForm = new UserForm(compareUser.getUserId(), compareUser.getName(), compareUser.getPassword(), compareUser.getEmail());
            loginUserForm.setId(compareUser.getId());
            return loginUserForm;
        }
        throw new IllegalStateException("개인정보(id)가 일치하지 않습니다.");
    }
}
