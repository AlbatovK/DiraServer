package com.albatros.simspriser.service;

import com.albatros.simspriser.dao.DailyDao;
import com.albatros.simspriser.domain.DailyNote;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class DailyService {

    @Autowired
    private DailyDao dao;

    public void saveDailyNote(DailyNote note) throws ExecutionException, InterruptedException {
        dao.save(note);
    }

    public void deleteDailyNote(DailyNote note) throws ExecutionException, InterruptedException {
        dao.delete(note);
    }

    public List<DailyNote> getDailyNotes() throws InterruptedException, ExecutionException {
        return dao.getAll();
    }
}
