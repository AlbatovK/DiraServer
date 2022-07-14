package com.albatros.simspriser.service;

import com.albatros.simspriser.dao.NoteDao;
import com.albatros.simspriser.domain.DiraNote;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NoteService {

    @Autowired
    private NoteDao dao;

    @CachePut(value = "notes", key = "#note.id")
    public void saveNote(DiraNote note) throws ExecutionException, InterruptedException {
        dao.save(note);
    }

    public void deleteNote(DiraNote note) throws ExecutionException, InterruptedException {
        dao.delete(note);
    }

    public List<DiraNote> getAllPaged(int limit) throws ExecutionException, InterruptedException {
        return dao.getAllPaged(limit);
    }

    public List<DiraNote> findInIdList(List<Long> id) throws ExecutionException, InterruptedException {
        return dao.findInIdList(id);
    }

    @Cacheable(value = "users", key = "#id")
    public DiraNote findNoteById(long id) throws ExecutionException, InterruptedException {
        return dao.findById(id);
    }

    @Cacheable(value = "users")
    public List<DiraNote> getNotes() throws InterruptedException, ExecutionException {
        return dao.getAll();
    }
}
