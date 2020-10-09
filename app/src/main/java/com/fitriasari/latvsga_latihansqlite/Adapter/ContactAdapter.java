package com.fitriasari.latvsga_latihansqlite.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fitriasari.latvsga_latihansqlite.Model.Contact;
import com.fitriasari.latvsga_latihansqlite.R;

import java.util.Date;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<Contact> contactList;

    public ContactAdapter(Activity activity, List<Contact> contactList) {
        this.activity = activity;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_contact,null);

        TextView id = (TextView) convertView.findViewById(R.id.textID);
        TextView name = (TextView) convertView.findViewById(R.id.textName);
        TextView phone_number = (TextView) convertView.findViewById(R.id.textPhoneNo);
        TextView address = (TextView) convertView.findViewById(R.id.textAddress);

        Contact contact = contactList.get(position);

        id.setText(contact.get_id());
        name.setText(contact.get_name());
        phone_number.setText(contact.get_phone_number());
        address.setText(contact.get_address());

        return convertView;
    }
}
