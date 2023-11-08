
import { sfetch } from "./main"
import { Video } from "./videos"

export async function searchOne(title: string): Promise<Video[]>  {
    return sfetch(`/videos/search/name/${title}`).then(resp => resp.json())
}