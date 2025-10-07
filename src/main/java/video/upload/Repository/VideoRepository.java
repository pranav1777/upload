package video.upload.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import video.upload.Entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
