package com.albatros.simspriser.service;

import com.albatros.simspriser.dao.NoteDao;
import com.albatros.simspriser.domain.DiraNote;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NoteService {

    @Autowired
    private NoteDao dao;

    public void saveNote(DiraNote note) throws ExecutionException, InterruptedException {
        dao.save(note);
    }

    public void deleteNote(DiraNote note) throws ExecutionException, InterruptedException {
        dao.delete(note);
    }

    public List<DiraNote> findInIdList(List<Long> id) throws ExecutionException, InterruptedException {
        return dao.findInIdList(id);
    }

    public DiraNote findNoteById(long id) throws ExecutionException, InterruptedException {
        return dao.findById(id);
    }

    public List<DiraNote> getNotes() throws InterruptedException, ExecutionException {
        return dao.getAll();
    }
}
