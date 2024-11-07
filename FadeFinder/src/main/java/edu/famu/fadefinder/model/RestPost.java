package edu.famu.fadefinder.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPost extends APost {
    private DocumentReference barberId;

    public RestPost(String postId, String imageUrl, Timestamp createdAt, DocumentReference barberId) {
        super(postId, imageUrl, createdAt);
        this.barberId = barberId;
    }
}