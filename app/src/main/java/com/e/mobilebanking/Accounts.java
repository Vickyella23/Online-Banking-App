package com.e.mobilebanking;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Accounts extends Fragment {

    @BindView(R.id.history)
    Button history;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.range)
    Button range;

    Button currentButton;
    private FragmentTransaction transaction;
    public Accounts() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);
        ButterKnife.bind(this, rootView);
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
