package com.example.dyslexigram.service.impl;

import com.example.dyslexigram.model.User;
import com.example.dyslexigram.model.exceptions.InvalidUserIdException;
import com.example.dyslexigram.model.exceptions.UserNotFoundException;
import com.example.dyslexigram.repository.UsersRepository;
import com.example.dyslexigram.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> listAllUsers() {
        return this.usersRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.usersRepository.findById(id).orElseThrow(InvalidUserIdException::new);
    }

    @Override
    public User findByNickname(String nickname) {
        return this.usersRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<User> save(String nickname, int total_points) {
        User userTosave = new User(nickname,total_points);

        return Optional.of(this.usersRepository.save(userTosave));
    }

    @Override
    public User edit(Long id, String nickname, int total_points) {
        User userToEdit = this.findById(id);
        userToEdit.setNickname(nickname);
        userToEdit.setTotal_points(total_points);

        return this.usersRepository.save(userToEdit);
    }

    //Eden vid na implementacija. Ako ne raboti, moze da se implementira so .delete(istanca od user)
    @Override
    public void deleteUser(Long id) {
        if(id == null) throw new IllegalArgumentException();
        this.usersRepository.deleteById(id);
    }
}
