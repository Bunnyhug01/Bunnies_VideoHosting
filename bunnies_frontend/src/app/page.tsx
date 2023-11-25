'use client'

import Link from 'next/link'

import React, { useCallback, useEffect, useState } from "react";

import { Box, ThemeProvider, createTheme } from "@mui/material";

import Header from "./components/Header";
import BottomNav from "./components/BottomNav";
import { ColorModeContext, getDesignTokens } from "./styles/designTokens";
import { search } from './api/search';
import { getLine } from './api/views';
import { Video } from './api/videos';
import VideoList from './components/VideoList';

export function Home() {
  const [data, setData] = useState<Video[]>([])

  const [searchText, setSearchText] = useState<string|undefined>(undefined);
  const searchHandler = useCallback((e: React.FormEvent<HTMLInputElement>) => {
    setSearchText(e.currentTarget.value);
  }, [])
  
  useEffect(() => {
    console.log('Ciao')
    if (searchText === undefined || searchText === '') {
      getLine().then((videoArray) => {
        setData(videoArray)
      })
    } else {
      search(searchText).then((videoArray) => {
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
      
      <Header searchHandler={searchHandler} ColorModeContext={ColorModeContext} text={{searchText: searchText, setSearchText: setSearchText}} />
      

      <Box sx={{ height: 'calc(100vh - 90px);', width: '100vw', marginTop: 5 }}>
      
        <Box
          className="grid grid-cols-3 gap-4 ml-2 mr-2 pb-4"
        >
          {data.map((video) => (
            <Link 
              key={video.id}
              href={`/video/${video.id}`}
            >
              <VideoList video={video} />
            </Link>
          ))}
        </Box>

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
        <Home />
      </ThemeProvider>
    </ColorModeContext.Provider>
    );
  }

function styled(Dialog: any) {
  throw new Error("Function not implemented.");
}