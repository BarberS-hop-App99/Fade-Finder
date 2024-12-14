package edu.famu.fadefinder.controller;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;
import edu.famu.fadefinder.model.Barber;

import edu.famu.fadefinder.model.RestBarber;
import edu.famu.fadefinder.service.BarberService;
import edu.famu.fadefinder.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/barbers")
public class BarberController {

    private BarberService service;
    private BarberService barberService;

    public BarberController(BarberService service) {
        this.service = service;
    }
    // -------------------- Create Barber --------------------
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createBarber(@RequestBody HashMap<String, Object> barber) {
        try {
            RestBarber barbers = new RestBarber();
            barbers.setBarberPostalCode((String) barber.get("BarberPostalCode"));
            barbers.setPassword((String) barber.get("password"));
            barbers.setUsername((String) barber.get("username"));
            barbers.setInstagramUrl((String) barber.get("instagramUrl"));


            barbers.setPosts(new ArrayList<>());
            String id = service.createBarber(barbers);
            return ResponseEntity.status(201).body(new ApiResponse<>(true, "Barber created", id, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    // -------------------- Get Barber by ID --------------------
    @GetMapping("/{barberId}")
    public ResponseEntity<ApiResponse<Barber>> getBarberById(@PathVariable(name= "barberId") String barberId) {
        try {
            Barber barber = service.getBarber(barberId);

            if (barber != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Barber found", barber, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(false, "Barber not found", null, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{barberId}")
    public ResponseEntity<ApiResponse<Barber>> deleteBarberById(@PathVariable(name= "barberId") String barberId) {
        try{
            boolean deleted = service.deleteBarberById(barberId);
            if(deleted)
                return ResponseEntity.ok(new ApiResponse<>(true, "Barber deleted", null, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(false, "Barber not found", null, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

   @PutMapping("/{barberId}")
    public ResponseEntity<ApiResponse<RestBarber>> updateBarber(@PathVariable(name= "barberId") String barberId, @RequestBody HashMap<String, Object> barber){
        try{

            RestBarber barbers = new RestBarber();
            barbers.setBarberPostalCode((String) barber.get("BarberPostalCode"));
            barbers.setPassword((String) barber.get("password"));
            barbers.setUsername((String) barber.get("username"));
            barbers.setInstagramUrl((String) barber.get("instagramUrl"));
            ArrayList<String> list = (ArrayList<String>) barber.get("posts");
            ArrayList<String> postRef = new ArrayList<>();

            barbers.setPosts(postRef);
            boolean updated = service.updateBarber(barberId, barbers);

            if(updated)
                return ResponseEntity.status(201).body(new ApiResponse<>(true, "Barber updated", null, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(false, "Barber not updated", null, null));
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
            Barber barber = BarberService.findByUsernameAndPassword(username, password);
            if (barber != null) {
                return ResponseEntity.ok(barber);
            } else {
                return ResponseEntity.status(404).body("Barber not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during login: " + e.getMessage());
        }
    }
    @GetMapping("/find-barber")
    public ResponseEntity<?> findBarberId(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password) {
        try {
            // Use the service to find the customerId
            String customerId = service.findBarberIdByUsernameAndPassword(username, password);

            if (customerId != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Barber found", customerId, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "Barber not found", null, null));
            }
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error retrieving barber", null, e));
        }
    }
    @GetMapping("/barbers/count")
    public ResponseEntity<?> countBarbersByPostalCode(@RequestParam String BarberPostalCode) {
        try {
            long count = barberService.countByPostalCode(BarberPostalCode);

            return ResponseEntity.ok(new ApiResponse<>(true, "Barbers found", count, null));


        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error retrieving count", null, e));
        }
    }

    @GetMapping("/getId")
    public ResponseEntity<ApiResponse<String>> getId(@RequestBody String username){
        try {
            // Use the service to find the barberId
            String customerId = service.findBarberIdByUsername(username);

            if (customerId != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Barber found", customerId, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "Barber not found", null, null));
            }
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error retrieving barber", null, e));
        }
    }



}


