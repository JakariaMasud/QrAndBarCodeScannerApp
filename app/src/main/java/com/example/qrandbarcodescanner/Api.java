package com.example.qrandbarcodescanner;

import com.example.qrandbarcodescanner.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL="https://jsonplaceholder.typicode.com/";

    @GET("/posts")
    Call<List<Post>> getPosts();
}
