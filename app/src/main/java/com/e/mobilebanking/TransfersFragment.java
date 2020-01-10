package com.e.mobilebanking;


import android.app.Activity;
import android.app.Person;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityManagerCompat;
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
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.List;
//import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




public class TransfersFragment extends Fragment  {


    private class VictoriaBackendlessUser extends BackendlessUser {
        public double account_balance;

        public void setAccountBalance(double bl) {
            this.account_balance = bl;
        }

        public double getAccountBalance() {
            return this.account_balance;
        }
    }


    public static final String APPLICATION_ID = "1B318336-3978-19F1-FF92-C04EC1B11C00";
    public static final String API_KEY = "6FA245E9-13D0-4961-81EF-3DED01603C55";
    public String SERVER_URL = "https://api.backendless.com";
    public BackendlessUser debit;
    public BackendlessUser credit;
    private int debitAmount;
//    public VictoriaBackendlessUser credit;

    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.beneficiary_account)
    EditText beneficiaryAccountNumber;
    @BindView(R.id.amount_to_send)
    EditText amountToSend;


    public TransfersFragment() {
    }

    public void setCredit(VictoriaBackendlessUser credit) { this.credit = credit;
    }
    public void setCredit(BackendlessUser credit) {
        this.credit = credit;
    }


    public void setDebit(BackendlessUser debit) {
        this.debit = debit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Backendless.setUrl(SERVER_URL );
        Backendless.initApp( getActivity().getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
        String userObjectId = UserIdStorageFactory.instance().getStorage().get();
        Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                setDebit(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        View view = inflater.inflate(R.layout.fragment_transfers, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public double convertToDouble(String val) {
        // convert string to double
//        String doubled = String.valueOf(val);
        double d = 0.0;
        try {
            d = Double.parseDouble(val);
        }catch (Exception ex) {

        }
        return d;
    }


    @OnClick(R.id.send_button)
    void setSendButton() {
//        Toast.makeText( getActivity(), this.debit.getEmail(), Toast.LENGTH_SHORT ).show();
//        Toast.makeText( getActivity(), beneficiaryAccountNumber.getText(), Toast.LENGTH_SHORT ).show();
        if (TextUtils.isEmpty(beneficiaryAccountNumber.getText()))
        {
            Toast.makeText(getActivity(), "Enter valid email address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(amountToSend.getText()))
        {
            Toast.makeText(getActivity(), "Enter amount to send", Toast.LENGTH_SHORT).show();
        }
        else {
            String userObjectId = UserIdStorageFactory.instance().getStorage().get();

            String whereClause = String.format("email = '%s'",beneficiaryAccountNumber.getText());
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause(whereClause);


            Backendless.Data.of(BackendlessUser.class).find( queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
                @Override
                public void handleResponse(List<BackendlessUser> response) {
                    if ( response != null && !response.isEmpty() ){
                        Toast.makeText(getContext(), response.get(0).getEmail(), Toast.LENGTH_SHORT).show();
                        BackendlessUser _credit = response.get(0);
                        setCredit(_credit);
                        debitAmount = Integer.valueOf(amountToSend.getText().toString());
                        int account = (int) debit.getProperty("account_balance");
                        int accountBalance = account - debitAmount;
                        debit.setProperty("account_balance", accountBalance);
                        _credit.setProperty("account_balance", (int) _credit.getProperty("account_balance") + debitAmount);
                        Backendless.UserService.update( debit, new AsyncCallback<BackendlessUser>()
                        {
                            public void handleResponse( BackendlessUser user )
                            {
                                // user has been updated
                                Toast.makeText(getContext(), "new account balance = " + user.getProperty("account_balance"), Toast.LENGTH_LONG).show();
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                // user update failed, to get the error code call fault.getCode()
                            }
                        });
                        Backendless.UserService.update( _credit, new AsyncCallback<BackendlessUser>()
                        {
                            public void handleResponse( BackendlessUser user )
                            {
                                // user has been updated
                                //Toast.makeText(getContext(), "new account balance = " + debit.getProperty("account_balance"), Toast.LENGTH_LONG).show();
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                // user update failed, to get the error code call fault.getCode()
                            }
                        });

                    }

                    else{

                    }


                }


                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });



//            Backendless.Data.of(BackendlessUser.class).findFirst(queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
//                @Override
//                public void handleResponse(List<BackendlessUser> usersFound) {
//                    Toast.makeText(getActivity(), usersFound.size(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void handleFault(BackendlessFault fault) {
//                    // an error has occurred, the error code can be retrieved with fault.getCode()
//                }
//            });
//

//            String email = beneficiaryAccountNumber.getText().toString().trim();
//            String amount = amountToSend.getText().toString().trim();
//            debit.setProperty( 'account_balance', debit.account_balance - amountToSend.getText() );

//            credit.setProperty( 'account_balance', debit.account_balance + amountToSend.getText() );


//            Bank bank = new Bank();
//            bank.setEmail(email);
//            bank.setAmount(amount);
//            bank.setUserEmail(TestApplication.user);


        }

    }

//    @OnClick(R.id.beneficiary_account)
//    void setBeneficiaryAccountNumber() {
//
//
//
//
//    }
//
   @OnClick(R.id.amount_to_send)
   void setAmountToSend() {

   }

    public int getDebitAmount() {
        return debitAmount;
    }
}
