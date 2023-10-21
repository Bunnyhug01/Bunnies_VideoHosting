'use client'

import React from "react";

import { Box, PaletteMode, ThemeProvider, createTheme, TextField, SelectChangeEvent, Typography, FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { amber, grey } from "@mui/material/colors";

import { DataGrid } from '@mui/x-data-grid';

import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import { rows, columns } from "../api/dataGrid";


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



export function UserVideos() {

  return(
    <Box
      sx={{
        bgcolor: 'background.default',
        color: 'text.primary',
      }}
    >
      
      <Header ColorModeContext={ColorModeContext} />
      

      <Box sx={{ height: '100%', width: '100%', marginTop: 5 }}>
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

function styled(Dialog: any) {
  throw new Error("Function not implemented.");
}
