package com.dungmac.quanlymonhoc.list_subject_of_grade;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dungmac.quanlymonhoc.R;
import com.dungmac.quanlymonhoc.model.Subject;

import java.io.File;
import java.util.ArrayList;

public class MySubjectAdapter extends BaseAdapter implements Filterable {
    //khai báo đối tượng Activity để xác định Activity chứa Listview
    private Activity activity;
    //khai báo đối tượng ArrayList<Subject> là nguồn dữ liệu cho Adapter
    private ArrayList<Subject> data;
    //khai báo đối tương LayoutInflater để phân tích giao diện cho một phần tử
    private LayoutInflater inflater;
    private ArrayList<Subject> databackup;

    public MySubjectAdapter(Activity activity, ArrayList<Subject> data) {
        this.activity = activity;
        this.data = data;
//        databackup = data;
        inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Subject> data) {
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
            v = inflater.inflate(R.layout.subject_item_layout, null);

            //tham chiếu tới textview để hiển thị tên
            TextView name = v.findViewById(R.id.subject_item_text);
            TextView count = v.findViewById(R.id.subject_item_text_count);
            ImageView img = v.findViewById(R.id.subject_item_image);
            CheckBox chkstatus = v.findViewById(R.id.checkBoxSubject);

            //thiết lập thuộc tính text của name là tên của phần tử thứ position
            name.setText(data.get(position).getName());
            count.setText(String.format("Số tiết: %s", data.get(position).getCount()));
            chkstatus.setChecked(data.get(position).getStatus());
            chkstatus.setOnClickListener(view -> data.get(position)
                    .setStatus(!data.get(position).getStatus()));
            if (data.get(position).getImagePath() != null) {
                File imgFile = new File(data.get(position).getImagePath());
                if (imgFile.exists()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    img.setImageBitmap(myBitmap);

                }
            }


        } else {
            TextView name = v.findViewById(R.id.subject_item_text);
            TextView count = v.findViewById(R.id.subject_item_text_count);
            ImageView img = v.findViewById(R.id.subject_item_image);
            CheckBox chkstatus = v.findViewById(R.id.checkBoxSubject);

            name.setText(data.get(position).getName());
            count.setText(String.format("Số tiết: %s", data.get(position).getCount()));
            chkstatus.setChecked(data.get(position).getStatus());
            if (data.get(position).getImagePath() != null) {
                File imgFile = new File(data.get(position).getImagePath());
                if (imgFile.exists()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    img.setImageBitmap(myBitmap);

                }
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
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
                    ArrayList<Subject> newdata = new ArrayList<>();
                    for (Subject u : databackup)
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
                data = new ArrayList<Subject>();
                ArrayList<Subject> tmp = (ArrayList<Subject>) filterResults.values;
                for (Subject u : tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
    }
}

