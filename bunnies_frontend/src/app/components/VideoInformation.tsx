import { ThumbUp, ThumbDown } from "@mui/icons-material";
import { Box, Button, IconButton, Typography } from "@mui/material";

import UserIcon from "./UserIcon";
import { Video, hasDisLike, hasLike, removeDisLike, removeLike, setDisLike, setLike } from "../api/videos";
import MobileVideoDescription from "./MobileVideoDescription";
import VideoDescription from "./VideoDescription";
import { addSubscribe, getMe, hasSubscribe, removeSubscribe } from "../api/users";
import { useEffect, useState } from "react";


interface Props {
  video : Video
}


export default function VideoInformation({ video } : Props) {

  const [user, setUser] = useState<number | null>(null)

  const [likeView, setViewLike] = useState(video?.likes)
  const [dislikeView, setViewDislike] = useState(video?.dislikes)
  const [subscribeView, setSubscribeView] = useState()

  async function handleLike() {

    if (await hasDisLike(video.id!)) {
      await removeDisLike(video.id!)
      setViewDislike(dislikeView - 1)
    }

    if (await hasLike(video.id!))
    {
      removeLike(video.id!)
      setViewLike(likeView - 1)
    }
    else
    {
      setLike(video.id!)
      setViewLike(likeView + 1)
    }

  }

  async function handleDislike() {

    if (await hasLike(video.id!))
    {
      await removeLike(video.id!)
      setViewLike(likeView - 1)
    }

    if (await hasDisLike(video.id!))
    {
      removeDisLike(video.id!)
      setViewDislike(dislikeView - 1)
    }
    else
    {
      setDisLike(video.id!)
      setViewDislike(dislikeView + 1)
    }

  }

  async function handleSubscribe() {
    
    if (user != null) {
      if (await hasSubscribe(video.owner)) {
        addSubscribe(user)
      }
      else
      {
        removeSubscribe(user)
      }
    }

  }

  useEffect(() => {
    getMe().then((user) => setUser(user.id))
  },[])

  return (
    <Box>
      <Box className="flex items-center lg:w-[100%] md:w-[100%] sm:w-[100%] h-[80px] py-4 px-8 sm:px-4">

        <Box>
          <Box className="inline-block">
            <UserIcon userId={video.owner} />
          </Box>
          
          <Box className="inline-block">

            <Button
              sx={{color: 'text.primary', ml:1}}
              disableElevation
              onClick={handleSubscribe}
              className="font-bold"
            >
              Subscribe
            </Button>

            <IconButton
              className="lg:ml-2"
              onClick={handleLike}
            >
                <ThumbUp sx={{color: 'text.primary'}} className="lg:w-[25px] lg:h-[25px] md:w-[25px] md:h-[25px] sm:w-[20px] sm:h-[20px]" />
            </IconButton>
            <Typography sx={{color: 'text.secondary', fontSize: 14}} className='inline-block font-bold ml-2'>{likeView}</Typography>

            <IconButton 
              className="ml-2"
              onClick={handleDislike}
            >
                <ThumbDown sx={{color: 'text.primary'}} className="lg:w-[25px] lg:h-[25px] md:w-[25px] md:h-[25px] sm:w-[20px] sm:h-[20px]" />
            </IconButton>
            <Typography sx={{color: 'text.secondary', fontSize: 14}} className='inline-block font-bold ml-2'>{dislikeView}</Typography>
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