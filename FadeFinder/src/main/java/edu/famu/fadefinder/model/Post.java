package edu.famu.fadefinder.model;

public class Post {

    private String postId;
    private String content;  // Example field

    // Constructor accepting a String (post ID)
    public Post(String postId) {
        this.postId = postId;
    }

    // Getters and setters

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    // Add other fields and methods here as needed
}
