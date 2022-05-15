package com.nisum.user.api.model.entity;

import com.nisum.user.api.model.dto.UserStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class User {

  @Id
  private String id;

  private String name;

  private String email;

  private String password;

  @CreationTimestamp
  private LocalDateTime lastLogin;

  private String token;

  private UserStatus isActive;

  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp
  private LocalDateTime updatedDate;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Phone> phoneList;

}
