package com.example.grademe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.grademe.datatransferobject.MonthCategoryDTO;
import com.example.grademe.datatransferobject.SubPuMoCaDTO;
import com.example.grademe.domain.CategoryRating;
import com.example.grademe.domain.Pupil;

import java.util.List;

//SCHUELERLISTE
public class SubMonthCategoryListAdapter extends BaseAdapter {

    private List<SubPuMoCaDTO> monthCategoryDTOS;
    Context context;

    private static LayoutInflater inflater = null;
    public SubMonthCategoryListAdapter(Context context, List<SubPuMoCaDTO> monthCategoryDTOS){
        this.context = context;
        this.monthCategoryDTOS = monthCategoryDTOS;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return monthCategoryDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return monthCategoryDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monthCategoryDTOS.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.category_rating_list_row, null);
            TextView header = (TextView) vi.findViewById(R.id.header);
            header.setText(monthCategoryDTOS.get(position).getPupil().getLastName()+ " " + monthCategoryDTOS.get(position).getPupil().getEmail() );
//        TextView rowContentLeft = (TextView) vi.findViewById(R.id.rowContentLeft);
//        rowContentLeft.setText("Noteneinträge : " + grades.get(position).getCategoryRatingList().size());
        return vi;
    }
}
