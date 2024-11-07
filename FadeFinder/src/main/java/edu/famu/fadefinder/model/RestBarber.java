package edu.famu.fadefinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FirestoreOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RestBarber extends ABarber {
    private ArrayList<DocumentReference> posts;  // Document references to posts made by the barber


    public RestBarber(String barberId, String instagramUrl, String password, String username, String BarberPostalCode, ArrayList<DocumentReference> posts) {
        super(barberId, instagramUrl, password, username, BarberPostalCode);
        this.posts = posts;
    }
}