package com.example.tv360.ui.TV;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tv360.adapter.RvAdapter;
import com.example.tv360.adapter.RvTVAdapter;
import com.example.tv360.databinding.FragmentTvBinding;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.HomePresenter;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.google.android.exoplayer2.ExoPlayer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    ExoPlayer player;
    private FragmentTvBinding binding;

    RvTVAdapter tvAdapter;

    HomeService apiInterface;

    List<HomeModel> listitem = new ArrayList<HomeModel>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentTvBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.viewtv.setLayoutManager(new LinearLayoutManager(requireContext()));
        GetData();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void GetData() {
        apiInterface = ApiService.getClient().create(HomeService.class);
        Call<DataObject> data = apiInterface.getTVBox();

        data.enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {

                DataObject dataObject = response.body();
                tvAdapter=new RvTVAdapter(getContext(), dataObject.getData());
                binding.viewtv.setAdapter(tvAdapter);
            }

            @Override
            public void onFailure(Call<DataObject> call, Throwable throwable) {

                call.cancel();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}