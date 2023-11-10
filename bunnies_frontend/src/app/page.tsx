'use client'

import * as React from 'react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Header from './components/Header';
import Comments from './components/Comments';
import RecommendedList from './components/RecommendedList';
import VideoContainer from './components/VideoContainer';
import { useEffect, useState } from 'react';
import BottomNav from './components/BottomNav';
import { addView, getLine } from './api/views';
import { Video } from './api/videos';
import { ColorModeContext, getDesignTokens } from './styles/designTokens';
import { searchOne } from './api/search';


function Home() {

  const [data, setData] = useState<Video[]>([])

  const [video, setVideo] = useState<Video>(data[0])

  const [searchText, setSearchText] = useState<string|undefined>(undefined);
  const searchHandler = (e: React.FormEvent<HTMLInputElement>) => {
    setSearchText(e.currentTarget.value);
  }
  
  useEffect(() => {
    if (searchText === undefined || searchText === '') {
      getLine().then((videoArray) => {
        setData(videoArray)
      })
    } else {
      searchOne(searchText).then((videoArray) => {
        setData(videoArray)
      })
    }

  }, [searchText])
  

  return (
    <Box
      sx={{
        bgcolor: 'background.default',
        color: 'text.primary',
      }}>
      
      <Header searchHandler={searchHandler} ColorModeContext={ColorModeContext} />
      
      <Box 
        component="main"
        sx={{ 
          bgcolor: 'background.default',
          color: 'text.primary',
          flexGrow: 1, p: 3,
        }}>
        
        
        {/* Main Container */}
        <Box className='md:w-full h-full'>
          {/* Top Section */}
          <Box className='relative w-full h-full max-h-[700px] grid grid-cols-3 gap-2 p-2 sm:w-[107vw] sm:right-[10vw] sm3:w-[108vw] sm3:right-[9vw] lg:right-0 md:right-0 md:w-full sm2:w-full sm2:right-0'>
            
            {/* Video Container */}
            <Box className='sm:col-span-6 md:col-span-2 rounded-lg overflow-hidden items-center justify-center flex'>
              <VideoContainer video={video} />
            </Box>

            {/* Recommended list */} 
            <Box className='sm:col-span-6 md:col-span-1 overflow-y-auto
              scrollbar-thin scrollbar-thumb-gray-800 lg:max-h-[70%] md:max-h-[65%]
             '
              sx={{ 
                bgcolor: 'background.additional',
                color: 'text.primary',
              }}
              id='recommendedList'
            >
        
              <Typography className='text-[18px] font-bold my-2 px-2'>
                Recommended
              </Typography>

              {data.map((video) => (
                <Box 
                  key={video.id}
                  onClick={() => {
                    setVideo(video)
                    addView(video.id)
                  }}
                >
                  <RecommendedList video={video} />
                </Box>
              ))}

            </Box>

          </Box>

          {/* Bottom Section */}
          <BottomNav />

        </Box>
      </Box>
    </Box>
  );
}


export default function ToggleColorMode() {
  const [mode, setMode] = React.useState<'light' | 'dark'>('dark');
  const colorMode = React.useMemo(
    () => ({
      toggleColorMode: () => {
        setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
      },
    }),
    [],
  );

  const theme = React.useMemo(
    () =>
      createTheme(getDesignTokens(mode)),
    [mode],
  );

  return (
    <ColorModeContext.Provider value={colorMode}>
    <ThemeProvider theme={theme}>
      <Home />
    </ThemeProvider>
  </ColorModeContext.Provider>
  );
}