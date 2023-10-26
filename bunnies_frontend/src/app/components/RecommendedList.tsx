import Image from 'next/image';

import { Box, Typography } from '@mui/material';
import { Video } from '../api/videos';
import { useState } from 'react';
import { User, getOne } from '../api/users';
import { UserInfo, UserName } from './user/user';
import { VideoInfo, VideoLength, VideoLogo, VideoTitle, VideoViews } from './video/video';

interface Props {
    video:Video
}


export default function RecommendedList({ video } : Props) {
  return (
    <VideoInfo video={video}>
        <Box 
            className="flex items-center mb-2 cursor-pointer px-3 py-2 duration-200 ease-in-out overflow-hidden"
            sx={{'&:hover': {
                backgroundColor: 'background.hoverColor',
            }}}
        >
            <Box className='sm:w-[60px] sm:h-[60px] lg:w-[140px] lg:h-[80px] relative'>
                <VideoLogo/>
            </Box>

            <Box className='ml-2 flex-1'>
                <Box>
                    <Typography sx={{color: 'text.primary'}} variant='inherit' className='lg:text-[16px] sm:text-[12px]'>
                        <VideoTitle/> 
                    </Typography>
                    <UserInfo id={video.owner}>
                        <Typography sx={{color: 'text.primary', fontSize: 12}} className='block'>
                            <UserName/>
                        </Typography>
                    </UserInfo>
                </Box>
                <Box sx={{color: 'text.secondary', fontSize: 14}} className='flex items-center mt-2'>
                    <Typography variant='inherit' className='font-bold'><VideoLength/></Typography>
                    <Typography variant='inherit' className='font-bold ml-6'><VideoViews/></Typography>
                </Box>
            </Box>
        </Box>
    </VideoInfo>
  )
}
