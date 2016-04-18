package com.demo.commentapp.remotestore;


import com.demo.commentapp.model.Comment;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Server1 implements RemoteStoreInterface {

    final static String BASE_COMMENT_URL = "http://somewhere.com";

    final static String SAVE_COMMENT_URL = BASE_COMMENT_URL + "/comment/save";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;

    private Request request;

    /**
     * A convenience wrapper-accessor, so we know exactly what we are saving and what url
     *
     * @param comment
     * @return
     * @throws IOException
     */
    public String saveComment(Comment comment) throws IOException {

        String responseStr = saveSerializable(comment, SAVE_COMMENT_URL);

        return responseStr;
    }

    protected String saveSerializable(Object dataToSave, String url) throws IOException {

        Gson gson = new Gson();

        String dataJsonStr = gson.toJson(dataToSave);

        RequestBody body = RequestBody.create(JSON, dataJsonStr);

        client = new OkHttpClient();

        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
