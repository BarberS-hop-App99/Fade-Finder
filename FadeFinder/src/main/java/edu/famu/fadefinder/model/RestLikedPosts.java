package edu.famu.fadefinder.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.database.annotations.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestLikedPosts extends ALikedPosts{
    private @Nullable DocumentReference barberId;

    public RestLikedPosts(String likedPostId, Timestamp createdAt, DocumentReference barberId) {
        super(likedPostId, createdAt);
        this.barberId = barberId;
    }
}
