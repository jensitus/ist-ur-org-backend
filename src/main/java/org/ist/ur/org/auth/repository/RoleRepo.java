package org.ist.ur.org.auth.repository;

import org.ist.ur.org.auth.model.Role;
import org.ist.ur.org.auth.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(RoleName name);

}
