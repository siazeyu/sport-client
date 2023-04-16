package com.sport.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sport.R;
import org.w3c.dom.Text;

import java.util.Collection;
import java.util.List;

public class RecordListViewAdapter extends ArrayAdapter<RecordListViewModel> {

    private List<RecordListViewModel> modelList;

    public RecordListViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.record_item, parent, false);
            TextView time_t = itemView.findViewById(R.id.time_t);
            TextView km_t = itemView.findViewById(R.id.km_t);
            TextView cal_t = itemView.findViewById(R.id.cal_t);

            ViewHolder viewHolder = new ViewHolder(time_t, km_t, cal_t);
            itemView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) itemView.getTag();
        RecordListViewModel model = modelList.get(position);
        viewHolder.time.setText(model.getTime());
        viewHolder.km.setText(model.getKm().toString());
        viewHolder.cal.setText(model.getCal());

        return itemView;
    }

    @Override
    public void addAll(@NonNull Collection<? extends RecordListViewModel> collection) {
        super.clear();
        super.addAll(collection);
    }

    public void setModelList(List<RecordListViewModel> modelList) {
        this.modelList = modelList;
        addAll(modelList);
    }

    private class ViewHolder {
        private TextView time;
        private TextView km;
        private TextView cal;

        public ViewHolder(TextView time, TextView km, TextView cal) {
            this.time = time;
            this.km = km;
            this.cal = cal;
        }
    }
}