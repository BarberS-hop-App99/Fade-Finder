package edu.famu.fadefinder.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.CollectionReference;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.RestPost;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PostService {

    private static final String POSTS_COLLECTION = "Posts";
    private static final String BARBERS_COLLECTION = "Barbers";

    public String createPost(RestPost post) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        // Add the post to the Posts collection
        CollectionReference postsCollection = firestore.collection(POSTS_COLLECTION);
        DocumentReference postDocRef = postsCollection.document();
        post.setPostId(postDocRef.getId());
        postDocRef.set(post).get();

        // Update the barber's posts array with the post ID (string)
        DocumentReference barberDocRef = firestore.collection(BARBERS_COLLECTION).document(String.valueOf(post.getBarberId()));
        barberDocRef.update("posts", FieldValue.arrayUnion(postDocRef.getId())).get();

        return postDocRef.getId();
    }
}


    /*
    // Get Post by ID
    public Post getPost(String postId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference postRef = firestore.collection(POSTS_COLLECTION).document(postId);
        DocumentSnapshot postSnap = postRef.get().get();
        return documentToPost(postSnap);
    }

    // Get Posts by Barber ID
    public List<Post> getPostsByBarberId(String barberId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = dbFirestore.collection(POSTS_COLLECTION)
                .whereEqualTo("barberId", barberId)
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        List<Post> posts = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            posts.add(document.toObject(Post.class));
        }
        return posts;
    }


    // Delete Post
    public boolean deletePost(String postId) throws ExecutionException, InterruptedException {
        DocumentReference postRef = firestore.collection(POSTS_COLLECTION).document(postId);
        DocumentSnapshot postSnap = postRef.get().get();

        if(!postSnap.exists())
            return false;

        ApiFuture<WriteResult> writeResult = postRef.delete();
        WriteResult result = writeResult.get();

        return result != null;

    }*/

