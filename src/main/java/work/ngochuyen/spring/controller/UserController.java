package work.ngochuyen.spring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.ngochuyen.spring.model.User;
import work.ngochuyen.spring.repository.UserRepository;


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


    private final UserRepository userRepository;


    // Constructor injection of UserRepository
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // 1. Create a new User (POST)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // Trả về 201 Created
    }


    // 2. Get a User by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
        return ResponseEntity.ok(user); // Trả về 200 OK
    }


    // 3. Get all Users (GET)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users); // Trả về 200 OK
    }


    // 4. Update a User by ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with ID: " + id);
        }
        updatedUser.setUserId(id); // Set the ID to the updated user
        User savedUser = userRepository.save(updatedUser);
        return ResponseEntity.ok(savedUser); // Trả về 200 OK
    }


    // 5. Delete a User by ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " deleted successfully."); // Trả về 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."); // Trả về 404 Not Found
    }

}

