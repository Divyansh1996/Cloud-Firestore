package com.example.cloudfirestore.model;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.Data;

@Document(collectionName = "users")
@Data
public class User {

    private String id;

    private String name;
    private String email;
    private String phone;
}
