'use client'

import React, { useCallback, useEffect, useState } from "react";

import { Box, PaletteMode, ThemeProvider, createTheme, TextField, SelectChangeEvent, Typography, FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { amber, grey } from "@mui/material/colors";

import { DataGrid } from '@mui/x-data-grid';

import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import { rows, columns } from "../api/dataGrid";
import { ColorModeContext, getDesignTokens } from "../styles/designTokens";
import { getOne } from "../api/comment";
import { searchInLiked } from "../api/search";
import { getMe } from "../api/users";
import { Video } from "../api/videos";


export function UserVideos() {
  // const [data, setData] = useState<Video[]>([])

  // const [searchText, setSearchText] = useState<string|undefined>(undefined);
  // const searchHandler = useCallback((e: React.FormEvent<HTMLInputElement>) => {
  //   setSearchText(e.currentTarget.value);
  // }, [])

  // useEffect(() => {
  //   if (searchText === undefined || searchText === '') {
        
  //     getMe().then((user) => 
  //       user..map((videoId) =>
  //         getOne(videoId)
  //           .then((video) => {
  //             setData((prev)=>[...prev, video])
  //           }
  //         )
  //       )
  //     )
  
  //   } else {
  //     searchInLiked(searchText).then((videoArray) => {
  //       setData(videoArray)
  //     })
  //   }
  
  // }, [searchText])

  return(
    <Box
      sx={{
        bgcolor: 'background.default',
        color: 'text.primary',
      }}
    >
      
      <Header ColorModeContext={ColorModeContext} />
      

      <Box
        sx={{ height: '90vh', width: '100%', marginTop: 5 }}
      >
        <DataGrid
          rows={rows}
          columns={columns}
          initialState={{
            pagination: {
              paginationModel: {
                pageSize: 5,
              },
            },
          }}
          pageSizeOptions={[5]}
          checkboxSelection
          disableRowSelectionOnClick
        />
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
        <UserVideos />
      </ThemeProvider>
    </ColorModeContext.Provider>
    );
  }
