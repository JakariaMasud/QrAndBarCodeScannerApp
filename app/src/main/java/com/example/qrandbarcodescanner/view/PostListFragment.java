package com.example.qrandbarcodescanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qrandbarcodescanner.R;
import com.example.qrandbarcodescanner.adapters.PostAdapter;
import com.example.qrandbarcodescanner.databinding.FragmentPostListBinding;
import com.example.qrandbarcodescanner.models.Post;
import com.example.qrandbarcodescanner.viewmodels.PostViewModel;

import java.util.List;


public class PostListFragment extends Fragment {
  FragmentPostListBinding binding;
    RecyclerView.LayoutManager layoutManager;
    PostAdapter adapter;
    PostViewModel postViewModel;
    NavController navController;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentPostListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        layoutManager= new LinearLayoutManager(getActivity());
        binding.postRV.setLayoutManager(layoutManager);
        binding.postRV.setHasFixedSize(true);
        postViewModel=new ViewModelProvider(requireActivity()).get(PostViewModel.class);
        postViewModel.getPostsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                binding.postlistPB.setVisibility(View.GONE);
                    adapter=new PostAdapter(posts);
                    binding.postRV.setAdapter(adapter);

            }
        });

    }
}