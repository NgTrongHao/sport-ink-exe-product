package rubberduck.org.sportinksystemalt.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMetadata {
    private String bucket;
    private String folder;
    private String key;
    private String mime;
    private Long size;
    private String extension;
    private String url;
}
