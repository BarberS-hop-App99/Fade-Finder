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
    private ArrayList<Post> posts;  // ArrayList of full Post objects instead of document references

    // Constructor for extending ABarbers and adding full Post objects
    public Barber(String barberId, String imageUrl, String instagramUrl, String password, String username, ArrayList<Post> posts) {
        super(barberId, imageUrl, instagramUrl, password, username);  // Call to the ABarbers constructor
        this.posts = posts;  // Set posts as full Post objects
    }
}