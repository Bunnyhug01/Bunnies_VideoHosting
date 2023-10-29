import { ThumbUp, ThumbDown } from "@mui/icons-material";
import { Box, IconButton, Typography } from "@mui/material";

import UserIcon from "./UserIcon";
import { Video, hasDisLike, hasLike, removeDisLike, removeLike, setDisLike, setLike } from "../api/videos";
import MobileVideoDescription from "./MobileVideoDescription";
import VideoDescription from "./VideoDescription";
import { useEffect, useState } from "react";


interface Props {
  video : Video
}


export default function VideoInformation({ video } : Props) {

  const [likeView, setViewLike] = useState(false)
  const [dislikeView, setViewDisLike] = useState(false)

  async function handleLike() {
    if (await hasLike(video.id!))
    {
      removeLike(video.id!)
      setViewLike(false)
    }
    else
    {
      setLike(video.id!)
      setViewLike(true)
    }

  }


  async function handleDislike() {
    if (await hasDisLike(video.id!))
    {
      removeDisLike(video.id!)
      setViewDisLike(false)
    }
    else
    {
      setDisLike(video.id!)
      setViewDisLike(true)
    }

  }

  useEffect(() => {

  }, [likeView, dislikeView])

  return (
    <Box>
      <Box className="flex items-center lg:w-[100%] md:w-[100%] sm:w-[100%] h-[80px] py-4 px-8 sm:px-4">

        <Box>
          <Box className="inline-block">
            <UserIcon userId={video.owner} />
          </Box>
          
          <Box className="inline-block">
            <IconButton
              className="lg:ml-2"
              onClick={handleLike}
            >
                <ThumbUp sx={{color: 'text.primary'}} className="lg:w-[25px] lg:h-[25px] md:w-[25px] md:h-[25px] sm:w-[20px] sm:h-[20px]" />
            </IconButton>
            <Typography sx={{color: 'text.secondary', fontSize: 14}} className='inline-block font-bold ml-2'>{video?.likes}</Typography>

            <IconButton 
              className="ml-2"
              onClick={handleDislike}
            >
                <ThumbDown sx={{color: 'text.primary'}} className="lg:w-[25px] lg:h-[25px] md:w-[25px] md:h-[25px] sm:w-[20px] sm:h-[20px]" />
            </IconButton>
            <Typography sx={{color: 'text.secondary', fontSize: 14}} className='inline-block font-bold ml-2'>{video?.dislikes}</Typography>
          </Box>
        </Box>
      </Box>
      
      <Box className='md:w-[100%] sm:w-[100%] lg:w-[100%]'>
        <VideoDescription video={video} />
        <MobileVideoDescription video={video} />
      </Box>

    </Box>
  )
}