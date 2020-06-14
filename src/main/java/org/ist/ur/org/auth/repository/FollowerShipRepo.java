package org.ist.ur.org.auth.repository;

import org.ist.ur.org.auth.model.FollowerShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowerShipRepo extends JpaRepository<FollowerShip, UUID> {

  List<FollowerShip> findByFollowerId(Long followerId);

  List<FollowerShip> findByFollowedId(Long followedId);

  FollowerShip findByFollowerIdAndFollowedId(Long followerId, Long followedId);

}
