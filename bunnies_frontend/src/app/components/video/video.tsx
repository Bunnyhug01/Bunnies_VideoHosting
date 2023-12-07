
import Image from "next/image";

import { Video, getOne } from "@/app/api/videos";
import { Avatar } from "@mui/material";
import { deepPurple } from "@mui/material/colors";
import React, { useContext, useEffect, useMemo, useState } from "react";

export const VideoContext = React.createContext<Video | undefined>(undefined)

export function VideoInfo({ video, children }: { video: Video, children: React.ReactNode }) {
    return (
        <VideoContext.Provider value={video}>
            {children}
        </VideoContext.Provider>
    )
}

export function VideoIdInfo({ id, children }: { id: number, children: React.ReactNode }) {
    const [video, setVideo] = useState<Video>()

    useEffect(() => {
        getOne(id).then((video) => {
            setVideo(video)
        })
    }, [id])

    return (
        <>
        { video !== null && video !== undefined ?
        <VideoInfo video={video}>
            {children}
        </VideoInfo> : null
        }
        </>
    )
}

export function VideoTitle({}) {
    const video = useContext(VideoContext)!!
    return (<>{video.title}</>)
}

export function VideoLikes({}) {
    const video = useContext(VideoContext)!!
    return (<>{video.likes}</>)
}

export function VideoDisLikes({}) {
    const video = useContext(VideoContext)!!
    return (<>{video.dislikes}</>)
}

export function VideoViews({}) {
    const video = useContext(VideoContext)!!
    return (<>{video.views}</>)
}

export function VideoLength({}) {
    const [time, setTime] = useState("");

    const video = useContext(VideoContext)!!

    useEffect(() => {
        const tempVideoPlayer:HTMLVideoElement = document.createElement('video')
        tempVideoPlayer.setAttribute("type", "hidden")
        tempVideoPlayer.src = video.videoUrl
        tempVideoPlayer.preload = 'metadata'
    
        tempVideoPlayer.onloadedmetadata = function () {
    
            window.URL.revokeObjectURL(tempVideoPlayer.src);
            const duration = Math.floor(tempVideoPlayer.duration)

            const time = (duration / 60) >= 60
            ? new Date(duration * 1000).toISOString().slice(11, 19)
            : new Date(duration * 1000).toISOString().slice(14, 19)

            setTime(time)
        };
    
        tempVideoPlayer.remove()

    }, [video])

    
    return (<>{time}</>)
}

export function VideoUploadDate({}) {
    const video = useContext(VideoContext)!!

    const uploadDate = new Date(video.uploadDate)

    const month = uploadDate.getUTCMonth() + 1
    const day = uploadDate.getUTCDate()
    const year = uploadDate.getUTCFullYear()

    const date = day + "." + month + "." + year

    return (<>{date}</>)
}

export function VideoLogo({}) {
    const video = useContext(VideoContext)!!
    return (
        <Image
            src={video.logoUrl}
            fill
            alt=""
            className="rounded-lg object-cover"
        />
    )
}

export function UserVideoLogo({}) {
    const video = useContext(VideoContext)!!
    return (
        <Image
            src={video.logoUrl}
            width={300}
            height={167}
            alt=""
            className="rounded-lg object-cover"
        />
    )
}

export function VideoLogoPlayer({}) {
    const handleOnMouseOver = (e: React.MouseEvent<HTMLVideoElement>) => {
        const isPlaying = e.currentTarget.currentTime > 0 && !e.currentTarget.paused && !e.currentTarget.ended 
        && e.currentTarget.readyState > e.currentTarget.HAVE_CURRENT_DATA;

        if (!isPlaying) {
            e.currentTarget.play()
        }
    }

    const handleOnMouseOut = (e: React.MouseEvent<HTMLVideoElement>) => {
        e.currentTarget.pause()
        e.currentTarget.load()
    }

    const video = useContext(VideoContext)!!
    return (
        <video
            src={video.videoUrl}
            poster={video.logoUrl}
            muted
            disablePictureInPicture
            onMouseOver={handleOnMouseOver}
            onMouseOut={handleOnMouseOut}
            className="w-full h-full rounded-lg object-cover lg:max-h-[32vh] sm:max-h-[9vh] md:max-h-[12vh]"
        />
    )
}

