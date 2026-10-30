package vn.edu.vhu.ltdd.a9listview;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A9_231A290089";

    private final List<MonAn> danhSach = new ArrayList<>();
    private MonAnAdapter adapter;
    private ListView lvMonAn;
    private TextView tvTrong;
    private TextView tvThongKe;
    private EditText edtTen, edtGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        lvMonAn = findViewById(R.id.lvMonAn);
        tvTrong = findViewById(R.id.tvTrong);
        tvThongKe = findViewById(R.id.tvThongKe);
        edtTen = findViewById(R.id.edtTen);
        edtGia = findViewById(R.id.edtGia);
        Button btnThem = findViewById(R.id.btnThem);

        // 1. Tạo 5 món ăn mẫu
        taoDuLieuMau();

        // 2. Gán adapter vào ListView
        adapter = new MonAnAdapter(this, danhSach);
        lvMonAn.setAdapter(adapter);
        capNhatThongKe(); // Tính toán lần đầu

        // 3. Cơ chế tự động hiện TextView thay thế khi danh sách rỗng
        lvMonAn.setEmptyView(tvTrong);

        // 4. Bấm chạm ngắn một dòng để chọn món
        lvMonAn.setOnItemClickListener((parent, view, position, id) -> {
            MonAn mon = (MonAn) parent.getItemAtPosition(position);
            Toast.makeText(this, getString(R.string.chon_mon, mon.getTen()), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Chọn dòng " + position + ": " + mon.getTen());
        });

        // 5. Giữ lâu một dòng để xóa món (return true để không bị kích hoạt onItemClick đè lên)
        lvMonAn.setOnItemLongClickListener((parent, view, position, id) -> {
            MonAn mon = danhSach.remove(position);
            adapter.notifyDataSetChanged(); // Báo adapter vẽ lại
            capNhatThongKe(); // NC4: Cập nhật lại tổng
            Toast.makeText(this, getString(R.string.da_xoa, mon.getTen()), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Đã xóa dòng " + position + ": " + mon.getTen());
            return true;
        });

        // 6. Gán sự kiện nút thêm món
        btnThem.setOnClickListener(v -> themMon());
    }

    private void taoDuLieuMau() {
        danhSach.add(new MonAn("Phở bò tái", "Nước dùng ninh xương 8 giờ", 45000, R.drawable.mon1));
        danhSach.add(new MonAn("Bún chả Hà Nội", "Chả nướng than hoa", 50000, R.drawable.mon2));
        danhSach.add(new MonAn("Cơm tấm sườn", "Sườn nướng mật ong", 40000, R.drawable.mon3));
        danhSach.add(new MonAn("Bánh mì thịt nguội", "Pate nhà làm", 25000, R.drawable.mon4));
        danhSach.add(new MonAn("Trà đào cam sả", "Mát lạnh, ít đường", 30000, R.drawable.mon5));
    }

    private void themMon() {
        String ten = edtTen.getText().toString().trim();
        String giaStr = edtGia.getText().toString().trim();

        // Lớp 1: Kiểm tra rỗng tên
        if (ten.isEmpty()) {
            edtTen.setError(getString(R.string.err_ten));
            edtTen.requestFocus();
            return;
        }

        // Lớp 2: Kiểm tra định dạng số của giá
        int gia;
        try {
            gia = Integer.parseInt(giaStr);
        } catch (NumberFormatException e) {
            edtGia.setError(getString(R.string.err_gia));
            edtGia.requestFocus();
            return;
        }

        // Lớp 3: Kiểm tra giá phải > 0
        if (gia <= 0) {
            edtGia.setError(getString(R.string.err_gia_duong));
            edtGia.requestFocus();
            return;
        }

        // Thêm vào vị trí 0 (đầu danh sách) và gán ảnh mặc định mon1
        danhSach.add(0, new MonAn(ten, getString(R.string.moi_them), gia, R.drawable.mon1));
        adapter.notifyDataSetChanged();
        capNhatThongKe(); // NC4: Cập nhật lại tổng
        lvMonAn.smoothScrollToPosition(0); // Cuộn lên đầu để người dùng thấy ngay món mới

        edtTen.setText("");
        edtGia.setText("");
        edtTen.requestFocus();
        Log.d(TAG, "Đã thêm: " + ten + " - Tổng: " + danhSach.size() + " món");
    }

    // Phương thức tính toán thống kê (NC4)
    private void capNhatThongKe() {
        int tongSo = danhSach.size();
        long tongTien = 0;
        for (MonAn m : danhSach) {
            tongTien += m.getGia();
        }
        tvThongKe.setText(getString(R.string.thong_ke_format, tongSo, MonAn.dinhDangGia((int) tongTien)));
    }

}