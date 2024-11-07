package edu.famu.fadefinder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class FadeFinderApplication {

    public static void main(String[] args) throws IOException {
        ClassLoader loader = FadeFinderApplication.class.getClassLoader();

        File file = new File(loader.getResource("serviceAccountKey.json").getFile());

        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        if(FirebaseApp.getApps().isEmpty())
            FirebaseApp.initializeApp(options);
        SpringApplication.run(FadeFinderApplication.class, args);
    }
}
