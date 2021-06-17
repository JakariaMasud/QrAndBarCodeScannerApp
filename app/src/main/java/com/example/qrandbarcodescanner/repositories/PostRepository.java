package com.example.qrandbarcodescanner.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.qrandbarcodescanner.Api;
import com.example.qrandbarcodescanner.RetrofitInstance;
import com.example.qrandbarcodescanner.models.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    MutableLiveData<List<Post>>postsLiveData= new MutableLiveData<>();

    public LiveData<List<Post>> getPostsLiveData() {
        return postsLiveData;
    }

    public void loadPosts(){

        Api api = RetrofitInstance.getApiService();
        api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    postsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("info","failed to fetch posts");

            }
        });
    }
}
