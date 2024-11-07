package edu.famu.fadefinder.model;

import com.google.cloud.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikedPosts extends ALikedPosts {
    private Barber barberId;

    public LikedPosts(String likedPostId, Timestamp createdAt, Barber barberId) {
        super(likedPostId, createdAt);
        this.barberId = barberId;
    }
}
