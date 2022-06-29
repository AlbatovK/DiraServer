package com.albatros.simspriser.dao;

import com.albatros.simspriser.domain.DiraUser;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class UserDao implements DaoInterface<DiraUser> {

    private static final String collection_name = "users";

    @Override
    public void save(DiraUser user) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(user.getTokenId()).set(user).get();
    }

    @Override
    public void delete(DiraUser user) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(collection_name).document(user.getTokenId()).delete().get();
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
