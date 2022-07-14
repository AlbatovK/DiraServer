package com.albatros.simspriser.rest.controller;

import com.albatros.simspriser.domain.DiraUser;
import com.albatros.simspriser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService service;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public DiraUser createUser(@RequestBody DiraUser user) throws ExecutionException, InterruptedException {
        service.saveUser(user);
        return user;
    }

    @GetMapping(value = "/league/get/all")
    public List<DiraUser> getUsersByLeague(@RequestParam("league") int league) throws ExecutionException, InterruptedException {
        return service.getUsersByLeague(league);
    }

    @GetMapping(value = "/refresh/leagues")
    public void refreshLeagues() throws ExecutionException, InterruptedException {

        Consumer<DiraUser> leagueIncreaseConsumer = u -> {
            u.setLeague(u.getLeague() + 1);
            try { service.saveUser(u); } catch (ExecutionException | InterruptedException ignored) { }
        };

        int i = 4;
        while (i > 0) {
            service.getUsersByLeague(i).stream().limit(1).forEach(leagueIncreaseConsumer);
            i--;
        }
    }

    @GetMapping(value = "/refresh/day")
    public void refreshDay() throws ExecutionException, InterruptedException {
        List<DiraUser> users = service.getUsers();
        for (DiraUser user : users) {
            service.clearUserDayScore(user);
        }
    }

    @GetMapping(value = "/refresh/week")
    public void refreshWeek() throws ExecutionException, InterruptedException {
        List<DiraUser> users = service.getUsers();
        for (DiraUser user : users) {
            service.clearUserWeekScore(user);
        }
    }

    @GetMapping("/find")
    public DiraUser findById(@RequestParam("user_id") String user_id) throws ExecutionException, InterruptedException {
        return service.getUserById(user_id);
    }

    @GetMapping("/get/all")
    public List<DiraUser> getAll() throws ExecutionException, InterruptedException {
        return service.getAllPaged(10);
    }
}
