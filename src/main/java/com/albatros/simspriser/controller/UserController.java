package com.albatros.simspriser.controller;

import com.albatros.simspriser.domain.DiraUser;
import com.albatros.simspriser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequestMapping("/user")
@RestController
@Api(tags = {"UserController"}, description = "Provides set of CRUD operations with users " +
        "as well as manages their meta-data and in-between statistics")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService service;

    @ApiOperation(value = "Saves user to the database")
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public DiraUser createUser(@RequestBody DiraUser user) throws ExecutionException, InterruptedException {
        service.saveUser(user);
        return user;
    }

    @ApiOperation(value = "Returns a list of users present in a league with requested number")
    @GetMapping(value = "/league/get")
    public List<DiraUser> getUsersByLeague(@RequestParam("league") int league) throws ExecutionException, InterruptedException {
        return service.getUsersByLeague(league);
    }

    @ApiOperation(value = "Provides implementation for users leagues transition based on their stats")
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

    @ApiOperation(value = "Clears users daily meta-data")
    @GetMapping(value = "/refresh/day")
    public void refreshDay() throws ExecutionException, InterruptedException {
        List<DiraUser> users = service.getUsers();
        for (DiraUser user : users) {
            service.clearUserDayScore(user);
        }
    }

    @ApiOperation(value = "Clears users weekly meta-data")
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

    @GetMapping("/get")
    public List<DiraUser> getPaged(
            @RequestParam("from") int from,
            @RequestParam("to") int to
    ) throws ExecutionException, InterruptedException {
        List<DiraUser> all = service.getAllPaged(to);
        return all.stream().skip(from).collect(Collectors.toList());
    }

    @GetMapping("/get/all")
    public List<DiraUser> getAll() throws ExecutionException, InterruptedException {
        return service.getUsers();
    }
}
