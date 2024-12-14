package edu.famu.fadefinder.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ACustomer {
    @DocumentId
    private String customerId;  // Unique customer identifier
    private String username;    // Username for the customer
    private String password;    // Password for the customer
    private String postalCode;  // Postal code for the customer
    private ArrayList<String> liked;
}