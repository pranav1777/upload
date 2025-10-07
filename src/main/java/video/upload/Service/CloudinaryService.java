package video.upload.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map uploadVideo(MultipartFile file) throws IOException {
        // Use resource_type=video for videos
        return cloudinary.uploader().uploadLarge(file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video",
                        "chunk_size", 6000000 // 6MB chunk — adjust if needed
                ));
    }

    public Map delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "video"));
    }
}
