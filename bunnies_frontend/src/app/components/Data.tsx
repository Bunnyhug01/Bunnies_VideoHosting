import { getVideos, getImages } from "../api/getData";


const Data : { id: number; imgSrc: string; videoSrc: string; videoName: string; }[] = [
    {
        id: 1,
        imgSrc:
         "https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/images%2FTrevor%20Something.png?alt=media&token=058454ef-f542-4293-bd4d-87263c25e17e",
        videoSrc:
         "https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/videos%2FTrevor_Something.mp4?alt=media&token=8af7ad29-4187-4601-ad8f-a65a35c18d20",
        videoName:
         "Trevor Something - Trevor Something Does Not Exist [Full Album] (Explicit)"
    },

    {
        id: 2,
        imgSrc:
         "https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/images%2FKen%20Ashcorp.png?alt=media&token=ed3806f9-5569-4bf2-a4c1-87bde24d531e",
        videoSrc:
         "https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/videos%2FKen%20Ashcorp%20The%20Bunny%20Song%20VeggieTales%20cover.mp4?alt=media&token=3d86aa01-1583-4157-b2c5-ac7e8cb827d8",
        videoName:
         "Ken Ashcorp - The Bunny Song (VeggieTales cover)"
    },
]



export default Data;