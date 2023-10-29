
import { sfetch } from "./main"
import { Video } from "./videos"

export async function getLine(videoId?: number): Promise<Video[]>  {
    return sfetch("/line").then(resp => resp.json())
}

export async function addView(videoId: number)  {
    return sfetch(`/view/${videoId}`, {
        method: 'POST'
    })
}
