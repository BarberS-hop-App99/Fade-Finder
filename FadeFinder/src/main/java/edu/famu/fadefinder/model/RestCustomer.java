package edu.famu.fadefinder.model;

import com.google.cloud.firestore.DocumentReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCustomer extends ACustomer {
    private ArrayList<DocumentReference> likedPosts;  // ArrayList for Document references

    // Constructor for extending ACustomers and adding likedPosts
    public RestCustomer(String customerId, String username, String password, String postalCode, ArrayList<DocumentReference> likedPosts) {
        super(customerId, username, password, postalCode);  // Call to the ACustomers constructor
        this.likedPosts = likedPosts;  // Set liked posts as DocumentReferences in an ArrayList
    }
}