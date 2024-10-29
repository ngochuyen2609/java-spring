package work.ngochuyen.spring.controller;


import org.springframework.web.bind.annotation.*;
import work.ngochuyen.spring.model.User;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@RestController
// Đánh dấu lớp này như một controller trong Spring MVC.
//Kết hợp: Tự động thêm @ResponseBody vào tất cả các phương thức,
// có nghĩa là kết quả trả về sẽ được chuyển đổi thành JSON (hoặc XML) và trả về cho client.
//Chỉ định rằng một tham số của phương thức sẽ nhận dữ liệu từ phần thân của yêu cầu HTTP.
// Chỉ định rằng một tham số của phương thức sẽ lấy giá trị từ đường dẫn URL.
//Ví dụ: Trong getUserById(@PathVariable Long id), tham số id sẽ nhận giá trị từ phần {id} trong URL.
@RequestMapping("/api/users")
//Định nghĩa đường dẫn cơ sở cho các yêu cầu HTTP vào controller.
public class UserController {


    // In-memory storage for demo purposes
    private final Map<Long, User> userStorage = new ConcurrentHashMap<>();


    // 1. Create a new User (POST)
    @PostMapping
    // Chuyên biệt hóa @RequestMapping cho các yêu cầu HTTP POST.
    //Ứng dụng: Thường được sử dụng để tạo mới một tài nguyên.
    public User createUser(@RequestBody User user) {
        userStorage.put(user.getUserId(), user);
        return user;
    }


    // 2. Get a User by ID (GET)
    //Chuyên biệt hóa @RequestMapping cho các yêu cầu HTTP GET.
    //Ứng dụng: Dùng để lấy dữ liệu từ server.
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userStorage.getOrDefault(id, null);
    }


    // 3. Get all Users (GET)
    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(userStorage.values());
    }


    // 4. Update a User by ID (PUT)
    //Chức năng: Chuyên biệt hóa @RequestMapping cho các yêu cầu HTTP PUT.
    //Ứng dụng: Dùng để cập nhật tài nguyên đã tồn tại.
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (userStorage.containsKey(id)) {
            userStorage.put(id, updatedUser);
            return updatedUser;
        }
        throw new NoSuchElementException("User not found with ID: " + id);
    }


    // 5. Delete a User by ID (DELETE)
    //Chuyên biệt hóa @RequestMapping cho các yêu cầu HTTP DELETE.
    //Ứng dụng: Dùng để xóa tài nguyên
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (userStorage.remove(id) != null) {
            return "User with ID " + id + " deleted successfully.";
        }
        return "User not found.";
    }
}

