package com.albatros.simspriser.controller;

import com.albatros.simspriser.domain.hashing.HashingManager;
import com.albatros.simspriser.domain.pojo.Schedule;
import com.albatros.simspriser.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/schedule")
@RestController
@Api(tags = {"ScheduleController"}, description = "Manages user schedules data and state")
@RequiredArgsConstructor
public class ScheduleController {

    @Autowired
    private final ScheduleService scheduleService;

    @ApiOperation(value = "Saves given schedule in the database")
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public void saveSchedule(@RequestBody Schedule schedule) throws ExecutionException, InterruptedException {
        scheduleService.saveSchedule(schedule);
    }

    @ApiOperation(value = "Returns schedule with given owner tokenId")
    @GetMapping(value = "/get")
    public Schedule getScheduleById(@RequestParam("owner_id") String user_id) throws ExecutionException, InterruptedException {
        return scheduleService.findScheduleByOwnerId(user_id);
    }

    @ApiOperation(value = "Clears meta-data of all schedules. Requires api-key header")
    @GetMapping(value = "/refresh")
    public void refresh(@RequestHeader("api-key") String key) throws ExecutionException, InterruptedException, NoSuchAlgorithmException, IllegalAccessException {
        if (!HashingManager.getHash(key).equalsIgnoreCase(HashingManager.hash))
            throw new IllegalAccessException("No Api key provided");

        List<Schedule> schedules = scheduleService.getSchedules();
        for (Schedule schedule : schedules) {
            if (!schedule.getTasks().isEmpty()) {
                schedule.clearTasks();
                scheduleService.updateSchedule(schedule);
            }
        }
    }
}
