package com.example.grademe;

import android.content.Context;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.grademe.datatransferobject.SubjectDTO;
import com.example.grademe.domain.Module;

import java.util.List;

public class SubjectsListAdapter extends BaseAdapter {

    private List<SubjectDTO> modules;
    Context context;

    private static LayoutInflater inflater = null;
    public SubjectsListAdapter(Context context, List<SubjectDTO> modules){
        this.context = context;
        this.modules = modules;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return modules.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.module_list_row, null);
        TextView header = (TextView) vi.findViewById(R.id.header);
        header.setText(modules.get(position).getName());
        TextView rowContentLeft = (TextView) vi.findViewById(R.id.rowContentLeft);
//        rowContentLeft.setText("Lehrer: " + modules.get(position).getTeacher().getUserDTO().getLastName());
        return vi;
    }
}
