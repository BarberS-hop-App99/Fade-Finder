package edu.famu.fadefinder.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.Timestamp;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APost {

    @DocumentId
    private String postId;
    private String imageUrl;
    private boolean likedPosts;
    private String description;


}
