package com.albatros.simspriser.service;

import com.albatros.simspriser.dao.ScheduleDao;
import com.albatros.simspriser.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    @Autowired
    private ScheduleDao dao;

    public void saveSchedule(Schedule schedule) throws ExecutionException, InterruptedException {
        dao.save(schedule);
    }

    public Schedule findScheduleByOwnerId(String ownerId) throws ExecutionException, InterruptedException {
        return dao.findByOwnerId(ownerId);
    }

    public void updateSchedule(Schedule schedule) throws ExecutionException, InterruptedException {
        dao.updateScheduleNoteList(schedule);
    }

    public void deleteSchedule(Schedule schedule) throws ExecutionException, InterruptedException {
        dao.delete(schedule);
    }

    public List<Schedule> getSchedules() throws InterruptedException, ExecutionException {
        return dao.getAll();
    }
}
