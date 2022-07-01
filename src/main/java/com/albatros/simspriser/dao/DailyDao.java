package com.albatros.simspriser.dao;

import com.albatros.simspriser.domain.DailyNote;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class DailyDao implements DaoInterface<DailyNote> {

    private static final String collection_name = "daily";

    @Override
    public void save(DailyNote note) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(String.valueOf(note.getId())).set(note).get();
    }

    @Override
    public void delete(DailyNote note) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(String.valueOf(note.getId())).delete().get();
    }

    @Override
    public List<DailyNote> getAll() throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();
        List<DailyNote> res = new ArrayList<>();
        Iterable<DocumentReference> refs = firestore.collection(collection_name).listDocuments();
        for (DocumentReference ref : refs) {
            DocumentSnapshot doc = ref.get().get();
            DailyNote item = doc.toObject(DailyNote.class);
            res.add(item);
        }
        return res;
    }
}

