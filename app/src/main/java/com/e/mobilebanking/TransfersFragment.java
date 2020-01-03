package com.e.mobilebanking;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TransfersFragment extends Fragment {

    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.beneficiary_account)
    EditText beneficiaryAccountNumber;
    @BindView(R.id.amount_to_send)
    EditText amountToSend;

    public TransfersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transfers, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.send_button)
    void setSendButton() {

        if (TextUtils.isEmpty(beneficiaryAccountNumber.getText()))
        {
            Toast.makeText(getActivity(), "Enter valid email address", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(amountToSend.getText()))
        {
            Toast.makeText(getActivity(), "Enter amount to send", Toast.LENGTH_SHORT).show();
        }
        else {
            String whereClause = String.format("email = '%s'",beneficiaryAccountNumber.getText());
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause(whereClause);

            Backendless.Data.of("APP TABLES").find(queryBuilder, new AsyncCallback<List<Map>>() {
                        @Override
                        public void handleResponse(List<Map> foundContacts) {
                            Toast.makeText(getActivity(), foundContacts.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            // an error has occurred, the error code can be retrieved with fault.getCode()
                        }
                    });
        }
    }

    @OnClick(R.id.beneficiary_account)
    void setBeneficiaryAccountNumber() {

    }

    @OnClick(R.id.amount_to_send)
    void setAmountToSend() {


    }
}
