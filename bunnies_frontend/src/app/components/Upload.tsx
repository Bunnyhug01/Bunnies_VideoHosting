import { Box, Button, Dialog, DialogTitle, IconButton, DialogContent, DialogActions, Typography, TextField, FormControl, InputLabel, Select, MenuItem, SelectChangeEvent } from "@mui/material"
import CloseIcon from '@mui/icons-material/Close';
import VideoCallIcon from '@mui/icons-material/VideoCall';
import { DropzoneArea } from 'material-ui-dropzone'

import { ref, uploadBytes } from "firebase/storage";

import { ChangeEvent, useEffect, useState } from "react";
import { storage } from "../firebase/firebase";
import UploadZone from "./UploadZone";

export default function Upload() {
  
  // Video upload
  const [videoUpload, setVideoUpload] = useState<File|null>(null);
  const uploadVideo = () => {
    console.log(videoUpload)
    if (videoUpload == null) return;
    const videoRef = ref(storage, `videos/${videoUpload.name}`);

    uploadBytes(videoRef, videoUpload).then(() => {
      alert('Video uploaded');
    });
  };

  // Image upload
  const [imageUpload, setImageUpload] = useState<File|null>(null);
  const uploadImage = () => {
    if (imageUpload == null) return;
    const imageRef = ref(storage, `images/${imageUpload.name}`);

    uploadBytes(imageRef, imageUpload).then(() => {
      alert('Image uploaded');
    });
  };

  const [privacy, setPrivacy] = useState('');

  const handleChange = (event: SelectChangeEvent) => {
    setPrivacy(event.target.value);
  };


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

  const [isThumbnailUpload, setIsThumbnailUpload] = useState<boolean>(false)

  useEffect(() => {
    setIsThumbnailUpload(!isThumbnailUpload)
  }, [imageUpload])

  const [isVideoUpload, setIsVideoUpload] = useState<boolean>(false)

  useEffect(() => {
    setIsVideoUpload(!isVideoUpload)
  }, [videoUpload])

  return (
    <Box>
 
        <IconButton size="large" color="inherit" onClick={handleClickUploadOpen}>
            <VideoCallIcon />
        </IconButton>

        <Box>
            <Box>
                <Dialog
                    onClose={() => {
                        handleUploadClose()
                        setVideoUpload(null)
                    }}
                    aria-labelledby="customized-dialog-title"
                    open={openUpload}
                    fullWidth
                >
                    <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                    Video Upload
                    </DialogTitle>
                    <IconButton
                    aria-label="close"
                    onClick={() => {
                        handleUploadClose()
                        setVideoUpload(null)
                    }}
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
                            <UploadZone setFile={setVideoUpload} fileType={'video'}/>
                        </Box>
                    </DialogContent>
                    <DialogActions>
   
                        <Button
                            disabled={isVideoUpload} 
                            autoFocus 
                            onClick={() => {
                                handleUploadClose()
                                handleClickOpen()
                            }}
                        >
                            Next
                        </Button>
    
                    </DialogActions>
                </Dialog>
            </Box>

            <Box>
                <Dialog
                    onClose={() => {
                        handleClose()
                        setVideoUpload(null)
                        setImageUpload(null)
                    }}
                    aria-labelledby="customized-dialog-title"
                    open={open}
                    fullWidth
                >
                    <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                    Video Upload
                    </DialogTitle>
                    <IconButton
                    aria-label="close"
                    onClick={() => {
                        handleClose()
                        setVideoUpload(null)
                        setImageUpload(null)
                    }}
                    sx={{
                        position: 'absolute',
                        right: 8,
                        top: 8,
                        color: (theme) => theme.palette.grey[500],
                    }}
                    >
                    <CloseIcon />
                    </IconButton>
                    <form
                        method="post" 
                        onSubmit={() => {
                            uploadVideo()
                            uploadImage()
                            handleClose()
                            setVideoUpload(null)
                            setImageUpload(null)
                        }}
                    >
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

                            <FormControl
                                sx={{
                                    marginTop: 4,
                                }}
                                fullWidth
                                required          
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
                            </FormControl>
                        </Box>

                        <Box 
                            sx ={{
                            marginTop: 2,
                            }}
                        >
                            <Typography variant="h5">Thumbnail</Typography>
                            <Typography>Select a thumbnail for your video</Typography>

                            <Box
                                sx={{
                                    marginTop: 2,
                                }}
                                className="sm:w-full w-[40%]"
                            >
     
                                <UploadZone setFile={setImageUpload} fileType="image"/>
                            </Box>
                            
                        </Box>

                        </DialogContent>
                        <DialogActions>
                            <Button type="submit" disabled={isThumbnailUpload} autoFocus>
                                Save changes
                            </Button>
                        </DialogActions>
                    </form>
                    
                </Dialog>
            </Box>

        </Box>

    </Box>
  )
}
