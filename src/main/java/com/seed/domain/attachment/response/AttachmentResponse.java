package com.seed.domain.attachment.response;

import com.seed.domain.attachment.entity.Attachment;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AttachmentResponse {
    private Long id;
    private String originalName;
    private String savedFileName;
    private String filePath;

    public static List<AttachmentResponse> fromEntityList(List<Attachment> attachments) {
        if (attachments == null || attachments.isEmpty()) return null;
        return attachments.stream().map(AttachmentResponse::fromEntity).collect(Collectors.toList());
    }

    public static AttachmentResponse fromEntity(Attachment attachment) {
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .originalName(attachment.getOriginalName())
                .savedFileName(attachment.getSavedFileName())
                .filePath(attachment.getFilePath())
                .build();
    }
}
