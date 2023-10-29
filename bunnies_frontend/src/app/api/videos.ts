
import { sfetch } from "./main"


export interface VideoBody {
    logoUrl: string,
    title: string,
    detail: string,
    videoUrl: string,
}

export interface Video {
    id?: number,
    logoUrl: string,
    title: string,
    detail: string,
    videoUrl: string,
    uploadDate: Date,
    likes: number,
    dislikes: number,
    views: number,
    owner: number,
}

export async function getAll(): Promise<Video[]>  {
    return sfetch("/videos").then(resp => resp.json())
}

export async function getOne(id: number): Promise<Video> {
    return sfetch(`/videos/${id}`).then(resp => resp.json())
}

export async function deleteOne(id: number) {
    return sfetch(`/videos/${id}`, {
        method: "DELETE"
    }).then(resp => resp.json())
}

export async function create(video: VideoBody): Promise<Video> {
    return sfetch(`/videos`, {
        method: "POST",
        body: JSON.stringify(video)
    }).then(resp => resp.json())
}

export async function setLike(video: number): Promise<Response> {
    return sfetch(`/likes/${video}`, {
        method: "POST"
    })
}

export async function setDisLike(video: number): Promise<Response> {
    return sfetch(`/dislikes/${video}`, {
        method: "POST"
    })
}
export async function removeLike(video: number): Promise<Response> {
    return sfetch(`/likes/${video}`, {
        method: "DELETE"
    })
}

export async function removeDisLike(video: number): Promise<Response> {
    return sfetch(`/dislikes/${video}`, {
        method: "DELETE"
    })
}

export async function hasLike(video: number): Promise<boolean> {
    return sfetch(`/likes/${video}`).then(resp => resp.json()).then(json => json["status"]  === 'true')
}


export async function hasDisLike(video: number): Promise<boolean> {
    return sfetch(`/dislikes/${video}`).then(resp => resp.json()).then(json => json["status"]  === 'true')
}

