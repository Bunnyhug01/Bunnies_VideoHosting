
import Image from "next/image";

import { Video, getOne } from "@/app/api/videos";
import { Avatar } from "@mui/material";
import { deepPurple } from "@mui/material/colors";
import React, { useContext, useEffect, useState } from "react";

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
    }, [])

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
    const video = useContext(VideoContext)!!
    return (<>40:30</>)
}

export function VideoUploadDate({}) {
    const video = useContext(VideoContext)!!
    const start = new Date(video.uploadDate)
    const end = new Date()
    if(end.getFullYear() > start.getFullYear())
        return (<>{end.getFullYear() - start.getFullYear()} years ago</>)
    if(end.getDay() > start.getDay())
        return (<>{end.getDay() - start.getDay()} days ago</>)
    if(end.getHours() > start.getHours())
        return (<>{end.getHours() - start.getHours()} hours ago</>)
    return (<>{end.getMinutes() - start.getMinutes()} minutes ago</>)
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

