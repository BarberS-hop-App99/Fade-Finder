package edu.famu.fadefinder.controller;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.Customer;
import edu.famu.fadefinder.model.RestBarber;
import edu.famu.fadefinder.model.RestCustomer;
import edu.famu.fadefinder.service.CustomerService;
import edu.famu.fadefinder.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService service;
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createCustomer(@RequestBody HashMap<String, Object> customer){
        try{
            RestCustomer customers = new RestCustomer();
            customers.setPostalCode((String) customer.get("postalCode"));
            customers.setPassword((String) customer.get("Password"));
            customers.setUsername((String) customer.get("Username"));
            ArrayList<DocumentReference> likedPostRef = new ArrayList<>();
            ArrayList<String> list = (ArrayList<String>) customer.get("likedPosts");

            if(!list.isEmpty()) {
                Firestore db = FirestoreClient.getFirestore();
                for(String likedPost : list)
                {
                    likedPostRef.add(db.collection("likedPosts").document(likedPost));
                }
            }
            customers.setLikedPosts(likedPostRef);
            String id = service.createCustomer(customers);
            return ResponseEntity.status(201).body(new ApiResponse<>(true, "Customer created", id, null));

        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable(name= "customerId") String customerId) {
        try {
            Customer customer = service.getCustomer(customerId);

            if (customer != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Customer found", customer, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(false, "Customer not found", null, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Customer>> deleteCustomer(@PathVariable(name= "customerId") String customerId) {
        try{
            boolean deleted = service.deleteCustomer(customerId);
            if(deleted)
                return ResponseEntity.ok(new ApiResponse<>(true, "Customer deleted", null, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(false, "Customer not found", null, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<RestCustomer>> updateCustomer(@PathVariable(name= "customerId") String customerId, @RequestBody HashMap<String, Object> customer){
        try{

            RestCustomer customers = new RestCustomer();
            customers.setPostalCode((String) customer.get("postalCode"));
            customers.setPassword((String) customer.get("password"));
            customers.setUsername((String) customer.get("username"));
            ArrayList<String> list = (ArrayList<String>) customer.get("likedPosts");
            ArrayList<DocumentReference> likedPostRef = new ArrayList<>();

            if(!list.isEmpty()) {
                Firestore db = FirestoreClient.getFirestore();
                for(String likedPost : list)
                {
                    likedPostRef.add(db.collection("Post").document(likedPost));
                }
            }
            customers.setLikedPosts(likedPostRef);
            boolean updated = service.updateCustomer(customerId, customers);

            if(updated)
                return ResponseEntity.status(201).body(new ApiResponse<>(true, "Customer updated", null, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(false, "Customer not updated", null, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
