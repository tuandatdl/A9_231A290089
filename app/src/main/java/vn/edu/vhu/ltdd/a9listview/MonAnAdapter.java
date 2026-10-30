package vn.edu.vhu.ltdd.a9listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MonAnAdapter extends BaseAdapter {

    private static final String TAG = "A9_231A290089/Adapter";

    // Giữ sẵn tham chiếu View để tránh gọi findViewById lặp đi lặp lại
    private static class ViewHolder {
        ImageView imgAnh;
        TextView tvTen, tvMoTa, tvGia;
    }

    private final Context context;
    private final List<MonAn> danhSach;
    private final LayoutInflater inflater;

    public MonAnAdapter(Context context, List<MonAn> danhSach) {
        this.context = context;
        this.danhSach = danhSach;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return danhSach.size(); // Có bao nhiêu dòng?
    }

    @Override
    public Object getItem(int position) {
        return danhSach.get(position); // Dữ liệu tại vị trí position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Dòng mới hoàn toàn: Cần "thổi" layout từ XML (tốn tài nguyên)
            convertView = inflater.inflate(R.layout.item_mon_an, parent, false);
            holder = new ViewHolder();
            holder.imgAnh = convertView.findViewById(R.id.imgAnh);
            holder.tvTen = convertView.findViewById(R.id.tvTen);
            holder.tvMoTa = convertView.findViewById(R.id.tvMoTa);
            holder.tvGia = convertView.findViewById(R.id.tvGia);

            // Cất ViewHolder vào View bằng setTag
            convertView.setTag(holder);

            // Log phục vụ báo cáo: Đếm số lần inflate thực tế
            Log.d(TAG, "Inflate dòng mới tại vị trí: " + position);
        } else {
            // Tái sử dụng dòng vừa cuộn khỏi màn hình bằng getTag
            holder = (ViewHolder) convertView.getTag();
        }

        // BẮT BUỘC: Đổ dữ liệu ra NGOÀI nhánh if để tránh lỗi lẫn lộn nội dung
        MonAn mon = danhSach.get(position);
        holder.imgAnh.setImageResource(mon.getAnhResId());
        holder.tvTen.setText(mon.getTen());
        holder.tvMoTa.setText(mon.getMoTa());
        holder.tvGia.setText(MonAn.dinhDangGia(mon.getGia()));
        // Bài nâng cao NC2: Món có giá > 45.000 đ đổi nền cam nhạt, còn lại giữ nền trắng
        if (mon.getGia() > 45000) {
            convertView.setBackgroundColor(0xFFFFF3E0); // Cam nhạt nổi bật
        } else {
            convertView.setBackgroundColor(0xFFFFFFFF); // BẮT BUỘC gán trắng để không dính màu khi cuộn
        }

        return convertView;
    }
}