package edu.famu.fadefinder.controller;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.ACustomer;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.RestBarber;
import edu.famu.fadefinder.service.CustomerService;
import edu.famu.fadefinder.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
            ACustomer customers = new ACustomer();
            customers.setPostalCode((String) customer.get("postalCode"));
            customers.setPassword((String) customer.get("password"));
            customers.setUsername((String) customer.get("username"));

            String id = service.createCustomer(customers);
            return ResponseEntity.status(201).body(new ApiResponse<>(true, "Customer created", id, null));

        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<ACustomer>> getACustomerById(@PathVariable(name= "customerId") String customerId) {
        try {
            ACustomer customer = service.getCustomer(customerId);

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
    public ResponseEntity<ApiResponse<ACustomer>> deleteCustomer(@PathVariable(name= "customerId") String customerId) {
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
    public ResponseEntity<ApiResponse<ACustomer>> updateCustomer(@PathVariable(name= "customerId") String customerId, @RequestBody HashMap<String, Object> customer){
        try{

            ACustomer customers = new ACustomer();
            customers.setPostalCode((String) customer.get("postalCode"));
            customers.setPassword((String) customer.get("password"));
            customers.setUsername((String) customer.get("username"));
            ArrayList<String> list = (ArrayList<String>) customer.get("likedPosts");


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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            ACustomer customer = CustomerService.findByUsernameAndPassword(username, password);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(404).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during login: " + e.getMessage());
        }
    }

    @GetMapping("/check-username")
    public ResponseEntity<String> checkUsername(@RequestParam("username") String username) throws ExecutionException, InterruptedException {
        boolean exists = service.usernameExists(username);
        if (exists) {
            return ResponseEntity.status(409).body("Username is Taken"); // Conflict
        }
        return ResponseEntity.ok().body("Complete"); // Username is available
    }

    @GetMapping("/find-customer")
    public ResponseEntity<?> findCustomerId(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password) {
        try {
            // Use the service to find the customerId
            String customerId = service.findCustomerIdByUsernameAndPassword(username, password);

            if (customerId != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Customer found", customerId, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "Customer not found", null, null));
            }
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error retrieving customer", null, e));
        }
    }

    @GetMapping("/getId")
    public ResponseEntity<ApiResponse<String>> getId(@RequestBody String username){
        try {
            // Use the service to find the customerId
            String customerId = service.findCustomerIdByUsername(username);

            if (customerId != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Customer found", customerId, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "Customer not found", null, null));
            }
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error retrieving customer", null, e));
        }
    }

}
