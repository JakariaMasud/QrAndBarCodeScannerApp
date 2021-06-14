package com.example.qrandbarcodescanner.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.qrandbarcodescanner.Api;
import com.example.qrandbarcodescanner.models.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostViewModel extends ViewModel {
    private MutableLiveData<List<Post>>postsLiveData=new MutableLiveData<>();

    public LiveData<List<Post>> getPostsLiveData() {
        return postsLiveData;
    }

    public PostViewModel() {
        loadPosts();
    }

    public   void loadPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
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
