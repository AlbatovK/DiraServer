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

    public List<DiraUser> getUsersByLeague(int league) throws ExecutionException, InterruptedException {
        return dao.getUsersByLeague(league);
    }

    public DiraUser getUserById(String id) throws ExecutionException, InterruptedException {
        return dao.findById(id);
    }

    public List<DiraUser> getAllPaged(int limit) throws ExecutionException, InterruptedException {
        return dao.getAllPaged(limit);
    }

    public void clearUserDayScore(DiraUser user) throws ExecutionException, InterruptedException {
        dao.clearUserDayScore(user);
    }

    public void clearUserWeekScore(DiraUser user) throws ExecutionException, InterruptedException {
        dao.clearUserWeekScore(user);
    }

    public List<DiraUser> getUsers() throws InterruptedException, ExecutionException {
        return dao.getAll();
    }
}
