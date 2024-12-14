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

    public RestPost(String postId, String imageUrl, boolean likedPosts, String description, DocumentReference barberId) {
        super(postId, imageUrl, likedPosts, description);
        this.barberId = barberId;
    }
}