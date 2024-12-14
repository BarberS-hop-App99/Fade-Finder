package edu.famu.fadefinder.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.ACustomer;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {
    private Firestore firestore;
    private static final String CUSTOMERS_COLLECTION = "Customer";

    public CustomerService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private ACustomer documentToCustomer(DocumentSnapshot document) {
        ACustomer customer = new ACustomer();
        if (document.exists()) {
            customer = new ACustomer();
            customer.setCustomerId(document.getId());
            customer.setPassword(document.getString("password"));
            customer.setPostalCode(document.getString("postalCode"));
            customer.setUsername(document.getString("username"));
            customer.setLiked(null);
        }
        return customer;
    }
    // Create a new Customer
    public String createCustomer(ACustomer customer) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> collectionsApiFuture = firestore.collection(CUSTOMERS_COLLECTION).add(customer);
        DocumentReference rs = collectionsApiFuture.get();
        return rs.getId();
    }

    // Get a Customer by ID
    public ACustomer getCustomer(String customerId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference customerRef = firestore.collection(CUSTOMERS_COLLECTION).document(customerId);
        DocumentSnapshot customerSnap = customerRef.get().get();
        return documentToCustomer(customerSnap);
    }

    // Update an existing Customer
    public boolean updateCustomer(String username, ACustomer customer) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference customerRef = firestore.collection(CUSTOMERS_COLLECTION).document(username);
        DocumentSnapshot customerSnap = customerRef.get().get();

        if (!customerSnap.exists()) {
            return false;
        }

        ApiFuture<WriteResult> writeResult = customerRef.set(customer, SetOptions.mergeFields("postalCode","username", "password","liked"));
        writeResult.get();
        return true;
    }

    // Delete a Customer by ID
    public boolean deleteCustomer(String customerId) throws ExecutionException, InterruptedException{
        DocumentReference customerRef = firestore.collection(CUSTOMERS_COLLECTION).document(customerId);
        DocumentSnapshot customerSnap = customerRef.get().get();

        if(!customerSnap.exists())
            return false;

        ApiFuture<WriteResult> writeResult = customerRef.delete();
        WriteResult result = writeResult.get();

        return result != null;

    }

    public static ACustomer findByUsernameAndPassword(String username, String password) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = dbFirestore.collection("Customer")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        return documents.get(0).toObject(ACustomer.class);

    }
    public boolean usernameExists(String username) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = dbFirestore.collection("Customer") // Or "Barbers"
                .whereEqualTo("username", username)
                .get();
        return !query.get().isEmpty();
    }

    public String findCustomerIdByUsernameAndPassword(String username, String password) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference customers = dbFirestore.collection(CUSTOMERS_COLLECTION);

        // Query for a customer with the given username and password
        ApiFuture<QuerySnapshot> query = customers
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get();

        QuerySnapshot querySnapshot = query.get();

        // If the query returns a result, get the customerId
        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            return document.getId(); // Return the Firestore document ID as the customerId
        }

        // Return null if no matching user is found
        return null;
    }
    public String findCustomerIdByUsername(String username) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference customers = dbFirestore.collection(CUSTOMERS_COLLECTION);

        // Query for a customer with the given username and password
        ApiFuture<QuerySnapshot> query = customers
                .whereEqualTo("username", username)
                .get();

        QuerySnapshot querySnapshot = query.get();

        // If the query returns a result, get the customerId
        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            return document.getId(); // Return the Firestore document ID as the customerId
        }

        // Return null if no matching user is found
        return null;
    }


}
