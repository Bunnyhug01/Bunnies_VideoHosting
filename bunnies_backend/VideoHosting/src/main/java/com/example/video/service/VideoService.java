package com.example.video.service;

import com.example.video.dto.request.VideoCreateRequest;
import com.example.video.dto.request.VideoReplaceRequest;
import com.example.video.entity.Video;

import java.util.List;

public interface VideoService {

    Video createVideo(VideoCreateRequest request, long author);

    Video getOneVideo(long id);

    List<Video> getAllVideos();

    void deleteVideo(long id);

    Video replaceVideo(long id, VideoReplaceRequest request);

}
