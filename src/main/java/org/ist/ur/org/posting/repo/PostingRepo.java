package org.ist.ur.org.posting.repo;

import org.ist.ur.org.posting.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostingRepo extends JpaRepository<Posting, UUID> {
}
