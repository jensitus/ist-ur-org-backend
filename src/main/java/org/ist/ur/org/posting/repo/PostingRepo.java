package org.ist.ur.org.posting.repo;

import org.ist.ur.org.posting.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepo extends JpaRepository<Posting, Long> {
}
