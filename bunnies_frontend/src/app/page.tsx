'use client'

import * as React from 'react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Header from './components/Header';
import { PaletteMode } from '@mui/material';
import Comments from './components/Comments';
import Data from './components/Data';
import RecommendedList from './components/RecommendedList';
import VideoContainer from './components/VideoContainer';
import VideoInfo from './components/VideoInfo';
import { useEffect, useState } from 'react';
import { amber, grey } from '@mui/material/colors';
import BottomNav from './components/BottomNav';

const ColorModeContext = React.createContext({ toggleColorMode: () => {} });


const getDesignTokens = (mode: PaletteMode) => ({
  palette: {
    mode,
    primary: {
      ...amber,
      ...(mode === 'dark' && {
        main: amber[300],
      }),
    },
    ...(mode === 'dark' ? {
      background: {
        default: '#040506',
        additional: '#100f14',
        drawer: 'rgba(4, 5, 6, 1)',
        paper: '#040506',
      },
    } : {
      background: {
        default: '#ffffff',
        additional: '#f6f6f6',
      },
    }),
    text: {
      ...(mode === 'light'
        ? {
            primary: grey[900],
            secondary: grey[800],
          }
        : {
            primary: '#b1b1b1',
            secondary: grey[500],
          }),
    },
  },
});


function Home() {

  interface isVideoState  {
    name? : string,
    videoSrc?: string
  }

  const [windowSize, setWindowSize] = useState<number[]>([]);

  useEffect(() => {
    setWindowSize([window.innerWidth, window.innerHeight]);
    const handleWindowResize = () => {
      setWindowSize([window.innerWidth, window.innerHeight]);
    };

    window.addEventListener('resize', handleWindowResize);

    return () => {
      window.removeEventListener('resize', handleWindowResize);
    };
  }, []);

  const [isVideo, setVideo] = useState<isVideoState>({
    name : Data[0].videoName,
    videoSrc : Data[0].videoSrc
  });

  const [inputText, setInputText] = useState<string>("");
  const inputHandler = (e: React.FormEvent<HTMLInputElement>) => {
      const lowerCase = e.currentTarget.value.toLowerCase();
      setInputText(lowerCase);
  }

  const filteredData = Data.filter((el) => {
    if (inputText === '') {
      return el;
    }
    else {
      return el.videoName.toLowerCase().startsWith(inputText);
    }
  })
  
  return (
    <Box
      sx={{
        bgcolor: 'background.default',
        color: 'text.primary',
      }}>
      
      <Header ColorModeContext={ColorModeContext} />
      
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
          <Box className='relative w-full h-[70%] max-h-[480px] grid grid-cols-3 gap-2 p-2 sm:w-[107vw] sm:right-[10vw] lg:right-0 md:right-0 md:w-full sm2:w-full sm2:right-0'>
            
            {/* Video Container */}
            <Box className='sm:col-span-6 md:col-span-2 rounded-lg overflow-hidden flex items-center justify-center'>
              <VideoContainer data={isVideo} />
            </Box>

            {/* Recommended list */} 
            <Box className='sm:col-span-6 md:col-span-1 overflow-y-auto
              scrollbar-thin scrollbar-thumb-gray-800
             '
              sx={{ 
                bgcolor: 'background.additional',
                color: 'text.primary',
              }}
              id='recommendedList'
            >
        
              <p className='text-[18px] font-bold my-2 px-2'>
                Recommended
              </p>

              {Data && filteredData.map((data) => (
                <Box key={data.id} onClick={() => setVideo({
                    name : data.videoName,
                    videoSrc : data.videoSrc
                  })
                }>
                  <RecommendedList
                    imgSrc={data.imgSrc}
                    videoName={data.videoName}
                  />
                </Box>
              ))}

            </Box>

          </Box>

          {/* Bottom Section */}
          <Box className='w-full h-[30%]'>
            
            <VideoInfo />
            
            {windowSize[0] <= 640
              ? <BottomNav />
              : null
            }

            {/* <div className='lg:mt-5'>
              <Comments />
            </div> */}

            {/* <div 
              className='flex overflow-x-scroll items-center scrollbar-none py-2'
              id='scrollContainer'
            >
              {
                Data && Data.map((data) => (
                  <Collection key={data.id} imgSrc={data.imgSrc} />
                ))
              }
            </div> */}
          </Box>
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

