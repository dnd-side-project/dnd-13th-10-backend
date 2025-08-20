package com.seed.domain.schedule.controller;

import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.domain.schedule.service.ScheduleCommandService;
import com.seed.domain.schedule.service.ScheduleQueryService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@Tag(name = "일정 API")
public class ScheduleController {

    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;

    @Operation(
            summary = "면접 일정을 생성하는 API",
            description = "면접 일정을 생성하는 API입니다. "
    )
    @PostMapping
    public ApiResponse<?> createSchedule(@RequestBody ScheduleRequest.ScheduleCreateRequestDTO requestDTO,
                                         @AuthenticationPrincipal User user) {
        scheduleCommandService.createSchedule(user.getId(), requestDTO);
        return ApiResponse.success("면접 일정이 생성되었습니다.");
    }

    @Operation(
            summary = "모든 면접 일정 조회 API",
            description = " 사용자가 등록한 모든 면접 일정을 조회합니다."
    )
    @GetMapping("")
    public ApiResponse<List<ScheduleResponse.ScheduleInfoDTO>> getAllSchedules(@AuthenticationPrincipal User user) {
        return ApiResponse.success(scheduleQueryService.findAll(user.getId()));
    }

    @Operation(
            summary = "면접 일정 단건 조회 API",
            description = "일정 ID를 통해 면접일정을 상세 조회합니다."
    )
    @GetMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponse.DetailDTO> getSchedule(@PathVariable Long scheduleId) {
        return ApiResponse.success(scheduleQueryService.getSchedule(scheduleId));
    }

    @Operation(
            summary = "면접 일정 삭제 API",
            description = "일정 ID를 통해 면접 일정을 삭제합니다."
    )
    @DeleteMapping("/{scheduleId}")
    public ApiResponse<?> deleteSchedule(@PathVariable Long scheduleId,
                                         @AuthenticationPrincipal User user) {
        scheduleCommandService.deleteSchedule(scheduleId, user.getId());
        return ApiResponse.success("면접 일정이 삭제되었습니다.");
    }

    @Operation(
            summary = "면접 일정 수정 API",
            description = "일정 ID를 통해 면접 일정을 수정합니다. 수정하지 않을 필드는 비워주시면 됩니다."
    )
    @PutMapping("/{scheduleId}")
    public ApiResponse<?> modifySchedule(@PathVariable Long scheduleId,
                                         @RequestBody ScheduleRequest.ScheduleUpdateRequestDTO requestDTO,
                                         @AuthenticationPrincipal User user) {
        scheduleCommandService.modifySchedule(scheduleId, user.getId(), requestDTO);
        return ApiResponse.success("면접 일정이 수정되었습니다.");
    }

}
