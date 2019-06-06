package com.example.grademe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.grademe.domain.Module;
import com.example.grademe.domain.Pupil;

import java.util.List;

public class PupilListAdapter extends BaseAdapter {

    private List<Pupil> pupils;
    Context context;

    private static LayoutInflater inflater = null;
    public PupilListAdapter(Context context, List<Pupil> pupils){
        this.context = context;
        this.pupils = pupils;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pupils.size();
    }

    @Override
    public Object getItem(int position) {
        return pupils.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pupils.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.pupils_list_row, null);
        TextView header = (TextView) vi.findViewById(R.id.header);
        header.setText(pupils.get(position).getFirstName() + " " + pupils.get(position).getLastName());
        TextView rowContentLeft = (TextView) vi.findViewById(R.id.rowContentLeft);
        rowContentLeft.setText("Lehrer: " + pupils.get(position).getCategoryRatingList().size() + " Noteneinträge");
        return vi;
    }
}
