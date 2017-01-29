package br.edu.ufam.icomp.taprental.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.model.Rental;

/**
 * Created by gabri on 29/01/2017.
 */

public class RentListAdapter extends ArrayAdapter<Rental> {

    public RentListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RentListAdapter(Context context, int resource, List<Rental> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.rental_list_item, null);
        }

        Rental p = getItem(position);
        Log.d("Rent", p.getProduct().toString());
        if (p != null) {
            TextView txtRentalTitle= (TextView) v.findViewById(R.id.txtRentalTitle);
            TextView txtRentalCustomerName = (TextView) v.findViewById(R.id.txtRentalCustomerName);
            TextView txtRentalRemaniningDays = (TextView) v.findViewById(R.id.txtRentalRemaniningDays);

            if (txtRentalTitle!= null) {
                txtRentalTitle.setText(p.getProduct().getTitle());
            }

            if (txtRentalCustomerName != null) {
                txtRentalCustomerName.setText(p.getCustomer().getName());
            }

            if (txtRentalRemaniningDays != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                Date strDate = null;
                try {
                    strDate = format.parse(p.getRentDate());
                    Date today = new Date();
                    long diff = (long)today.getTime() - strDate.getTime();
                    diff = p.getProduct().getMaxPeriodRent() - TimeUnit.MILLISECONDS.toDays(diff);

                    txtRentalRemaniningDays.setText(Long.toString(diff));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return v;
    }

}