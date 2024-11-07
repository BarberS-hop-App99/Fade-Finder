package edu.famu.fadefinder.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ABarber {
    @DocumentId
    private String barberId;              // Unique Barber identifier
    private String instagramUrl;          // URL for the barber's Instagram profile
    private String password;              // Password for the barber's account
    private String username;              // Username for the barber
    private String BarberPostalCode;

}
