import { TimelineLite } from 'gsap';
import { useState, useEffect, useRef } from 'react';
import { VideoPlayer } from './VideoPlayer';


interface Props {
  data : {
    name? : string,
    imgSrc? : string,
    videoSrc? : string
  }
}


export default function VideoContainer(  { data } : Props  ) {
  const [isPlaying, setPlaying] = useState<boolean>(false);
  const tl: TimelineLite = new TimelineLite({ delay: 0.3 });
  
  useEffect(() => {
    if(isPlaying) {
      tl.fromTo('#mainVideoName', { y : 0, opacity : 1 }, { y : -20, opacity : 0 });
    } else {
      tl.fromTo('#mainVideoName', { y : -20, opacity : 0 }, { y : 0, opacity : 1 });
    }
  }, [isPlaying, data])

  return (
    <div className="relative w-full h-full">
        <video 
            src={data.videoSrc}
            controls
            poster={data.imgSrc}
            className="min-w-full min-h-full w-full h-full object-cover"
            onPlay={() => setPlaying(true)}
            onPause={() => setPlaying(false)}
        ></video>

        {/* <VideoPlayer /> */}

        <div className="absolute text-sm top-0 left-0 z-10 w-full h-[60px] py-4 px-3 bg-gradient-to-b from-black to-transparent">
            <h2 className="text-textColor" id='mainVideoName'>
              {data.name}
            </h2>
        </div>
      
    </div>
  )
}