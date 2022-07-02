package com.albatros.simspriser.dao;

import com.albatros.simspriser.domain.Schedule;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
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

    @Override
    public List<Schedule> getAll() throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();
        List<Schedule> res = new ArrayList<>();
        Iterable<DocumentReference> refs = firestore.collection(collection_name).listDocuments();
        for (DocumentReference ref : refs) {
            DocumentSnapshot doc = ref.get().get();
            Schedule item = doc.toObject(Schedule.class);
            res.add(item);
        }
        return res;
    }
}
