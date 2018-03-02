package com.bcit.a00998715.mycontacts;

import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bcit.a00998715.mycontacts.data.Contact;
import com.bcit.a00998715.mycontacts.db.DBAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edz on 2018-03-02.
 */

public class ContactListFragment extends Fragment {

    ContactListAdapter adapter;
    ListView list;
    List<Contact> contacts;
    DBAdapter db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        contacts = new ArrayList<>();
        db = new DBAdapter(getContext());
    }

    private void fetchContacts(){
        contacts = new ArrayList<>();
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()){
            do{
                Contact contact = new Contact(
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
                contacts.add(contact);
            }while (c.moveToNext());
        }
        db.close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
        fetchContacts();
        list = v.findViewById(R.id.contact_list);

        adapter = new ContactListAdapter(contacts, getContext());
        list.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_contact:
                this.getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddContactFragment()).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

class ContactListAdapter extends BaseAdapter{

    private List<Contact> contacts;
    private Context context;

    public ContactListAdapter(List<Contact> contacts, Context context){
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        //return getItem(i).getId();
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (view == null){
            view = inflater.inflate(R.layout.contact_list_item, viewGroup, false);
        }

        TextView firstName = view.findViewById(R.id.first_name);
        TextView lastName = view.findViewById(R.id.last_name);
        TextView city = view.findViewById(R.id.city_val);
        TextView streetAddress = view.findViewById(R.id.street_address_val);
        TextView province = view.findViewById(R.id.province_val);
        TextView postalCode = view.findViewById(R.id.postal_code_val);
        TextView email = view.findViewById(R.id.email_val);
        TextView phoneNumber = view.findViewById(R.id.phone_number_val);

        firstName.setText(getItem(i).getFirstName());
        lastName.setText(getItem(i).getLastName());
        city.setText(getItem(i).getCity());
        streetAddress.setText(getItem(i).getStreetAddress());
        province.setText(getItem(i).getProvince());
        postalCode.setText(getItem(i).getPostalCode());
        email.setText(getItem(i).getEmail());
        phoneNumber.setText(getItem(i).getPhoneNumber());

        return view;
    }
}
