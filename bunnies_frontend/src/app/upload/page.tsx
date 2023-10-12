'use client'

import React from "react";
import { useEffect, useState } from "react";

import { Box, Button, PaletteMode, ThemeProvider, createTheme, Input, ButtonGroup, TextField, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent } from "@mui/material";
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import { styled } from '@mui/material/styles';
import { amber, grey } from "@mui/material/colors";

import Header from "../components/Header";
import BottomNav from "../components/BottomNav";

import { ref, uploadBytes } from "firebase/storage";
import { storage } from "../firebase/firebase";


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
        hoverColor: 'rgba(17, 24, 39, 1)',
        paper: '#040506',
      },
    } : {
      background: {
        default: '#ffffff',
        additional: '#f6f6f6',
        hoverColor: grey[300],
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
            additional: grey[600],
          }),
    },
  },
});


export function Upload() {

  // // VIDEO UPLOAD
  // -------------------
  const [videoUpload, setVideoUpload] = useState<FileList|null>(null);
  const uploadVideo = () => {
    if (videoUpload == null) return;
    const videoRef = ref(storage, `videos/${videoUpload[0].name}`);
    console.log(videoUpload[0].name);
    uploadBytes(videoRef, videoUpload[0]).then(() => {
      alert('Vide uploaded');
    });
  };
  // -------------------

  const [privacy, setPrivacy] = useState('');

  const handleChange = (event: SelectChangeEvent) => {
    setPrivacy(event.target.value);
  };


  return(
    <Box       
      sx={{
        bgcolor: 'background.default',
        color: 'text.primary',
      }}
    >
      <Header ColorModeContext={ColorModeContext} />

      <Box
        component="main"
        sx={{ 
          bgcolor: 'background.default',
          color: 'text.primary',
          flexGrow: 1, p: 3,
        }}
      >
        <Box className='md:w-full h-full'>
          {/* <ButtonGroup>
            <Input
              type="file"
              onChange={(event) => {
                // setVideoUpload(event?.target?.files)
              }}
            />
            <Button
              component="label"
              variant="contained"
              startIcon={<CloudUploadIcon />}
              onClick={uploadVideo}
            >
              Upload file
            </Button>
          </ButtonGroup> */}
          <Box>
            <Box>
              <TextField
                required
                id="filled-required"
                label="Title"
                variant="filled"
              />
              <FormControl variant="filled" sx={{ m: 1, minWidth: 120 }}>
                <InputLabel id="demo-simple-select-filled-label">Age</InputLabel>
                <Select
                  labelId="demo-simple-select-filled-label"
                  id="demo-simple-select-filled"
                  value={privacy}
                  onChange={handleChange}
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  <MenuItem value={10}>Private</MenuItem>
                  <MenuItem value={20}>Public</MenuItem>
                </Select>
              </FormControl>
            </Box>
            <Box sx={{display: 'block', p: 1, width: '50%'}}>
              <TextField
                sx={{width: '100%'}}
                id="filled-textarea"
                label="Description"
                multiline
                variant="filled"
              />
            </Box>
        </Box>
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
        <Upload />
      </ThemeProvider>
    </ColorModeContext.Provider>
    );
  }