package edu.famu.fadefinder.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.RestBarber;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BarberService {
    private static final String BARBERS_COLLECTION = "Barber";

    private Firestore firestore;

    public BarberService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Barber documentToBarber(DocumentSnapshot document) throws ParseException {
        Barber barber = null;
        if (document.exists()) {
            barber = new Barber();
            barber.setBarberId(document.getId());
            barber.setBarberPostalCode(document.getString("BarberPostalCode"));
            barber.setInstagramUrl(document.getString("instagramUrl"));
            barber.setPassword(document.getString("password"));
            barber.setPosts(null);
            barber.setUsername(document.getString("username"));

        }
        return barber;
    }
    // Create a new Barber
    public String createBarber(RestBarber barber) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> collectionsApiFuture = firestore.collection(BARBERS_COLLECTION).add(barber);
        DocumentReference rs = collectionsApiFuture.get();
        return rs.getId();
    }

    // Get a Barber by ID
    public Barber getBarber(String username) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference barberRef = firestore.collection(BARBERS_COLLECTION).document(username);
        DocumentSnapshot barberSnap = barberRef.get().get();
        return documentToBarber(barberSnap);
    }

    // Update an existing Barber
    // Delete a Barber by ID
    public boolean deleteBarberById(String barberId) throws ExecutionException, InterruptedException {
        DocumentReference barberRef = firestore.collection(BARBERS_COLLECTION).document(barberId);
        DocumentSnapshot barberSnap = barberRef.get().get();

        if(!barberSnap.exists())
            return false;

        ApiFuture<WriteResult> writeResult = barberRef.delete();
        WriteResult result = writeResult.get();

        return result != null;

    }

    // Update an existing Barber by ID
    public boolean updateBarber(String barberId, RestBarber barber) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference barberRef = firestore.collection(BARBERS_COLLECTION).document(barberId);
        DocumentSnapshot barberSnap = barberRef.get().get();

        if (!barberSnap.exists()) {
            return false;
        }

        ApiFuture<WriteResult> writeResult = barberRef.set(barber, SetOptions.mergeFields("BarberPostalCode","username", "password","posts"));
        writeResult.get();
        return true;
    }

    public static Barber findByUsernameAndPassword(String username, String password) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = dbFirestore.collection("Barber")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

            return documents.get(0).toObject(Barber.class);
        
    }
    public String findBarberIdByUsernameAndPassword(String username, String password) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference barbers = dbFirestore.collection(BARBERS_COLLECTION);

        // Query for a customer with the given username and password
        ApiFuture<QuerySnapshot> query = barbers
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
    public String findBarberIdByUsername(String username) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference barbers = dbFirestore.collection(BARBERS_COLLECTION);

        // Query for a customer with the given username and password
        ApiFuture<QuerySnapshot> query = barbers
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

    public long countByPostalCode(String BarberPostalCode) throws ExecutionException, InterruptedException {
            String normalizedPostalCode = BarberPostalCode.trim().toLowerCase();
            System.out.println("Normalized postal code: " + normalizedPostalCode);
            long count = firestore.collection("Barber")
                    .whereEqualTo("BarberPostalCode", normalizedPostalCode)
                    .get()
                    .get()
                    .size();
            return count;

    }






}
