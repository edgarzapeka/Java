package com.bcit.a00998715.mycontacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bcit.a00998715.mycontacts.data.Contact;
import com.bcit.a00998715.mycontacts.db.DBAdapter;

/**
 * Created by edz on 2018-03-02.
 */

public class AddContactFragment extends Fragment {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPhoneNumber;
    private EditText mPostalCode;
    private EditText mStreetAddress;
    private EditText mCity;
    private EditText mProvince;

    private Button mSave;
    private Button mCancel;

    private DBAdapter db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = new DBAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_contact, container, false);

        mFirstName = v.findViewById(R.id.first_name_edit);
        mLastName = v.findViewById(R.id.last_name_edit);
        mEmail = v.findViewById(R.id.email_edit);
        mPhoneNumber = v.findViewById(R.id.phone_number_edit);
        mPostalCode = v.findViewById(R.id.postal_code_edit);
        mStreetAddress = v.findViewById(R.id.street_address_edit);
        mCity = v.findViewById(R.id.city_edit);
        mProvince = v.findViewById(R.id.province_edit);

        mSave = v.findViewById(R.id.save_button);
        mCancel = v.findViewById(R.id.cancel_button);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c = new Contact(
                        mFirstName.getText().toString(),
                        mLastName.getText().toString(),
                        mPhoneNumber.getText().toString(),
                        mEmail.getText().toString(),
                        mStreetAddress.getText().toString(),
                        mCity.getText().toString(),
                        mProvince.getText().toString(),
                        mPostalCode.getText().toString());
                db.open();
                db.insertContact(c);
                db.close();

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return v;
    }
}
