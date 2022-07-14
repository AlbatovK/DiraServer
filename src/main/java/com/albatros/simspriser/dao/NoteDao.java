package com.albatros.simspriser.dao;

import com.albatros.simspriser.domain.DiraNote;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@Service
public class NoteDao implements DaoInterface<DiraNote> {

    private static final String collection_name = "notes";

    @Override
    public void save(DiraNote note) throws ExecutionException, InterruptedException {
        FirestoreClient.getFirestore().collection(collection_name)
                .document(String.valueOf(note.getId())).set(note).get();
    }

    @Override
    public void delete(DiraNote note) throws ExecutionException, InterruptedException {
        FirestoreClient.getFirestore().collection(collection_name)
                .document(String.valueOf(note.getId())).delete().get();
    }

    public DiraNote findById(long id) throws ExecutionException, InterruptedException {
        Query query = FirestoreClient.getFirestore().collection(collection_name).whereEqualTo("id", id);
        List<QueryDocumentSnapshot> foundDocs = query.get().get().getDocuments();
        QueryDocumentSnapshot snapshot = foundDocs.stream().findFirst().orElse(null);
        return snapshot == null ? null : snapshot.toObject(DiraNote.class);
    }

    public List<DiraNote> findInIdList(List<Long> idList) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> foundDocs = FirestoreClient.getFirestore().collection(collection_name)
                .whereIn("id", idList).get().get().getDocuments();
        List<DiraNote> res = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : foundDocs) {
            DiraNote note = snapshot.toObject(DiraNote.class);
            res.add(note);
        }
        return res;
    }

    @Override
    public List<DiraNote> getAll() throws InterruptedException, ExecutionException {
        List<DiraNote> res = new ArrayList<>();
        Iterable<DocumentReference> refs = FirestoreClient.getFirestore()
                .collection(collection_name).listDocuments();
        for (DocumentReference ref : refs) {
            DocumentSnapshot doc = ref.get().get();
            DiraNote item = doc.toObject(DiraNote.class);
            res.add(item);
        }
        return res;
    }
}
