package video.upload.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudConfig {
    // application.properties uses cloudinary.cloud_name, cloudinary.api_key,
    // cloudinary.api_secret
    // provide empty defaults so the app can start when environment variables are
    // not set during local development
    @Value("${cloudinary.cloud_name:}")
    private String cloudName;
    @Value("${cloudinary.api_key:}")
    private String apiKey;
    @Value("${cloudinary.api_secret:}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }
}
