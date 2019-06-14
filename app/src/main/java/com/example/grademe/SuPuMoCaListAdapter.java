package com.example.grademe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.grademe.datatransferobject.SubPuMoCaDTO;
import com.example.grademe.domain.Module;
import com.example.grademe.domain.Pupil;

import java.util.List;

public class SuPuMoCaListAdapter extends BaseAdapter {

    private List<SubPuMoCaDTO> subPuMoCaDTOS;
    Context context;

    private static LayoutInflater inflater = null;
    public SuPuMoCaListAdapter(Context context, List<SubPuMoCaDTO> subPuMoCaDTOS){
        this.context = context;
        this.subPuMoCaDTOS = subPuMoCaDTOS;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subPuMoCaDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return subPuMoCaDTOS.get(position).getPupil();
    }

    @Override
    public long getItemId(int position) {
        return subPuMoCaDTOS.get(position).getPupil().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.pupils_list_row, null);
        TextView header = (TextView) vi.findViewById(R.id.header);
        header.setText(subPuMoCaDTOS.get(position).getPupil().getFirstName() + " " + subPuMoCaDTOS.get(position).getPupil().getLastName());
        TextView rowContentLeft = (TextView) vi.findViewById(R.id.rowContentLeft);
        rowContentLeft.setText("Email : " + subPuMoCaDTOS.get(position).getPupil().getEmail());
        return vi;
    }
}
