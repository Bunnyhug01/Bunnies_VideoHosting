import { TimelineLite } from 'gsap';
import { useState, useEffect, useRef } from 'react';
import { VideoPlayer } from './VideoPlayer';
import { Video } from '../api/videos';
import { VideoInfo, VideoTitle } from './video/video';
import VideoInformation from './VideoInformation';
import { Box } from '@mui/material';


interface Props {
  video: Video
}

const DEFAULT_VIDEO: Video = {
  logoUrl: "https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/gifs%2Fpls-stand-by.gif?alt=media&token=5acd497a-8ad8-462c-a208-21958dc6edca&_gl=1*12bnvus*_ga*NDQwMTkyNzg1LjE2OTgxNjA5ODA.*_ga_CW55HF8NVT*MTY5ODE2MDk4MC4xLjEuMTY5ODE2MTU4OS4xMS4wLjA.",
  title: '',
  detail: '',
  videoUrl: '',
  uploadDate: new Date(),
  likes: 0,
  dislikes: 0,
  views: 0,
  owner: 0
}

export default function VideoContainer( { video = DEFAULT_VIDEO } : Props ) {
  const [isPlaying, setPlaying] = useState<boolean>(false);
  const tl: TimelineLite = new TimelineLite({ delay: 0.3 });
  
  useEffect(() => {
    if(isPlaying) {
      tl.fromTo('#mainVideoName', { y : 0, opacity : 1 }, { y : -20, opacity : 0 });
    } else {
      tl.fromTo('#mainVideoName', { y : -20, opacity : 0 }, { y : 0, opacity : 1 });
    }
  }, [isPlaying, video])

  return (
    <VideoInfo video={video}>
      <div className="relative w-full h-full">
          <video 
              src={video.videoUrl}
              controls
              poster={ video.logoUrl }
              className="min-w-full w-full lg:h-[70%] md2:h-[65%] sm:h-[60%] object-cover"
              onPlay={() => setPlaying(true)}
              onPause={() => setPlaying(false)}
          ></video>

          <Box className="absolute w-full lg:w-[100%]">
            {video.videoUrl !== ""
              ? <VideoInformation video={video} />
              : null
            }
          </Box>

          <div className="absolute text-sm top-0 left-0 z-10 w-full h-[60px] py-4 px-3 bg-gradient-to-b from-black to-transparent">
              <h2 className="text-textColor" id='mainVideoName'>
                <VideoTitle/>
              </h2>
          </div>
        
      </div>
    </VideoInfo>
  )
}