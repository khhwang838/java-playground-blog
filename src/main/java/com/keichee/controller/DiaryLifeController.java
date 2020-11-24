package com.keichee.controller;

import com.keichee.domain.Post;
import com.keichee.domain.Response;
import com.keichee.exception.BadRequestException;
import com.keichee.service.DiaryLifeService;
import com.mongodb.client.FindIterable;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/diary/life")
@AllArgsConstructor
public class DiaryLifeController {

    private final DiaryLifeService diaryLifeService;

    @ApiOperation("전체 목록 조회")
    @GetMapping
    public Response<List<Post>> getPosts() {
        log.info("전체 목록 조회");

        return new Response<>(diaryLifeService.getPosts(null));
    }

    @ApiOperation("다이어리 포스팅 신규생성")
    @PostMapping
    public Response<Void> savePost(@RequestBody Post post) {
        validatePostInput(post);
        diaryLifeService.createPost(post);
        return new Response<>();
    }

    @ApiOperation("다이어리 포스팅 업데이트")
    @PutMapping
    public Response<Void> updatePost(@RequestBody Post post) {
        validatePostInput(post);
        diaryLifeService.updatePost(post);
        return new Response<>();
    }

    @ApiOperation("다이어리 포스팅 삭제")
    @DeleteMapping
    public Response<Void> deletePost(@RequestParam String _id) {
        diaryLifeService.deletePost(_id);
        return new Response<>();
    }

    private void validatePostInput(Post post) {
        if (ObjectUtils.isEmpty(post.getTitle())) {
            throw new BadRequestException("No TITLE exists.");
        }
        if (ObjectUtils.isEmpty(post.getContent())) {
            throw new BadRequestException("No CONTENT exists.");
        }
    }

    @ExceptionHandler
    public ResponseEntity<Response<String>> badRequest(BadRequestException e) {
        log.error("Bad request.., e-msg:{}", e.getMessage(), e);
        return ResponseEntity.badRequest().body(new Response<>(e.getMessage()));
    }
}
