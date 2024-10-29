package work.ngochuyen.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
//Tự động tạo các getter, setter, toString(), equals(), và hashCode().
@NoArgsConstructor
//Tạo constructor không có tham số (mặc định).
@AllArgsConstructor
//Tạo constructor có đầy đủ các tham số tương ứng với tất cả các thuộc tính của class.

@Entity
@Table(name = "spring_user") // Tên bảng trong cơ sở dữ liệu

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long userId;
    private String username;
    private String password;
    private String token;
    private Long tokenExpired;
}
