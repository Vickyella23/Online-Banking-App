package com.e.mobilebanking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class History extends Fragment {


    private RecyclerView recyclerView;
    private List<String> historyList = new ArrayList<>();
    private ProgressBar progressBar;

    public History() {
        // Required empty public constructor
    }


    public static History newInstance() {
        return new History();
    }
    private void setHistoryList(BackendlessUser user) {
        if (user.getProperty("debit_history") != null) {
            String transactionHistory = user.getProperty("debit_history").toString();
            String[] split = transactionHistory.split("-");
            for (String s : split) {
                if (s.equals("null"))
                    continue;
                historyList.add(s);
            }
        }
        onResume();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userObjectId = UserIdStorageFactory.instance().getStorage().get();

        Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                setHistoryList(response);

                //refresh the view with new data...
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_history);
        progressBar = rootView.findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();


        if (!historyList.isEmpty())
            progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new HistoryAdapter(historyList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
    }
}
