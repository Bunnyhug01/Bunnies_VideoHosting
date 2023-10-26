
import Image from "next/image";

import { Video, getOne } from "@/app/api/videos";
import { Avatar } from "@mui/material";
import { deepPurple } from "@mui/material/colors";
import React, { useContext, useEffect, useState } from "react";

export const VideoContext = React.createContext<Video | undefined>(undefined)

export function VideoInfo({ id, children }: { id: number, children: React.ReactNode }) {
    const [video, setVideo] = useState<Video>()

    useEffect(() => {
        getOne(id).then((video) => {
            setVideo(video)
        })
    }, [])

    return (
        <>
        { video !== null && video !== undefined ?
            <VideoContext.Provider value={video}>
                {children}
            </VideoContext.Provider>
            : null
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

export function VideoUploadDate({}) {
    const video = useContext(VideoContext)!!
    return (<>{video.uploadDate}</>)
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


