import { ThumbUp, ThumbDown } from "@mui/icons-material";
import { Accordion, AccordionDetails, AccordionSummary, Box, IconButton, Typography } from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import UserIcon from "./UserIcon";
import { Video } from "../api/videos";
import { VideoInfo, VideoUploadDate } from "./video/video";


interface Props {
  video : Video
}


export default function VideoInformation({ video } : Props) {
  return (
    <VideoInfo video={video}>
      <Box>
        <Box className="flex items-center md:w-[65%] sm:w-[100%] h-[80px] py-4 px-8 sm:px-4">

          <Box>
            <Box className="inline-block">
              <UserIcon userId={video.owner} />
            </Box>
            
            <Box className="inline-block">
              <IconButton className="lg:ml-2">
                  <ThumbUp sx={{color: 'text.primary'}} />
              </IconButton>
              <Typography sx={{color: 'text.secondary', fontSize: 14}} className='inline-block font-bold ml-2'>{video?.likes}</Typography>

              <IconButton className="ml-2">
                  <ThumbDown sx={{color: 'text.primary'}} />
              </IconButton>
              <Typography sx={{color: 'text.secondary', fontSize: 14}} className='inline-block font-bold ml-2'>{video?.dislikes}</Typography>
            </Box>
          </Box>


        </Box>
        <Box className='md:w-[65%] sm:w-[100%]'>
          <Accordion sx={{bgcolor: 'background.additional'}} elevation={0}>
            <AccordionSummary
              sx={{pointerEvents: ""}}
              expandIcon={
                <ExpandMoreIcon
                  sx={{pointerEvents: "auto"}}
                />
              }
              aria-controls="panel2a-content"
              id="panel2a-header"
            >
              <Box className="mx-4">
                <p className="text-[14px] font-bold">{video?.views} Views</p>
              </Box>

              <Box className="mx-4">
                <p className="text-[14px] font-bold"><VideoUploadDate/></p>
              </Box>
            </AccordionSummary>
            <AccordionDetails>
              <Typography>
                {video.detail}
              </Typography>
            </AccordionDetails>
          </Accordion>
          </Box>
      </Box>
    </VideoInfo>
  )
}