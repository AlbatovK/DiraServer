package com.albatros.simspriser.controller;

import com.albatros.simspriser.domain.Schedule;
import com.albatros.simspriser.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "Clears meta-data of all schedules")
    @GetMapping(value = "/refresh")
    public void refresh() throws ExecutionException, InterruptedException {
        List<Schedule> schedules = scheduleService.getSchedules();
        for (Schedule schedule : schedules) {
            schedule.clearTasks();
            scheduleService.updateSchedule(schedule);
        }
    }
}
