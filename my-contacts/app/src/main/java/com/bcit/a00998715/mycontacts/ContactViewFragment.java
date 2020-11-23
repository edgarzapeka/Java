package com.bcit.a00998715.mycontacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcit.a00998715.mycontacts.data.Contact;
import com.bcit.a00998715.mycontacts.db.DBAdapter;

/**
 * Created by edz on 2018-03-02.
 */

public class ContactViewFragment extends Fragment {

    public static String CONTACT_KEY = "COTNACT_KEY";

    private Contact contact;
    private DBAdapter db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = new DBAdapter(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_view, container, false);

        fetchContact();

        if (contact == null){
            getActivity().getSupportFragmentManager().popBackStack();
        }

        ((TextView)v.findViewById(R.id.first_name_val)).setText(contact.getFirstName());
        ((TextView)v.findViewById(R.id.last_name_val)).setText(contact.getLastName());
        ((TextView)v.findViewById(R.id.city_val)).setText(contact.getCity());
        ((TextView)v.findViewById(R.id.street_address_val)).setText(contact.getStreetAddress());
        ((TextView)v.findViewById(R.id.province_val)).setText(contact.getProvince());
        ((TextView)v.findViewById(R.id.postal_code_val)).setText(contact.getPostalCode());
        ((TextView)v.findViewById(R.id.email_val)).setText(contact.getEmail());
        ((TextView)v.findViewById(R.id.phone_number_val)).setText(contact.getPhoneNumber());

        ((ImageView)v.findViewById(R.id.delete_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage(String.format("Are you sure you want to delete %s %s contact?", contact.getFirstName(), contact.getLastName()));
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.open();
                                db.deleteContact(contact.getId());
                                db.close();
                                dialog.cancel();
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        ((ImageView)v.findViewById(R.id.edit_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditContactFragment f = new EditContactFragment();
                Bundle bundle = new Bundle();
                bundle.putLong(ContactViewFragment.CONTACT_KEY, contact.getId());
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, f).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

            }
        });

        return v;
    }

    private void fetchContact(){
        db.open();
        Cursor c = db.getContact(getArguments().getLong(CONTACT_KEY));
        if (c.moveToFirst()){
            contact = new Contact(
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8)
            );
            contact.setId(c.getLong(0));
        }
        db.close();
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_back, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back_fragment:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
