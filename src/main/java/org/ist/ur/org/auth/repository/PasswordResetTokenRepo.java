package org.ist.ur.org.auth.repository;

import org.ist.ur.org.auth.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {
  PasswordResetToken findByTokenAndUserId(String token, Long user_id);
}
