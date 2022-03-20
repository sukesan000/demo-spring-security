package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Table(name = "user")
@Entity
@Data
//コンストラクタ自動生成
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "email", "password" })

public class User {
    @Id
    //主キー生成
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 60, nullable = false)
    private String name;
    @Column(name = "email", length = 120, nullable = false, unique = true)
    private String email;
    @Column(name = "password", length = 120, nullable = false)
    private String password;
    @Column(name = "roles", length = 120)
    private String roles;
    @Column(name = "enable_flag", nullable = false)
    private Boolean enable_flag;
}
