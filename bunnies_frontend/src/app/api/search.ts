
import { sfetch } from "./main"
import { Video } from "./videos"

export async function search(title: string): Promise<Video[]>  {
    return sfetch(`/videos/search/name/${title}`).then(resp => resp.json())
}

export async function searchInLiked(title: string): Promise<Video[]>  {
    return sfetch(`/videos/search/like/${title}`).then(resp => resp.json())
}

export async function searchInHistory(title: string): Promise<Video[]>  {
    return sfetch(`/videos/search/history/${title}`).then(resp => resp.json())
}