package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
