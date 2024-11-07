package edu.famu.fadefinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends ACustomer {
    private ArrayList<Post> likedPosts;  // ArrayList for full Post objects

    // Constructor for extending ACustomers and adding likedPosts
    public Customer(String customerId, String username, String password, String postalCode, ArrayList<Post> likedPosts) {
        super(customerId, username, password, postalCode);  // Call to the ACustomers constructor
        this.likedPosts = likedPosts;  // Set liked posts as full Post objects in an ArrayList
    }
}