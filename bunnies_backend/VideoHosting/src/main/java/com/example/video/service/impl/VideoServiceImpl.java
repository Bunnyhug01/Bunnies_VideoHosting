package com.example.video.service.impl;

import com.example.video.controller.advice.exception.VideoNotFoundException;
import com.example.video.dto.request.VideoCreateRequest;
import com.example.video.dto.request.VideoReplaceRequest;
import com.example.video.entity.Video;
import com.example.video.repository.UserRepository;
import com.example.video.repository.VideoRepository;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("videoService")
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository repository;
    private final UserRepository userRepository;

    @Override
    public Video createVideo(VideoCreateRequest request, long owner) {
        return repository.save(Video.builder()
                .uploadDate(new Date())
                .owner(userRepository.getReferenceById(owner))
                .title(request.getTitle())
                .detail(request.getDetail())
                .videoUrl(request.getVideoUrl())
                .logoUrl(request.getLogoUrl())
                .isPrivate(request.getIsPrivate())
                .build());
    }

    @Override
    public Video getOneVideo(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
    }

    @Override
    public List<Video> getAllVideos() {
        return repository.findAll();
    }

    @Override
    public void deleteVideo(long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public Video replaceVideo(long id, VideoReplaceRequest request) {
        System.out.println(request);
        var video = getOneVideo(id);
        var title = request.getTitle();
        if (title != null)
            video.setTitle(title);
        var detail = request.getDetail();
        if (detail != null)
            video.setDetail(detail);
        var videoUrl = request.getVideoUrl();
        if (videoUrl != null)
            video.setVideoUrl(videoUrl);
        var logoUrl = request.getLogoUrl();
        if (logoUrl != null)
            video.setLogoUrl(logoUrl);
        var isPrivate = request.getIsPrivate();
        if (isPrivate != null)
            video.setIsPrivate(isPrivate);
        return repository.save(video);
    }

}
