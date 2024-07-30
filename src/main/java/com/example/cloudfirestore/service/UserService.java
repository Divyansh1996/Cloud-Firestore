package com.example.cloudfirestore.service;

import com.example.cloudfirestore.model.User;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.v1.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Firestore firestore;

    public UserService() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\asus\\Downloads\\cloud-spanner-430219-92527c7aa217.json"));
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder().setCredentials(credentials).setProjectId("cloud-spanner-430219").build();
        this.firestore = firestoreOptions.getService();
    }

    public String createUser(User user) throws ExecutionException, InterruptedException {
        WriteResult users = firestore.collection("users").document().set(user).get();
        return "Created Document";
    }

    public List<User> getUser(String userId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firestore.collection("users").whereEqualTo("id", userId).get();
        QuerySnapshot queryDocumentSnapshots = querySnapshotApiFuture.get();
        return queryDocumentSnapshots.getDocuments().stream().map(m->m.toObject(User.class)).collect(Collectors.toList());
    }

    public String deleteUser(String userId) throws ExecutionException, InterruptedException {
        firestore.collection("users").whereEqualTo("id", userId).get().get().getDocuments().stream().forEach(queryDocumentSnapshot -> {
            String id = queryDocumentSnapshot.getId();
            firestore.collection("users").document(id).delete();
        });
        return "Deleted Document";
    }

    public String updateUser(String userId, User user) throws ExecutionException, InterruptedException {
        firestore.collection("users").whereEqualTo("id",userId).get().get().getDocuments().stream().forEach(queryDocumentSnapshot -> {
            String id = queryDocumentSnapshot.getId();
            firestore.collection("users").document(id).set(user);
        });
        return "Updated Document";
    }

}
