package video.upload.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 2000)
    private String url;

    private String publicId; // cloudinary public_id for deletes
    private String uploadedBy;
    private LocalDateTime uploadedAt;
    private Long sizeBytes;
    private String contentType;
}
