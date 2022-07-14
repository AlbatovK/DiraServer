package com.albatros.simspriser.service;

import com.albatros.simspriser.dao.UserDao;
import com.albatros.simspriser.domain.DiraUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserDao dao;

    public void saveUser(DiraUser user) throws ExecutionException, InterruptedException {
        dao.save(user);
    }

    public void deleteUser(DiraUser user) throws ExecutionException, InterruptedException {
        dao.delete(user);
    }

    public List<DiraUser> getAllPaged(int limit) throws ExecutionException, InterruptedException {
        return dao.getAllPaged(limit);
    }

    public List<DiraUser> getUsers() throws InterruptedException, ExecutionException {
        return dao.getAll();
    }
}
