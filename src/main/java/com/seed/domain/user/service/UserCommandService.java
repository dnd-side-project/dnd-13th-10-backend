package com.seed.domain.user.service;

import com.seed.domain.file.service.S3FileUploadService;
import com.seed.domain.user.dto.request.UserInfoUpdateRequest;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserCommandService {

    private final UserRepository userRepository;
    private final S3FileUploadService s3FileUploadService;

    public void updateUserInfo(Long userId, MultipartFile image, UserInfoUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(image != null) {
            String imageUrl = s3FileUploadService.uploadImage(image, userId);
            user.updateImageUrl(imageUrl);
        }

        if(request.getUsername() != null) {
            user.updateName(request.getUsername());
        }
    }

    public void deleteAccount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.deleteAccount();
    }
}
