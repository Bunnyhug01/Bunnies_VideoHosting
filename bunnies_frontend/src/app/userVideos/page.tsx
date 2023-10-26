'use client'

import React from "react";

import { Box, PaletteMode, ThemeProvider, createTheme, TextField, SelectChangeEvent, Typography, FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { amber, grey } from "@mui/material/colors";

import { DataGrid } from '@mui/x-data-grid';

import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import { rows, columns } from "../api/dataGrid";
import { ColorModeContext, getDesignTokens } from "../styles/designTokens";


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
