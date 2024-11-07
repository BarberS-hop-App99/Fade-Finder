package edu.famu.fadefinder.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class ALikedPosts {
    @DocumentId
    private @Nullable String likedPostId;
    private Timestamp createdAt;

    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }
}
