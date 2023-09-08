package com.example.tv360.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tv360.adapter.RvAdapter;
import com.example.tv360.databinding.FragmentHomeBinding;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.ListFilmModel;
import com.example.tv360.presenter.HomePresenter;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RvAdapter rvAdapter;
    final List<HomeModel> listitem = new ArrayList<HomeModel>();
    List<FilmModel> listitembanner = new ArrayList<FilmModel>();
    HomeService apiInterface;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.viewhome.setLayoutManager(new LinearLayoutManager(requireContext()));
        GetData();
        return root;
    }

    private void GetData(){
         HomePresenter homePresenter= new HomePresenter();
        apiInterface = ApiService.getClient().create(HomeService.class);
        Call<DataObject> data = apiInterface.getHomeBox();

        data.enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {

                DataObject dataObject = response.body();
                for (HomeModel homeModel : dataObject.getData()) {
                    listitem.add(homeModel);
                }
                rvAdapter=new RvAdapter(getContext(), listitem);
                binding.viewhome.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<DataObject> call, Throwable throwable) {
                Toast.makeText(getContext(), "error"  , Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

//        apiInterface = ApiService.getClient().create(HomeService.class);
//        Call<DataObject> data = apiInterface.getHomeBox();
//       data.enqueue(new Callback<DataObject>() {
//          @Override
//           public void onResponse(Call<DataObject> call, Response<DataObject> response) {
//               DataObject dataObject = response.body();
//               Toast.makeText(getContext(), "success" + dataObject.getMessage()  , Toast.LENGTH_SHORT).show();
//               if (response.isSuccessful() && response.body() != null) {
//                   List<HomeModel> listdata = response.body();
//
//                   Toast.makeText(getContext(), "" + listdata.size(), Toast.LENGTH_SHORT).show();
//                   for (int i = 0; i < listdata.size(); i++) {
//                       for (FilmModel l : listdata.get(i).getContent()) {
//
//                       }
//                   }
//               } else {
//                   Toast.makeText(getContext(), "nỗi ", Toast.LENGTH_SHORT).show();
//               }
//           }
//
//           @Override
//           public void onFailure(Call<List<HomeModel>> call, Throwable throwable) {
//
//           }
//       });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}