package org.ist.ur.org.auth.repository;

import org.ist.ur.org.auth.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

}
