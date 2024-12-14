package edu.famu.fadefinder.model;

import com.google.cloud.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Barber extends ABarber {
    private ArrayList<Post> description;  // ArrayList of full Post objects instead of document references

    public Barber(String barberId, String instagramUrl, String password, String username, String BarberPostalCode, ArrayList<String> posts, ArrayList<Post> postDetails) {
        super(barberId, instagramUrl, password, username, BarberPostalCode, posts);
        this.description = postDetails;
    }
}