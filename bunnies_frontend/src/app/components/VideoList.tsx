import { Box, Typography } from '@mui/material';
import { Video } from '../api/videos';
import { UserIdInfo, UserInfo, UserName } from './user/user';
import { VideoInfo, VideoLength, VideoLogo, VideoLogoPlayer, VideoTitle, VideoViews } from './video/video';
import UserIcon from './UserIcon';

interface Props {
    video:Video,
    langDictionary: any
}


export default function VideoList({ video, langDictionary } : Props) {
    return (
        <Box className="py-1">
            <VideoInfo video={video}>
            <Box>
                <Box>
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
                    <Box sx={{color: 'text.secondary'}} className='flex items-center mt-2 lg:text-[14px] md:text-[14px] sm:text-[8px]'>
                        <Typography variant='inherit' className='font-bold'><VideoLength/></Typography>
                        <Typography variant='inherit' className='font-bold ml-6'><VideoViews/> {langDictionary['views']}</Typography>
                    </Box>
            </Box>
            </VideoInfo>
        </Box>
    )
}
