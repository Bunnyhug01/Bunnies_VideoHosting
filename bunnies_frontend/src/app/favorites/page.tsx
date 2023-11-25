'use client'

import Link from 'next/link'

import React, { useEffect, useState } from "react";

import { Box, ThemeProvider, Typography, createTheme } from "@mui/material";
import FavoriteIcon from '@mui/icons-material/Favorite';

import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import { ColorModeContext, getDesignTokens } from "../styles/designTokens";
import { search, searchInLiked } from "../api/search";
import { addView, getLine } from "../api/views";
import { UserVideos } from "../userVideos/page";
import { Video, getOne, hasLike } from "../api/videos";
import RecommendedList from "../components/RecommendedList";
import { getMe } from "../api/users";


export function Favorites() {

    const [data, setData] = useState<Video[]>([])


    const [searchText, setSearchText] = useState<string|undefined>(undefined);
    const searchHandler = (e: React.FormEvent<HTMLInputElement>) => {
      setSearchText(e.currentTarget.value);
    }
    
    useEffect(() => {
      if (searchText === undefined || searchText === '') {
        
        getMe().then((user) => 
          user.likes.map((videoId) =>
            getOne(videoId)
              .then((video) => {
                setData(data.concat([video]))
              }
            )
          )
        )
  
      } else {
        searchInLiked(searchText).then((videoArray) => {
          setData(videoArray)
        })
      }
  
    }, [searchText])

    console.log('DATA', data)
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
                <Link 
                  key={video.id}
                  href={`/video/${video.id}`}
                  onClick={() => {
                      addView(video.id)
                  }}
                >
                  <RecommendedList video={video} />
                </Link>
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
