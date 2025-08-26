package com.seed.domain.memoir.controller;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.HotMemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;
import com.seed.domain.memoir.service.MemoirQueryService;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.domain.memoir.service.MemoirViewHistService;
import com.seed.domain.memoir.validator.MemoirProcRequestValidator;
import com.seed.domain.user.entity.User;
import com.seed.global.paging.CursorPage;
import com.seed.global.response.ApiResponse;
import com.seed.global.response.ErrorCode;
import com.seed.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.seed.global.utils.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memoirs")
@RequiredArgsConstructor
@Tag(name = "회고 API")
public class MemoirController {

    private final MemoirService memoirService;
    private final MemoirViewHistService memoirViewHistService;
    private final MemoirQueryService memoirQueryService;
    private final MemoirProcRequestValidator memoirValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memoirValidator);
    }

    /**
     * 회고 등록 (퀵 회고, 일반 회고, 임시 저장)
     *
     * @param memoirProcRequest
     * @return
     */
    @Operation(
            summary = "면접 회고를 등록하는 API",
            description = "면접 회고를 등록하는 API 입니다.<br>"
                    + "- 넘어오는 type 필드 데이터를 동해 퀵/일반 회고를 구분합니다.<br>"
                    + "- 만약 isTmp 필드 값이 true 로 전달될 경우 임시저장으로 구분하여 저장합니다. <br>"
                    + "- 회고 타입에 따라 필수값 검증 로직이 실행 됩니다. 임시저장은 검증하지 않습니다. <br>"
                    + "- '일정 불러오기' 를 통해 회고를 작성하면 scheduleId 값을 전달해주고, 불러오지 않고 작성 시 scheduleId 값을 전달하지 않습니다."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "요청 바디 예시(퀵/일반). 실제 바인딩은 MemoirProcRequest 단일 DTO를 사용합니다.",
            content = @Content(
                    schema = @Schema(implementation = MemoirProcRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "QUICK 예시",
                                    summary = "퀵 회고 전송 예시",
                                    value = """
                    {
                      "scheduleId" : 1,
                      "type": "10",
                      "companyName": "토스",
                      "position": "20",
                      "interviewDate" : "2025-08-23",
                      "interviewTime" : "17:55",
                      "interviewFormat" : "30",
                      "interviewMood" : "10",
                      "satisfactionNote" : "30",
                      "interviewStatus" : "10",
                      "freeNote": "면접 끝나고 바로 적은 간단 메모",
                      "questions": [
                        { "questionType": "10", "title": "JPA N+1 해결법?", "order": 1 },
                        { "questionType": "20", "title": "TCP 3-way handshake", "order": 2 }
                      ],
                      "isTmp": false,
                      "isPublic": true
                    }
                    """
                            ),
                            @ExampleObject(
                                    name = "GENERAL 예시",
                                    summary = "일반 회고 전송 예시",
                                    value = """
                    {
                      "scheduleId" : 1,
                      "type": "10",
                      "companyName": "토스",
                      "position": "20",
                      "interviewDate" : "2025-08-23",
                      "interviewTime" : "11:22",
                      "interviewFormat" : "30",
                      "interviewMood" : "10",
                      "satisfactionNote" : "30",
                      "interviewStatus" : "10",
                      "interviewLevel" : "10",
                      "interviewMethod" : "20",
                      "interviewStep" : "30",
                      "freeNote": "전체 회고 작성",
                      "questions": [
                        { "questionType": "10", "title": "JPA N+1 해결법?", "answer": "페치 조인/EntityGraph", "order": 1 },
                        { "questionType": "20", "title": "TCP 3-way handshake", "answer": "SYN/SYN-ACK/ACK", "order": 2 }
                      ],
                      "url": "https://example.com/detail",
                      "isTmp": false,
                      "isPublic": true
                    }
                    """
                            )
                    }
            )
    )
    @PostMapping
    public ApiResponse<?> createMemoir(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid MemoirProcRequest memoirProcRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error(ErrorCode.INVALID_PARAMETER, ValidationUtil.toErrorList(bindingResult));
        }

        Long quickMemoir = memoirService.createMemoir(user.getId(), memoirProcRequest);
        return ApiResponse.success(SuccessCode.RETROSPECT_CREATED, quickMemoir);
    }

    /**
     * 회고 리스트 조회 (퀵 회고, 일반 회고 모두 포함)
     *
     * @return
     */
    @Operation(
            summary = "면접 회고 리스트 조회 API",
            description = "면접 회고 리스트를 조회하는 API 입니다.<br>"
                    + "- 퀵/일반 회고 모두 조회합니다. (임시저장은 제외)"
    )
    @GetMapping
    public ApiResponse<CursorPage<List<MemoirListResponse>>> findListMemoir(
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        CursorPage<List<MemoirListResponse>> listMemoir = memoirService.findListMemoir(position, cursor, size);
        return ApiResponse.success(listMemoir);
    }

    /**
     * 상세 회고 조회 (퀵 회고, 일반 회고, 임시 저장)
     *
     * @param memoirId
     * @return
     */
    @Operation(
            summary = "면접 회고 상세 조회 API",
            description = "면접 회고 상세 조회 API 입니다. <br>"
                    + "- 해당 API 호출시 해당 회고의 조회수가 증가합니다",
            parameters = {
                    @Parameter(name = "memoirId", description = "회고 아이디", required = true)
            }
    )
    @GetMapping("/{memoirId}")
    public ApiResponse<MemoirResponse> findMemoirById(
            @PathVariable Long memoirId,
            @AuthenticationPrincipal(expression = "id") Long viewerId
    ) {
        // 조회 이벤트 기록 + viewCount 증가
        memoirViewHistService.recordView(memoirId, viewerId);

        MemoirResponse memoirResponse = memoirService.findMemoirById(viewerId, memoirId);
        return ApiResponse.success(memoirResponse);
    }

    /**
     * 내가 작성한 회고 리스트 조회 (퀵 회고, 일반 회고)
     *
     * @param user
     * @param request
     * @return
     */
    @Operation(
            summary = "내가 작성한 회고 리스트 조회 API",
            description = "내가 작성한 회고 리스트 조회 API 입니다. <br>"
                    + "- 해당 API 호출시 내가 작성한 퀵/일반 회고 리스트를 조회하지만, 임시저장된 건에 대해서는 가져오지 않습니다."
    )
    @GetMapping("/mine")
    public ApiResponse<List<MineMemoirListResponse>> findMineMemoir(
            @AuthenticationPrincipal User user,
            SearchMemoirRequest request
    ) {
        List<MineMemoirListResponse> listMineMemoir = memoirService.findListMineMemoir(user.getId(), request);
        return ApiResponse.success(listMineMemoir);
    }

    /**
     * 내가 작성중인 (임시저장) 회고 조회
     *
     * @param user
     * @return
     */
    @Operation(
            summary = "내가 임시저장한 회고 리스트 조회 API",
            description = "내가 임시저장한 회고 리스트 조회 API 입니다."
    )
    @GetMapping("/mine/tmp")
    public ApiResponse<List<MineMemoirListResponse>> findMineTmpMemoir(@AuthenticationPrincipal User user) {
        List<MineMemoirListResponse> list = memoirService.findListMineTmpMemoir(user.getId());
        return ApiResponse.success(list);
    }


    /**
     * 이번주 핫 회고 TOP 10 조회
     *
     * @return
     */
    @GetMapping("/hot")
    public ApiResponse<List<HotMemoirListResponse>> findWeeklyTop10() {
        List<HotMemoirListResponse> listHotMemoir = memoirService.findWeeklyTop10();
        return ApiResponse.success(listHotMemoir);
    }

    @Operation(
            summary = "내가 좋아요를 한 회고 글 조회 API",
            description = "내가 좋아요를 한 회고 글을 조회합니다. 커서 기반 페이지네이션이 적용되어 있습니다. 다음커서와 조회할 개수를 넘겨주시면 됩니다."
    )
    @GetMapping("/liked")
    public ApiResponse<CursorPage<List<CommonMemoirResponse>>> getLikedMemoirs(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.success(memoirQueryService.getLikedMemoirs(user.getId(), cursor, size));
    }

    @Operation(
            summary = "내가 북마크를 한 회고 글 조회 API",
            description = "내가 북마크를 한 회고 글을 조회합니다. 커서 기반 페이지네이션이 적용되어 있습니다. 다음커서와 조회할 개수를 넘겨주시면 됩니다."
    )
    @GetMapping("/bookMarked")
    public ApiResponse<CursorPage<List<CommonMemoirResponse>>> getBookMarkedMemoirs(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.success(memoirQueryService.getBookMarkedMemoirs(user.getId(), cursor, size));
    }

    @Operation(
            summary = "내가 댓글을 작성한 회고 글 조회 API",
            description = "내가 댓글을 작성한 회고 글을 조회합니다. 커서 기반 페이지네이션이 적용되어 있습니다. 다음커서와 조회할 개수를 넘겨주시면 됩니다."
    )
    @GetMapping("/commented")
    public ApiResponse<CursorPage<List<CommonMemoirResponse>>> getCommentedMemoirs(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.success(memoirQueryService.getCommentedMemoirs(user.getId(), cursor, size));
    }

    /**
     * 회고 수정 (퀵 회고, 일반 회고)
     *
     * @param memoirProcRequest
     * @return
     */
    @Operation(
            summary = "회고 수정 API",
            description = "퀵/일반 회고 수정 API 입니다."
    )
    @PutMapping
    public ApiResponse<Long> modifyMemoir(@RequestBody MemoirProcRequest memoirProcRequest) {

        Long id = memoirService.modifyMemoir(memoirProcRequest);
        return ApiResponse.success(SuccessCode.RETROSPECT_UPDATED, id);
    }

    /**
     * 회고 삭제 (unUse)
     *
     * @param memoirId
     * @return
     */
    @Operation(
            summary = "회고 삭제 API",
            description = "퀵/일반 회고 삭제 API 입니다. <br>"
                    + "- 실제 delete 하는 것이 아닌 비활성화(isUse=false) 하는 API 입니다.",
            parameters = {
                    @Parameter(name = "memoirId", description = "회고 아이디", required = true)
            }
    )
    @DeleteMapping("/{memoirId}")
    public ApiResponse<Long> deleteMemoir(@PathVariable Long memoirId) {
        memoirService.deleteMemoir(memoirId);
        return ApiResponse.success(SuccessCode.RETROSPECT_DELETED, null);
    }
}
