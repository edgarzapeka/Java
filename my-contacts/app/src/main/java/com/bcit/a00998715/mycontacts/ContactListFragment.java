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
import android.widget.AdapterView;
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
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact c = (Contact)adapterView.getItemAtPosition(i);
                ContactViewFragment f = new ContactViewFragment();
                Bundle bundle = new Bundle();
                bundle.putLong(ContactViewFragment.CONTACT_KEY, c.getId());
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, f).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });

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
        return getItem(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (view == null){
            view = inflater.inflate(R.layout.contact_list_item, viewGroup, false);
        }

        ((TextView)view.findViewById(R.id.first_name)).setText(getItem(i).getFirstName());
        ((TextView)view.findViewById(R.id.last_name)).setText(getItem(i).getLastName());
        ((TextView)view.findViewById(R.id.phone_number)).setText(getItem(i).getPhoneNumber());

        return view;
    }
}
