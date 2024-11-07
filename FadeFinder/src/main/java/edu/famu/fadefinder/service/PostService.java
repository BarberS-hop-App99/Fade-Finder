package edu.famu.fadefinder.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.Post;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PostService {

    private static final String POSTS_COLLECTION = "Posts";

    // Create Post
    public String createPost(Post post) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> collectionsApiFuture = dbFirestore.collection(POSTS_COLLECTION).add(post);
        return collectionsApiFuture.get().getId();
    }

    // Get Post by ID
    public Post getPost(String postId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(POSTS_COLLECTION).document(postId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(Post.class);
        } else {
            return null;
        }
    }

    // Update Post
    public String updatePost(Post post) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(POSTS_COLLECTION)
                .document(post.getPostId()).set(post);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    // Delete Post
    public String deletePost(String postId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(POSTS_COLLECTION).document(postId).delete();
        return "Document with Post ID " + postId + " has been deleted";
    }
}