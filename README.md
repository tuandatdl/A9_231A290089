# INT4211 - LẬP TRÌNH TRÊN CÁC THIẾT BỊ DI ĐỘNG
## BÁO CÁO THỰC HÀNH LAB A9: ADAPTER & LISTVIEW (THỰC ĐƠN QUÁN ĂN)

---

### THÔNG TIN SINH VIÊN
* **Họ và tên:** Đỗ Lê Tuấn Đạt
* **Mã số sinh viên (MSSV):** 231A290089
* **Lớp học phần:** Lập trình thiết bị di động (INT4211)
* **Môi trường thực thi:** Android Studio trên macOS (Apple Silicon)
* **Thiết bị thử nghiệm:** Máy ảo Pixel 8 (Android 17, API 37.1 – aarch64)
* **Repository GitHub:** `https://github.com/tuandatdl/A9_231A290089` 

---

### CÁC MỤC ĐÃ HOÀN THÀNH

#### 1. Lớp dữ liệu MonAn & Layout một dòng chuẩn (Phần 2, 3)
* Thiết kế lớp `MonAn.java` độc lập với giao diện, quản lý tiền tệ bằng kiểu số nguyên `int` để ngăn chặn triệt để sai số dấu phẩy động của `double`. Tích hợp phương thức tĩnh `dinhDangGia()` tự động định dạng tiền tệ có dấu chấm phân cách hàng nghìn (ví dụ: `45.000 đ`).
* Thiết kế `item_mon_an.xml` với `minHeight="72dp"` theo chuẩn Material Design, sử dụng `layout_weight="1"` cho khối văn bản để đẩy giá tiền sang mép phải, kết hợp `maxLines="1"` và `ellipsize="end"` để văn bản dài không làm xô lệch bố cục.

#### 2. Kế thừa BaseAdapter & Mẫu ViewHolder tối ưu cuộn mượt (Phần 4)
* Triển khai đầy đủ 4 phương thức bắt buộc của `BaseAdapter`: `getCount()`, `getItem()`, `getItemId()` và `getView()`.
* Áp dụng nghiêm ngặt mẫu thiết kế **ViewHolder** kết hợp `setTag()` và `getTag()` trên `convertView`:
  * Chỉ "thổi" layout bằng `LayoutInflater.inflate(..., false)` khi `convertView == null`.
  * Đặt phần gán dữ liệu nằm ngoài nhánh `if` để đảm bảo nội dung chính xác khi View được tái sử dụng.
  * Bổ sung `Log.d` chứng minh thực nghiệm số lần inflate chỉ đạt khoảng 8–10 View dù cuộn danh sách liên tục.

#### 3. Tương tác ListView, Thêm/Xóa dữ liệu & setEmptyView (Phần 5)
* Bắt sự kiện chạm ngắn `onItemClick` hiển thị Toast tên món ăn.
* Bắt sự kiện giữ lâu `onItemLongClick` xóa phần tử khỏi danh sách, gọi `notifyDataSetChanged()` và `return true` để chặn sự kiện click đè lên.
* Kiểm tra hợp lệ 3 lớp khi thêm món mới (kiểm tra rỗng, bắt ngoại lệ `NumberFormatException`, kiểm tra số dương) và cuộn mượt lên vị trí đầu bằng `smoothScrollToPosition(0)`.
* Cấu hình cơ chế `setEmptyView(tvTrong)` giúp tự động hiển thị thông báo "Thực đơn trống" khi toàn bộ danh sách bị xóa hết.

#### 4. Bài nâng cao NC2: Đổi màu nền phân loại giá món ăn
* Trong `getView()`, kiểm tra điều kiện `mon.getGia() > 45000`: nếu đúng gán nền cam nhạt (`#FFF3E0`), nếu sai bắt buộc gán lại nền trắng (`#FFFFFF`) để không bị lỗi loang màu sang các dòng khác khi cuộn.

#### 5. Bài nâng cao NC4: Thống kê số lượng và tổng giá trị thực đơn
* Bổ sung thanh thống kê `tvThongKe`, cài đặt hàm `capNhatThongKe()` tính tổng số món và tổng số tiền của toàn bộ thực đơn theo thời gian thực mỗi khi thêm mới hoặc xóa món.
