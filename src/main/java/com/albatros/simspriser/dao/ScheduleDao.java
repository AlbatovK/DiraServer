package com.albatros.simspriser.dao;

import com.albatros.simspriser.domain.Schedule;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class ScheduleDao implements DaoInterface<Schedule> {

    private static final String collection_name = "schedule";

    @Override
    public void save(Schedule schedule) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(schedule.getOwnerId()).set(schedule).get();
    }

    @Override
    public void delete(Schedule schedule) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(schedule.getOwnerId()).delete().get();
    }

    public void updateScheduleNoteList(Schedule schedule) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(schedule.getOwnerId())
                .update("tasks", schedule.getTasks()).get();
    }

    public Schedule findByOwnerId(String ownerId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        Query query = firestore.collection(collection_name).whereEqualTo("ownerId", ownerId);
        List<QueryDocumentSnapshot> foundDocs = query.get().get().getDocuments();
        QueryDocumentSnapshot snapshot = foundDocs.stream().findFirst().orElse(null);
        return snapshot == null ? null : snapshot.toObject(Schedule.class);
    }

    @Override
    public List<Schedule> getAll() throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> refs = firestore.collection(collection_name).listDocuments();
        List<Schedule> res = new ArrayList<>();
        for (DocumentReference ref : refs) {
            Schedule item = ref.get().get().toObject(Schedule.class);
            res.add(item);
        }
        return res;
    }
}
