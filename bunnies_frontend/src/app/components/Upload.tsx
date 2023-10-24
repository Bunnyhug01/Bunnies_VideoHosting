import { Box, Button, Dialog, DialogTitle, IconButton, DialogContent, DialogActions, Typography, TextField, FormControl, InputLabel, Select, MenuItem, SelectChangeEvent } from "@mui/material"
import CloseIcon from '@mui/icons-material/Close';
import VideoCallIcon from '@mui/icons-material/VideoCall';
import UploadZone from "./UploadZone"

import { ref, uploadBytes } from "firebase/storage";

import { useState } from "react";
import { storage } from "../firebase/firebase";

export default function Upload() {
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

//   const [privacy, setPrivacy] = useState('');

//   const handleChange = (event: SelectChangeEvent) => {
//     setPrivacy(event.target.value);
//   };


  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const [openUpload, setOpenUpload] = useState(false);

  const handleClickUploadOpen = () => {
    setOpenUpload(true);
  };
  const handleUploadClose = () => {
    setOpenUpload(false);
  };


  return (
    <Box>
 
        <IconButton size="large" color="inherit" onClick={handleClickOpen}>
            <VideoCallIcon />
        </IconButton>

        <Box>
            {/* <Box>
                <Dialog
                    onClose={handleUploadClose}
                    aria-labelledby="customized-dialog-title"
                    open={openUpload}
                    fullWidth
                >
                    <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                    Video Upload
                    </DialogTitle>
                    <IconButton
                    aria-label="close"
                    onClick={handleUploadClose}
                    sx={{
                        position: 'absolute',
                        right: 8,
                        top: 8,
                        color: (theme) => theme.palette.grey[500],
                    }}
                    >
                    <CloseIcon />
                    </IconButton>
                    <DialogContent dividers>
                        <Box>
                            <UploadZone />
                        </Box>
                    </DialogContent>
                    <DialogActions>
                    <Button autoFocus onClick={() => {
                        handleUploadClose()
                        handleClickOpen()
                        }}
                    >
                        Save changes
                    </Button>
                    </DialogActions>
                </Dialog>
            </Box> */}

            <Box>
                <Dialog
                    onClose={handleClose}
                    aria-labelledby="customized-dialog-title"
                    open={open}
                    fullWidth
                >
                    <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                    Video Upload
                    </DialogTitle>
                    <IconButton
                    aria-label="close"
                    onClick={handleClose}
                    sx={{
                        position: 'absolute',
                        right: 8,
                        top: 8,
                        color: (theme) => theme.palette.grey[500],
                    }}
                    >
                    <CloseIcon />
                    </IconButton>
                    <DialogContent dividers>

                    <Typography variant="h5">Details</Typography>

                    <Box
                        sx={{
                        marginTop: 2,
                        }}
                        className="w-full"
                    >
                        <TextField
                        required
                        id="title"
                        name="title"
                        label="Title"
                        fullWidth
                        autoComplete="off"
                        variant="outlined" 
                        />

                        <TextField
                        sx={{
                            marginTop: 4,
                        }}
                        placeholder="Tell viewers about your video"
                        id="description"
                        label="Description"
                        multiline
                        fullWidth
                        rows={4}
                        />

                        {/* <FormControl
                        sx={{
                            marginTop: 4,
                        }}
                        fullWidth            
                        >
                        <InputLabel>Privacy</InputLabel>
                        <Select
                            value={privacy}
                            label="Privacy"
                            onChange={handleChange}
                        >
                            <MenuItem value={'private'}>Private</MenuItem>
                            <MenuItem value={'public'}>Public</MenuItem>
                        </Select>
                        </FormControl> */}


                    </Box>

                    <Box 
                        sx ={{
                        marginTop: 2,
                        }}
                    >
                        <Typography variant="h5">Video</Typography>
                        <Typography>Select a video for uploading</Typography>

                        <Box
                        sx={{
                            marginTop: 2,
                        }}
                        className="sm:w-full w-[40%]"
                        >
                            <UploadZone />
                        </Box>
                        
                    </Box>

                    <Box 
                        sx ={{
                        marginTop: 2,
                        }}
                    >
                        <Typography variant="h5">Thumbnails</Typography>
                        <Typography>Select a thumbnail for your video</Typography>

                        <Box
                        sx={{
                            marginTop: 2,
                        }}
                        className="sm:w-full w-[40%]"
                        >
                            <UploadZone />
                        </Box>
                        
                    </Box>

                    </DialogContent>
                    <DialogActions>
                    <Button autoFocus onClick={handleClose}>
                        Save changes
                    </Button>
                    </DialogActions>
                </Dialog>
            </Box>

        </Box>

    </Box>
  )
}
