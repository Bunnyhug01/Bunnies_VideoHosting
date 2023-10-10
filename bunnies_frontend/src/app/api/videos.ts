
import { API_URL } from "./main"

export interface VideoBody {
    imgSrc: string,
    videoSrc: string,
    videoName: string,
}

export interface Video extends VideoBody {
    id: number,
}

export interface ApiVideo {
    id?: number,
    logoUrl: string,
    title: string,
    videoUrl: string,
}

function toVideo(v: ApiVideo): Video {
    return {id: v.id!!, imgSrc: v.logoUrl, videoSrc: v.videoUrl, videoName: v.title}
}

function toApiVideo(v: Video): ApiVideo {
    return {id: v.id, logoUrl: v.imgSrc, videoUrl: v.videoSrc, title: v.videoName}
}

function toApiVideoBody(v: VideoBody): ApiVideo {
    return {logoUrl: v.imgSrc, videoUrl: v.videoSrc, title: v.videoName}
}

export async function getAll(): Promise<Video[]>  {
    return fetch(`${API_URL}/videos`).then(resp => resp.json()).then(json => json["_embedded"]["videoList"].map(toVideo))
}

export async function getOne(id: number): Promise<Video> {
    return fetch(`${API_URL}/videos/${id}`).then(resp => resp.json()).then(json => toVideo(json))
}

export async function deleteOne(id: number) {
    return fetch(`${API_URL}/videos/${id}`, {
        method: "DELETE"
    }).then(resp => resp.json())
}

export async function create(video: VideoBody): Promise<Video> {
    return fetch(`${API_URL}/videos`, {
        method: "POST",
        body: JSON.stringify(toApiVideoBody(video))
    }).then(resp => resp.json()).then(json => toVideo(json))
}

export async function replace(video: VideoBody, id: number): Promise<Video> {
    return fetch(`${API_URL}/videos/${id}`, {
        method: "PUT",
        body: JSON.stringify(toApiVideoBody(video))
    }).then(resp => resp.json()).then(json => toVideo(json))
}

