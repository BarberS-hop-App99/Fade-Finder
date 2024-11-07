package edu.famu.fadefinder.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.RestBarber;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Service
public class BarberService {
    private Firestore firestore;
    private static final String BARBERS_COLLECTION = "Barber";

    public BarberService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Barber documentToBarber(DocumentSnapshot document) throws ParseException {
        Barber barber = null;
        if (document.exists()) {
            barber = new Barber();
            barber.setBarberId(document.getId());
            barber.setBarberPostalCode(document.getString("postalCode"));
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
    public Barber getBarber(String barberId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference barberRef = firestore.collection(BARBERS_COLLECTION).document(barberId);
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

        ApiFuture<WriteResult> writeResult = barberRef.set(barber, SetOptions.mergeFields("barberPostalCode","username", "password","posts"));
        writeResult.get();
        return true;
    }





}
