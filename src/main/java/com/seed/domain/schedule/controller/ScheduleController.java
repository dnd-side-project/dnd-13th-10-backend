package com.seed.domain.schedule.controller;

import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.domain.schedule.service.ScheduleCommandService;
import com.seed.domain.schedule.service.ScheduleQueryService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;

    @PostMapping
    public ApiResponse<?> createSchedule(@RequestBody ScheduleRequest.CreateRequestDTO requestDTO,
                                         @AuthenticationPrincipal User user) {
        scheduleCommandService.createSchedule(user.getId(), requestDTO);
        return ApiResponse.success("면접 일정이 생성되었습니다.");
    }

    @GetMapping
    public ApiResponse<List<ScheduleResponse.InfoDTO>> getAllSchedules(@AuthenticationPrincipal User user) {
        return ApiResponse.success(scheduleQueryService.findAll(user.getId()));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponse.DetailDTO> getSchedule(@PathVariable Long scheduleId) {
        return ApiResponse.success(scheduleQueryService.getSchedule(scheduleId));
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<?> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleCommandService.deleteSchedule(scheduleId);
        return ApiResponse.success("면접 일정이 삭제되었습니다.");
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<?> modifySchedule(@PathVariable Long scheduleId,
                                         @RequestBody ScheduleRequest.UpdateRequestDTO requestDTO) {
        scheduleCommandService.modifySchedule(scheduleId, requestDTO);
        return ApiResponse.success("면접 일정이 수정되었습니다.");
    }

}
