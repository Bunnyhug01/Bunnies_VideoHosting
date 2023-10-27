import { ThumbUp, ThumbDown } from "@mui/icons-material";
import { Box, IconButton, Typography } from "@mui/material";

import UserIcon from "./UserIcon";
import { Video } from "../api/videos";
import MobileVideoDescription from "./MobileVideoDescription";
import VideoDescription from "./VideoDescription";


interface Props {
  video : Video
}


export default function VideoInformation({ video } : Props) {
  return (
    <Box>
      <Box className="flex items-center lg:w-[100%] md:w-[100%] sm:w-[100%] h-[80px] py-4 px-8 sm:px-4">

        <Box>
          <Box className="inline-block">
            <UserIcon userId={video.owner} />
          </Box>
          
          <Box className="inline-block">
            <IconButton className="lg:ml-2">
                <ThumbUp sx={{color: 'text.primary'}} className="lg:w-[25px] lg:h-[25px] md:w-[25px] md:h-[25px] sm:w-[20px] sm:h-[20px]" />
            </IconButton>
            <Typography sx={{color: 'text.secondary', fontSize: 14}} className='inline-block font-bold ml-2'>{video?.likes}</Typography>

            <IconButton className="ml-2">
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