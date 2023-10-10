'use client'

import React from "react";
import { useEffect, useState } from "react";

import { Box, Button, PaletteMode, Step, StepLabel, Stepper, ThemeProvider, Typography, createTheme } from "@mui/material";
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import { styled } from '@mui/material/styles';
import { amber, grey } from "@mui/material/colors";

import Header from "../components/Header";
import BottomNav from "../components/BottomNav";

import { ref, uploadBytes } from "firebase/storage";
import { storage } from "../firebase/firebase";


const ColorModeContext = React.createContext({ toggleColorMode: () => {} });

const steps = ['Select campaign settings', 'Create an ad group', 'Create an ad'];

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

  const VisuallyHiddenInput = styled('input')({
    clip: 'rect(0 0 0 0)',
    clipPath: 'inset(50%)',
    height: 1,
    overflow: 'hidden',
    position: 'absolute',
    bottom: 0,
    left: 0,
    whiteSpace: 'nowrap',
    width: 1,
  });
  
export function Upload() {
  const [windowSize, setWindowSize] = useState<number[]>([]);

  const [activeStep, setActiveStep] = React.useState(0);
  const [skipped, setSkipped] = React.useState(new Set<number>());


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
  

    const isStepOptional = (step: number) => {
      return step === 1;
    };
  
    const isStepSkipped = (step: number) => {
      return skipped.has(step);
    };
  
    const handleNext = () => {
      let newSkipped = skipped;
      if (isStepSkipped(activeStep)) {
        newSkipped = new Set(newSkipped.values());
        newSkipped.delete(activeStep);
      }
  
      setActiveStep((prevActiveStep) => prevActiveStep + 1);
      setSkipped(newSkipped);
    };
  
    const handleBack = () => {
      setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };
  
    const handleSkip = () => {
      if (!isStepOptional(activeStep)) {
        // You probably want to guard against something like this,
        // it should never occur unless someone's actively trying to break something.
        throw new Error("You can't skip a step that isn't optional.");
      }
  
      setActiveStep((prevActiveStep) => prevActiveStep + 1);
      setSkipped((prevSkipped) => {
        const newSkipped = new Set(prevSkipped.values());
        newSkipped.add(activeStep);
        return newSkipped;
      });
    };
  
    const handleReset = () => {
      setActiveStep(0);
    };

    return (
        <Box>
            <Header ColorModeContext={ColorModeContext} />
            <Box sx={{ width: '100%', marginTop: 5}}>
                <Stepper activeStep={activeStep}>
                    {steps.map((label, index) => {
                        const stepProps: { completed?: boolean } = {};
                        const labelProps: {
                        optional?: React.ReactNode;
                        } = {};
                        if (isStepOptional(index)) {
                        labelProps.optional = (
                            <Typography variant="caption">Optional</Typography>
                        );
                        }
                        if (isStepSkipped(index)) {
                        stepProps.completed = false;
                        }
                        return (
                        <Step key={label} {...stepProps}>
                            <StepLabel {...labelProps}>{label}</StepLabel>
                        </Step>
                        );
                    })}
                </Stepper>
                {activeStep === steps.length ? (
                <React.Fragment>
                    <Typography sx={{ mt: 2, mb: 1 }}>
                    All steps completed - you&apos;re finished
                    </Typography>
                    <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
                    <Box sx={{ flex: '1 1 auto' }} />
                    <Button onClick={handleReset}>Reset</Button>
                    </Box>
                </React.Fragment>
                ) : (
                <React.Fragment>
                    <Typography sx={{ mt: 2, mb: 1 }}>Step {activeStep + 1}</Typography>
                    <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
                    <Button
                        color="inherit"
                        disabled={activeStep === 0}
                        onClick={handleBack}
                        sx={{ mr: 1 }}
                    >
                        Back
                    </Button>
                    <Box sx={{ flex: '1 1 auto' }} />
                    {isStepOptional(activeStep) && (
                        <Button color="inherit" onClick={handleSkip} sx={{ mr: 1 }}>
                        Skip
                        </Button>
                    )}
                    <Button onClick={handleNext}>
                        {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                    </Button>
                    </Box>
                </React.Fragment>
                )}
                <div>
                  <Button
                    component="label"
                    variant="contained"
                    startIcon={<CloudUploadIcon />}
                    onClick={uploadVideo}
                  >
                    Upload file
                    <VisuallyHiddenInput
                      type="file"
                      onChange={(event) => {
                        console.log(event?.target?.files);
                        setVideoUpload(event?.target?.files)
                      }}
                    />
                  </Button>
                </div>
            </Box>
            {windowSize[0] <= 640
                ? <BottomNav />
                : null
            }
        </Box>
    )
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