package com.example.grademe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.grademe.datatransferobject.CategoryRatingDTO;
import com.example.grademe.datatransferobject.MonthCategoryDTO;

import java.util.List;


public class CategoryRatingListAdapter extends BaseAdapter {

    private List<CategoryRatingDTO> categoryRatingDTOS;
    Context context;

    private static LayoutInflater inflater = null;
    public CategoryRatingListAdapter(Context context, List<CategoryRatingDTO> categoryRatingDTOS){
        this.context = context;
        this.categoryRatingDTOS = categoryRatingDTOS;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryRatingDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryRatingDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryRatingDTOS.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.category_rating_list_row, null);
            TextView header = (TextView) vi.findViewById(R.id.header);
            header.setText("Schülernote: " + String.valueOf(categoryRatingDTOS.get(position).getRatingPupil().getNoteAsInt())
                    + "Kommentar Schüler: " + categoryRatingDTOS.get(position).getCommentPupil()
                    + "Lehrernote: " + String.valueOf(categoryRatingDTOS.get(position).getRatingTeacher().getNoteAsInt())
                    + "Kommentar Lehrer: " + categoryRatingDTOS.get(position).getCommentTeacher());
//        TextView rowContentLeft = (TextView) vi.findViewById(R.id.rowContentLeft);
//        rowContentLeft.setText("Noteneinträge : " + grades.get(position).getCategoryRatingList().size());
        return vi;
    }
}
