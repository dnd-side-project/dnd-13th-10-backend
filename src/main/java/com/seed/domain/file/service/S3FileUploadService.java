package com.seed.domain.file.service;

import com.seed.global.config.S3Properties;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileUploadService {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public String uploadImage(MultipartFile file, Long userId) {
        String s3Key = generateS3Key(userId, file.getOriginalFilename());

        try {
            PutObjectResponse putObjectResponse = s3Client.putObject(PutObjectRequest.builder()
                            .bucket(s3Properties.getBucket())
                            .key(s3Key)
                            .contentType(file.getContentType())
                            .contentLength(file.getSize())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            log.info("S3 업로드 성공: {} (ETag: {})", s3Key, putObjectResponse.eTag());

            return getFileUrl(s3Key);

        }catch (IOException e) {
            log.error("파일 읽기 실패: {}", e.getMessage());
            throw new BusinessException(ErrorCode.FILE_READ_FAILED);
        } catch (S3Exception e) {
            log.error("S3 업로드 실패: {} (코드: {})", e.awsErrorDetails().errorMessage(), e.statusCode());
            throw new BusinessException(ErrorCode.S3_UPLOAD_FAILED);
        } catch (Exception e) {
            log.error("예상치 못한 오류: {}", e.getMessage());
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private String generateS3Key(Long userId, String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String fileName = String.format("profile_%d_%d.%s",
                userId, System.currentTimeMillis(), extension);
        return s3Properties.getFolder() + fileName;
    }


    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new BusinessException(ErrorCode.INVALID_FILE_NAME);
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private String getFileUrl(String s3Key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                s3Properties.getBucket(), s3Properties.getRegion(), s3Key);
    }
}
