'use client'

import * as React from 'react';
import { useTheme, ThemeProvider, createTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Header from './components/Header';
import { Accordion, AccordionSummary, AccordionDetails, IconButton, PaletteMode, AppBar, Button, Toolbar } from '@mui/material';
import Comments from './components/Comments';
import Data from './components/Data';
import RecommendedList from './components/RecommendedList';
import VideoContainer from './components/VideoContainer';
import VideoInfo from './components/VideoInfo';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useState } from 'react';
import { LightMode, DarkMode } from '@mui/icons-material';
import { amber, deepOrange, grey } from '@mui/material/colors';

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
    ...(mode === 'dark' && {
      background: {
        default: '#040506',
        paper: '#040506',
      },
    }),
    text: {
      ...(mode === 'light'
        ? {
            primary: grey[900],
            secondary: grey[800],
          }
        : {
            primary: '#fff',
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
        overflow: 'hidden',
      }}>
      
      <AppBar sx={{bgcolor: 'background.default'}} position="sticky">
        <Toolbar>
          <Header ColorModeContext={ColorModeContext}/>
        </Toolbar>
      </AppBar>
      
      <Box 
        component="main"
        sx={{ 
          bgcolor: 'background.default',
          color: 'text.primary',
          flexGrow: 1, p: 3,
        }}>
        
        {/* Main Container */}
        <Box className='sm:w-[calc(100%-60px)] md:w-full h-full'>
          {/* Top Section */}
          <div className='w-full h-[70%] max-h-[480px] grid grid-cols-3 gap-2 p-2'>
            
            {/* Video Container */}
            <div className='sm:col-span-6 md:col-span-2 rounded-lg overflow-hidden flex items-center justify-center'>
              <VideoContainer data={isVideo} />
            </div>

            {/* Recommended list  !!! bg-searhcBg */} 
            <Box className='sm:col-span-6 md:col-span-1 overflow-y-auto
              scrollbar-thin scrollbar-thumb-gray-800
             '
              sx={{ 
                bgcolor: 'background.default',
                color: 'text.primary',
              }}
              id='recommendedList'
            >
        
              <p className='text-textColor text-[18px] font-bold my-2 px-2'>
                Recommended
              </p>

              {Data && filteredData.map((data) => (
                <div key={data.id} onClick={() => setVideo({
                    name : data.videoName,
                    videoSrc : data.videoSrc
                  })
                }>
                  <RecommendedList
                    imgSrc={data.imgSrc}
                    videoName={data.videoName}
                  />
                </div>
              ))}

            </Box>

          </div>

          {/* Bottom Section */}
          <div className='w-full h-[30%]'>
            
            <VideoInfo />
            <Box className='w-[65%]'>
              <Accordion>
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
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
                    malesuada lacus ex, sit amet blandit leo lobortis eget.
                </AccordionSummary>
                <AccordionDetails>
                  <Typography>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
                    malesuada lacus ex, sit amet blandit leo lobortis eget.
                  </Typography>
                </AccordionDetails>
              </Accordion>
            </Box>
            
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
          </div>
        </Box>
      </Box>
    </Box>
  );
}


export default function ToggleColorMode() {
  const [mode, setMode] = React.useState<'light' | 'dark'>('light');
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

