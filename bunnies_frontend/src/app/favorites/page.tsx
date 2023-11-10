'use client'

import React, { useEffect, useState } from "react";

import { Box, ThemeProvider, Typography, createTheme } from "@mui/material";
import FavoriteIcon from '@mui/icons-material/Favorite';

import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import { ColorModeContext, getDesignTokens } from "../styles/designTokens";
import { searchOne } from "../api/search";
import { addView, getLine } from "../api/views";
import { UserVideos } from "../userVideos/page";
import { Video, hasLike } from "../api/videos";
import RecommendedList from "../components/RecommendedList";


export function Favorites() {

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


  return(
    <Box
      sx={{
        bgcolor: 'background.default',
        color: 'text.primary',
      }}
    >
      
      <Header searchHandler={searchHandler} ColorModeContext={ColorModeContext} />
            
      <Box 
        component="main"
        sx={{ 
          bgcolor: 'background.default',
          color: 'text.primary',
          flexGrow: 1, p: 3,
        }}>

            <Box className="flex items-center">
                <Typography className='text-[18px] font-bold my-2 px-2'>
                    Favorites
                </Typography>
                <FavoriteIcon />
            </Box>

            {data.length !== 0
              ? data.map((video) => (
                <Box 
                  key={video.id}
                  onClick={() => {
                      setVideo(video)
                      addView(video.id)
                  }}
                >
                  <RecommendedList video={video} />
                </Box>
              ))
              : <Typography className="my-2 px-2">You haven't liked any of the videos</Typography>
            }
        </Box>

      <BottomNav />

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
        <Favorites />
      </ThemeProvider>
    </ColorModeContext.Provider>
    );
}
