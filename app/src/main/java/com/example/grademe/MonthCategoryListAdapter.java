package com.example.grademe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.grademe.datatransferobject.MonthCategoryDTO;
import com.example.grademe.domain.CategoryRating;
import com.example.grademe.domain.Pupil;

import java.util.List;

public class MonthCategoryListAdapter extends BaseAdapter {

    private List<MonthCategoryDTO> grades;
    Context context;

    private static LayoutInflater inflater = null;
    public MonthCategoryListAdapter(Context context, List<MonthCategoryDTO> grades){
        this.context = context;
        this.grades = grades;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return grades.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.category_rating_list_row, null);
        TextView header = (TextView) vi.findViewById(R.id.header);
//        header.setText("Lehrerbewertung: " + grades.get(position).getRatingTeacher() + " Schülerselbstbewertung: " + grades.get(position).getRatingPupil());
//        TextView rowContentLeft = (TextView) vi.findViewById(R.id.rowContentLeft);
//        rowContentLeft.setText("Noteneinträge : " + grades.get(position).getCategoryRatingList().size());
        return vi;
    }
}
