package com.bank.bankapp.entity;
import jakarta. persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// @Getter
// @Setter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
      @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;
    @OneToOne(mappedBy="user",cascade=CascadeType.ALL)
    private Account account;
}
