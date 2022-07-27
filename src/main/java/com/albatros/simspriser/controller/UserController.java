package com.albatros.simspriser.controller;

import com.albatros.simspriser.domain.hashing.HashingManager;
import com.albatros.simspriser.domain.pojo.DiraUser;
import com.albatros.simspriser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
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

    @ApiOperation(value = "Provides implementation for users leagues transition based on their stats. Returns result data as string. Requires api-key header")
    @GetMapping(value = "/refresh/leagues")
    public String refreshLeagues(@RequestHeader("api-key") String key) throws ExecutionException, InterruptedException, NoSuchAlgorithmException, IllegalAccessException {
        if (!HashingManager.getHash(key).equalsIgnoreCase(HashingManager.hash))
            throw new IllegalAccessException("No Api key provided");

        final StringBuilder builder = new StringBuilder();

        Consumer<DiraUser> leagueIncreaseConsumer = u -> {
            u.setLeague(u.getLeague() + 1);
            builder.append(u.getNickname()).append('\n');
            try { service.saveUser(u); } catch (ExecutionException | InterruptedException ignored) { }
        };

        int i = 4;
        while (i > 0) {
            builder.append(i).append('\n');
            List<DiraUser> leagueUsers = service.getUsersByLeague(i);
            int maxScore = leagueUsers.stream().mapToInt(DiraUser::getScoreOfWeek).max().orElse(-1);
            leagueUsers.stream().filter(u -> u.getScoreOfWeek() == maxScore).forEach(leagueIncreaseConsumer);
            i--;
        }

        return builder.toString();
    }

    @ApiOperation(value = "Clears users daily meta-data. Requires api-key header")
    @GetMapping(value = "/refresh/day")
    public void refreshDay(@RequestHeader("api-key") String key) throws ExecutionException, InterruptedException, NoSuchAlgorithmException, IllegalAccessException {
        if (!HashingManager.getHash(key).equalsIgnoreCase(HashingManager.hash))
            throw new IllegalAccessException("No Api key provided");

        List<DiraUser> users = service.getUsers();
        for (DiraUser user : users) {
            if (user.getScoreOfDay() > 0)
                service.clearUserDayScore(user);
        }
    }

    @ApiOperation(value = "Clears users weekly meta-data. Requires api-key header")
    @GetMapping(value = "/refresh/week")
    public void refreshWeek(@RequestHeader("api-key") String key) throws ExecutionException, InterruptedException, NoSuchAlgorithmException, IllegalAccessException {
        if (!HashingManager.getHash(key).equalsIgnoreCase(HashingManager.hash))
            throw new IllegalAccessException("No Api key provided");

        List<DiraUser> users = service.getUsers();
        for (DiraUser user : users) {
            if (user.getScoreOfWeek() > 0)
                service.clearUserWeekScore(user);
        }
    }

    @ApiOperation(value = "Returns user with given tokenId")
    @GetMapping("/find")
    public DiraUser findById(@RequestParam("user_id") String user_id) throws ExecutionException, InterruptedException {
        return service.getUserById(user_id);
    }

    @ApiOperation(value = "Returns sublist of all users with edge indexes present in request params")
    @GetMapping("/get")
    public List<DiraUser> getPaged(
            @RequestParam("from") int from,
            @RequestParam("to") int to
    ) throws ExecutionException, InterruptedException {
        List<DiraUser> all = service.getAllPaged(to);
        return all.stream().skip(from).collect(Collectors.toList());
    }

    @ApiOperation(value = "Returns list of all users stored in the database")
    @GetMapping("/get/all")
    public List<DiraUser> getAll() throws ExecutionException, InterruptedException {
        return service.getUsers();
    }
}
