import Image from 'next/image';

import { Box, Typography } from '@mui/material';


interface Props {
    imgSrc: string,
    videoName: string
}


export default function RecommendedList({imgSrc, videoName} : Props) {
  return (
    <Box 
        className="flex items-center mb-2 cursor-pointer px-3 py-2 duration-200 ease-in-out overflow-hidden"
        sx={{'&:hover': {
            backgroundColor: 'background.hoverColor',
        }}}
    >
        <Box className='sm:w-[60px] sm:h-[60px] lg:w-[140px] lg:h-[80px] relative'>
            <Image
                src={imgSrc}
                fill
                alt=""
                className='rounded-lg object-cover'
            />
        </Box>

        <Box className='ml-2 flex-1'>
            <Box>
                <Typography sx={{color: 'text.primary'}} variant='inherit' className='lg:text-[16px] sm:text-[12px]'>
                    {videoName} 
                </Typography>
                <Typography sx={{color: 'text.primary', fontSize: 12}} className='block'>Ninja</Typography>
            </Box>
            <Box sx={{color: 'text.additional', fontSize: 14}} className='flex items-center mt-2'>
                <Typography variant='inherit' className='font-bold'>40:30</Typography>
                <Typography variant='inherit' className='font-bold ml-6'>36,000</Typography>
            </Box>
        </Box>
    </Box>
  )
}
