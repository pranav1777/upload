package video.upload.Controller;

import lombok.RequiredArgsConstructor;
import video.upload.Entity.Video;
import video.upload.Repository.VideoRepository;
import video.upload.Service.CloudinaryService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@Validated
public class VideoController {
    private final CloudinaryService cloudinaryService;
    private final VideoRepository videoRepository;

    // Upload endpoint (JWT-protect in SecurityConfig)
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
            @RequestParam("title") @NotBlank String title,
            @RequestParam("user") @NotBlank String user) throws IOException {
        if (file.isEmpty())
            return ResponseEntity.badRequest().body("Empty file");

        Map result = cloudinaryService.uploadVideo(file);
        String url = result.get("secure_url").toString();
        String publicId = result.get("public_id").toString();

        Video v = Video.builder()
                .title(title)
                .url(url)
                .publicId(publicId)
                .uploadedBy(user)
                .uploadedAt(LocalDateTime.now())
                .sizeBytes(file.getSize())
                .contentType(file.getContentType())
                .build();

        videoRepository.save(v);
        return ResponseEntity.ok(Map.of("url", url, "id", v.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return videoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
