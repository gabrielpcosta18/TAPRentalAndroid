package br.edu.ufam.icomp.taprental.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import br.edu.ufam.icomp.taprental.model.Rental;

/**
 * Created by gabri on 30/01/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter {
    private List<?> _items;

    public CustomSpinnerAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomSpinnerAdapter(Context context, int resource, List<?> items) {
        super(context, resource, items);
        this._items = items;
    }

    public List<?> getItemsList() {
        return  this._items;
    }
}
