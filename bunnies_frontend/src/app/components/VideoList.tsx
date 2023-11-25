import { Box, Typography } from '@mui/material';
import { Video } from '../api/videos';
import { useState } from 'react';
import { User, getOne } from '../api/users';
import { UserIdInfo, UserInfo, UserName } from './user/user';
import { VideoInfo, VideoLength, VideoLogo, VideoLogoPlayer, VideoTitle, VideoViews } from './video/video';
import UserIcon from './UserIcon';

interface Props {
    video:Video
}


export default function VideoList({ video } : Props) {
    return (
        <Box className="py-1">
            <VideoInfo video={video}>
            <Box>
                <Box className="">
                    <VideoLogoPlayer/>
                </Box>
                    <Box>
                        <Typography sx={{color: 'text.primary'}} variant='inherit' className='lg:text-[16px] sm:text-[12px]'>
                            <VideoTitle/> 
                        </Typography>
                        <UserIdInfo id={video.owner}>
                            {/* <UserIcon userId={video.owner} /> */}
                            <Typography sx={{color: 'text.primary', fontSize: 12}} className='block'>
                                <UserName/>
                            </Typography>
                        </UserIdInfo>
                    </Box>
                    <Box sx={{color: 'text.secondary', fontSize: 14}} className='flex items-center mt-2'>
                        <Typography variant='inherit' className='font-bold'><VideoLength/></Typography>
                        <Typography variant='inherit' className='font-bold ml-6'><VideoViews/> views</Typography>
                    </Box>
            </Box>
            </VideoInfo>
        </Box>
    )
}
