package com.bcit.a00998715.mycontacts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bcit.a00998715.mycontacts.data.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edz on 2018-03-02.
 */

public class ContactListFragment extends Fragment {

    ContactListAdapter adapter;
    ListView list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

        list = v.findViewById(R.id.contact_list);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());

        adapter = new ContactListAdapter(contacts, getContext());
        list.setAdapter(adapter);

        return v;
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
        firstName.setText("Hello world");
        TextView lastName = view.findViewById(R.id.last_name);
        lastName.setText("You hello");

        return view;
    }


}
