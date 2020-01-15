package com.e.mobilebanking;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Accounts extends Fragment {

    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.history)
    Button history;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.range)
    Button range;
    public BackendlessUser debit;


    Button currentButton;
    private FragmentTransaction transaction;
    public List<String> historyList = new ArrayList<>();

    public Accounts() {
        // Required empty public constructor
    }

    public void setDebit(BackendlessUser debit) {
        this.debit = debit;
        this.balance.setText(debit.getProperty("account_balance").toString());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);
        ButterKnife.bind(this, rootView);

        String userObjectId = UserIdStorageFactory.instance().getStorage().get();

        Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText( getActivity(), response.getEmail(), Toast.LENGTH_SHORT ).show();
                setDebit(response);

                //refresh the view with new data...
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        currentButton = history;
        history.setTextColor(getResources().getColor(R.color.white));
        history.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, History.newInstance()).commitNow();

    }


    @OnClick(R.id.history)
    void setHistory() {
        currentButton.setTextColor(getResources().getColor(R.color.black));
        currentButton.setBackgroundColor(getResources().getColor(R.color.grey));
        currentButton = history;
        history.setTextColor(getResources().getColor(R.color.white));
        history.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, History.newInstance()).commitNow();

    }

    @OnClick(R.id.search)
    void setSearch() {
        currentButton.setTextColor(getResources().getColor(R.color.black));
        currentButton.setBackgroundColor(getResources().getColor(R.color.grey));
        currentButton = search;
        search.setTextColor(getResources().getColor(R.color.white));
        search.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, Search.newInstance()).commitNow();
    }

    @OnClick(R.id.range)
    void setRange() {
        currentButton.setTextColor(getResources().getColor(R.color.black));
        currentButton.setBackgroundColor(getResources().getColor(R.color.grey));
        currentButton = range;
        range.setTextColor(getResources().getColor(R.color.white));
        range.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, Range.newInstance()).commitNow();
    }

    @OnClick(R.id.balance)
    public void setBalance() {
/*

        debit.setProperty( "balance", debit.account_balance - amountToSend.getText() );

        credit.setProperty( "balance", debit.account_balance + amountToSend.getText() );
*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



}
