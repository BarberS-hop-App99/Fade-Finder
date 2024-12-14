package edu.famu.fadefinder.controller;

import com.google.cloud.firestore.DocumentReference;
import edu.famu.fadefinder.model.Barber;
import edu.famu.fadefinder.model.Post;
import edu.famu.fadefinder.model.RestBarber;
import edu.famu.fadefinder.model.RestPost;
import edu.famu.fadefinder.service.PostService;
import edu.famu.fadefinder.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // Create Post
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createPost(@RequestBody HashMap<String, Object> post) {
        try {
            RestPost posts = new RestPost();
            posts.setImageUrl((String) post.get("imageUrl"));
            posts.setLikedPosts((Boolean) post.get("likedPosts")); // Fix type casting
            posts.setBarberId((DocumentReference) post.get("barberId")); // Handle barberId as String

            String id = service.createPost(posts);
            return ResponseEntity.status(201).body(new ApiResponse<>(true, "Post created", id, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        } catch (ClassCastException e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, "Invalid data type", null, e));
        }
    }

 /*
    // Get Post by ID
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable String postId) {
        try {
            Post post = postService.getPost(postId);
            if (post != null) {
                return ResponseEntity.ok(post);
            } else {
                return ResponseEntity.status(404).body("Post not found with ID: " + postId);
            }
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error retrieving post: " + e.getMessage());
        }
    }

    // Get Posts by Barber ID
    @GetMapping("/barber/{barberId}")
    public ResponseEntity<?> getPostsByBarberId(@PathVariable String barberId) {
        try {
            List<Post> posts = postService.getPostsByBarberId(barberId);
            return ResponseEntity.ok(posts);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error retrieving posts: " + e.getMessage());
        }
    }

    // Update Post
    @PutMapping("/update")
    public ResponseEntity<String> updatePost(@RequestBody Post post) {
        try {
            String updateTime = service.updatePost(post);
            return ResponseEntity.ok("Post updated successfully at: " + updateTime);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error updating post: " + e.getMessage());
        }
    }

    // Delete Post
   @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        try {
            String message = service.deletePost(postId);
            return ResponseEntity.ok(message);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error deleting post: " + e.getMessage());
        }
    }*/
}

