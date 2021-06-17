package com.example.qrandbarcodescanner.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.qrandbarcodescanner.Api;
import com.example.qrandbarcodescanner.models.Post;
import com.example.qrandbarcodescanner.repositories.PostRepository;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostViewModel extends ViewModel {
    PostRepository repository;
    public PostViewModel(){
        repository=new PostRepository();
    }

    public LiveData<List<Post>> getPostsLiveData() {
        return repository.getPostsLiveData() ;
    }

    public   void loadPosts(){
        repository.loadPosts();

    }
}
