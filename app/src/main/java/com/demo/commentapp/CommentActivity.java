package com.demo.commentapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.commentapp.model.Comment;
import com.demo.commentapp.remotestore.RemoteStoreInterface;
import com.demo.commentapp.remotestore.Server1;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Exercise Summary
 *
 * This app was created in Android Studio
 * 
 * ======================================================
 * =====================================
 * ASSUMPTIONS
 * 1. Saving of objects will be done as JSON strings
 *
 * 2. Comment and similiar objects to save in Remote Store (eg, Annotation) are Java Serializable
 *      > If there are non-serializable fields, will need to address the additional requirements there (likely at the application layer)
 *
 * ======================================================
 * ======================================
 * APIs BEING USED
 * 1. OkHttp - compile 'com.squareup.okhttp3:okhttp:3.2.0'
 * 2. GSON - compile 'com.google.code.gson:gson:2.2.+'
 *
 *
 * ======================================================
 * =======================================
 * DESIGN PATTERNS
 *
 * 1. Abstract Factory Pattern
 *      - The idea is that we have an interface that will dictate
 *      what the "generic" Remote Store should look like in terms of request/resposnse
 *      for the model data we want to Read/Write
 *
 *      - In terms of the "factory" part, Currently it is hardcoded in :
 *      private RemoteStoreInterface getCurrentRemoteStore();
 *
 *      - For a dynamic factory setup , future implementations can rely on a config var or even some user-admin screen,
 *      to change which server is used; and so it goes w/any other criteria that might arise for this
 *
 *      - To sum it up, will
 *      From RemoteStoreInterface, any service we use that implements the RemoteStoreInterface
 *      will be easy to swap in .. from one (eg Server1.java) to the next
 *
 *
 */
public class CommentActivity extends AppCompatActivity {

    Server1 server1 = new Server1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Button commentBtn = (Button) findViewById(R.id.btn_generate_comment);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Comment comment = new Comment();

                comment.generateRandomFakeComment();

                saveComment(comment);
            }
        });
    }

    /**
     * Centralized location where we can use a different Remote Store; eg, Parse.com
     *
     * @return
     */
    private RemoteStoreInterface getCurrentRemoteStore() {

        return server1;
    }

    /**
     * Simple wrapper to:
     * 1. save comment to Remote Store
     * 2. process the JSON reponse
     *
     * Caller decides which Remote Store implementation to use
     *
     * @param comment
     */
    private void saveComment(Comment comment) {

        RemoteStoreInterface currentRemoteStore = getCurrentRemoteStore();

        String saveResponseStr = null; // check if null for Exception case

        try {

            saveResponseStr = currentRemoteStore.saveComment(comment);
        }
        catch (Exception e) {

            e.printStackTrace();

            Log.e("UNEXPECTED", "Bad error! Do step-through debug");
        }

        if (saveResponseStr == null) {

            // Handle bad error/exception
            return;
        }

        JsonParser parser = new JsonParser();

        JsonObject saveResponseJson = parser.parse(saveResponseStr).getAsJsonObject();

        // process the response
    }
}
