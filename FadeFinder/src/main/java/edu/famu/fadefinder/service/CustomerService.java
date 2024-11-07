package edu.famu.fadefinder.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.Customer;
import edu.famu.fadefinder.model.RestCustomer;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {
    private Firestore firestore;
    private static final String CUSTOMERS_COLLECTION = "Customer";

    public CustomerService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Customer documentToCustomer(DocumentSnapshot document) {
        Customer customer = new Customer();
        if (document.exists()) {
            customer = new Customer();
            customer.setCustomerId(document.getId());
            customer.setPassword(document.getString("password"));
            customer.setPostalCode(document.getString("postalCode"));
            customer.setUsername(document.getString("username"));
            customer.setLikedPosts(null);
        }
        return customer;
    }
    // Create a new Customer
    public String createCustomer(RestCustomer customer) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> collectionsApiFuture = firestore.collection(CUSTOMERS_COLLECTION).add(customer);
        DocumentReference rs = collectionsApiFuture.get();
        return rs.getId();
    }

    // Get a Customer by ID
    public Customer getCustomer(String customerId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference customerRef = firestore.collection(CUSTOMERS_COLLECTION).document(customerId);
        DocumentSnapshot customerSnap = customerRef.get().get();
        return documentToCustomer(customerSnap);
    }

    // Update an existing Customer
    public boolean updateCustomer(String customerId, RestCustomer customer) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference customerRef = firestore.collection(CUSTOMERS_COLLECTION).document(customerId);
        DocumentSnapshot customerSnap = customerRef.get().get();

        if (!customerSnap.exists()) {
            return false;
        }

        ApiFuture<WriteResult> writeResult = customerRef.set(customer, SetOptions.mergeFields("postalCode","username", "password","likedPosts"));
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
}
