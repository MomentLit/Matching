package com.example.matchings.controller;

import com.example.matchings.dto.request.MatchingCreateRequest;
import com.example.matchings.dto.response.MatchingCreateResponse;
import com.example.matchings.dto.response.MatchingListResponse;
import com.example.matchings.global.dto.ApiResponse;
import com.example.matchings.global.security.UserPrincipal;
import com.example.matchings.global.util.ResponseUtil;
import com.example.matchings.service.MatchingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matchings")
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping
    public ResponseEntity<ApiResponse<MatchingCreateResponse>> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody MatchingCreateRequest request
    ) {
        MatchingCreateResponse response = matchingService.create(principal.getUserId(), request);
        ApiResponse<MatchingCreateResponse> apiResponse = ResponseUtil.success("create matching", response);

        return ResponseEntity.status(201).body(apiResponse);
    }

    @GetMapping("/inbox")
    public ResponseEntity<ApiResponse<MatchingListResponse>> getReceivedMatchings(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        MatchingListResponse response = matchingService.getReceivedMatchings(principal.getUserId());
        ApiResponse<MatchingListResponse> apiResponse = ResponseUtil.success("select received matchings", response);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MatchingListResponse>> getSentMatchings(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        MatchingListResponse response = matchingService.getSentMatchings(principal.getUserId());
        ApiResponse<MatchingListResponse> apiResponse = ResponseUtil.success("select sent matchings", response);

        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{matching-id}/approve")
    public ResponseEntity<Void> approve(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("matching-id") Long matchingId
    ) {
        matchingService.approve(principal.getUserId(), matchingId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{matching-id}/reject")
    public ResponseEntity<Void> reject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("matching-id") Long matchingId
    ) {
        matchingService.reject(principal.getUserId(), matchingId);

        return ResponseEntity.noContent().build();
    }
}
