package edu.famu.fadefinder.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.Customer;
import edu.famu.fadefinder.model.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AdminService {

    private static final String BARBERS_COLLECTION = "Barber";
    private static final String CUSTOMERS_COLLECTION = "Customer";
    private static final String POSTS_COLLECTION = "Post";

    // Get All Barbers
    public List<Barber> getAllBarbers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(BARBERS_COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Barber> barbersList = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            Barber barber = document.toObject(Barber.class);
            barbersList.add(barber);
        }
        return barbersList;
    }

    // Get All Customers
    public List<Customer> getAllCustomers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(CUSTOMERS_COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Customer> customersList = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            Customer customer = document.toObject(Customer.class);
            customersList.add(customer);
        }
        return customersList;
    }

    // Get All Posts
    public List<Post> getAllPosts() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(POSTS_COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Post> postsList = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            Post post = document.toObject(Post.class);
            postsList.add(post);
        }
        return postsList;
    }

    // Delete Barber
    public String deleteBarber(String barberId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(BARBERS_COLLECTION).document(barberId).delete();
        return "Barber with ID " + barberId + " has been deleted.";
    }

    // Delete Post
    public String deletePost(String postId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(POSTS_COLLECTION).document(postId).delete();
        return "Post with ID " + postId + " has been deleted.";
    }
}