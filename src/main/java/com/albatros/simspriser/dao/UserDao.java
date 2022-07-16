package com.albatros.simspriser.dao;

import com.albatros.simspriser.domain.pojo.DiraUser;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class UserDao implements DaoInterface<DiraUser> {

    private static final String collection_name = "users";

    @Override
    public void save(DiraUser user) throws ExecutionException, InterruptedException {
        FirestoreClient.getFirestore().collection(collection_name)
                .document(user.getTokenId()).set(user).get();
    }

    public void clearUserDayScore(DiraUser user) throws ExecutionException, InterruptedException {
        FirestoreClient.getFirestore().collection(collection_name).document(user.getTokenId())
                .update("scoreOfDay", 0).get();
    }

    public void clearUserWeekScore(DiraUser user) throws ExecutionException, InterruptedException {
        FirestoreClient.getFirestore().collection(collection_name).document(user.getTokenId())
                .update("scoreOfWeek", 0).get();
    }

    @Override
    public void delete(DiraUser user) throws ExecutionException, InterruptedException {
        FirestoreClient.getFirestore().collection(collection_name)
                .document(user.getTokenId()).delete().get();
    }

    public List<DiraUser> getUsersByLeague(int league) throws ExecutionException, InterruptedException {
        return FirestoreClient.getFirestore().collection(collection_name).whereEqualTo("league", league)
                .get().get().getDocuments().stream()
                .map(s -> s.toObject(DiraUser.class))
                .sorted(Comparator.comparingInt(DiraUser::getScoreOfWeek).reversed())
                .collect(Collectors.toList());
    }

    public DiraUser findById(String id) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        Query query = firestore.collection(collection_name).whereEqualTo("tokenId", id);
        List<QueryDocumentSnapshot> foundDocs = query.get().get().getDocuments();
        QueryDocumentSnapshot snapshot = foundDocs.stream().findFirst().orElse(null);
        return snapshot == null ? null : snapshot.toObject(DiraUser.class);
    }

    public List<DiraUser> getAllPaged(int limit) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> docs = FirestoreClient.getFirestore().collection(collection_name)
                .orderBy("score", Query.Direction.DESCENDING).limit(limit).get().get().getDocuments();
        List<DiraUser> res = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : docs) {
            DiraUser user = snapshot.toObject(DiraUser.class);
            res.add(user);
        }
        return res;
    }

    @Override
    public List<DiraUser> getAll() throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();
        List<DiraUser> res = new ArrayList<>();
        Iterable<DocumentReference> refs = firestore.collection(collection_name).listDocuments();
        for (DocumentReference ref : refs) {
            DocumentSnapshot doc = ref.get().get();
            DiraUser item = doc.toObject(DiraUser.class);
            res.add(item);
        }
        return res;
    }
}
