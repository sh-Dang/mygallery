package com.sh.mygallery.domain.image.repository;

import com.sh.mygallery.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
