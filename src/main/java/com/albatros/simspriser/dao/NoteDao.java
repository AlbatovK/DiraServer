package com.albatros.simspriser.dao;

import com.albatros.simspriser.domain.DiraNote;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class NoteDao implements DaoInterface<DiraNote> {

    private static final String collection_name = "notes";

    @Override
    public void save(DiraNote note) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(String.valueOf(note.getId())).set(note).get();
    }

    @Override
    public void delete(DiraNote note) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(String.valueOf(note.getId())).delete().get();
    }

    @Override
    public List<DiraNote> getAll() throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();
        List<DiraNote> res = new ArrayList<>();
        Iterable<DocumentReference> refs = firestore.collection(collection_name).listDocuments();
        for (DocumentReference ref : refs) {
            DocumentSnapshot doc = ref.get().get();
            DiraNote item = doc.toObject(DiraNote.class);
            res.add(item);
        }
        return res;
    }
}
