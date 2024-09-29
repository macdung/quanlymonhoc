package com.dungmac.quanlymonhoc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyGradeAdapter extends BaseAdapter implements Filterable {
    //khai báo đối tượng Activity để xác định Activity chứa Listview
    private Activity activity;
    //khai báo đối tượng ArrayList<Grade> là nguồn dữ liệu cho Adapter
    private ArrayList<Grade> data;
    //khai báo đối tương LayoutInflater để phân tích giao diện cho một phần tử
    private LayoutInflater inflater;
    private ArrayList<Grade> databackup;

    public MyGradeAdapter(Activity activity, ArrayList<Grade> data) {
        this.activity = activity;
        this.data = data;
//        databackup = data;
        inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Grade> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.grade_item_layout, null);

            //sau đó có thể thay đổi ảnh cho phần tử bằng đường dẫn ở đây
            //tham chiếu tới textview để hiển thị tên
            TextView name = v.findViewById(R.id.grade_item_text);
            //thiết lập thuộc tính text của name là tên của phần tử thứ position
            name.setText(data.get(position).getName());
        } else {
            TextView name = v.findViewById(R.id.grade_item_text);
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if (databackup == null)
                    databackup = new ArrayList<>(data);
                //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                if (charSequence == null || charSequence.length() == 0) {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                //Còn nếu không rỗng thì thực hiện filter
                else {
                    ArrayList<Grade> newdata = new ArrayList<>();
                    for (Grade u : databackup)
                        if (u.getName().toLowerCase().contains(
                                charSequence.toString().toLowerCase()))
                            newdata.add(u);
                    fr.count = newdata.size();
                    fr.values = newdata;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                data = new ArrayList<Grade>();
                ArrayList<Grade> tmp = (ArrayList<Grade>) filterResults.values;
                for (Grade u : tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}

