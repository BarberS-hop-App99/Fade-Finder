package edu.famu.fadefinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends APost{
    Barber barberId;

    public Post(String postId, String imageUrl, boolean likedPosts, String description, Barber barberId) {
        super(postId, imageUrl, likedPosts, description);
        this.barberId = barberId;
    }
}
