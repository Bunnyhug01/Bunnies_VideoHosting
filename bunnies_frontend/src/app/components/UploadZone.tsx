import { Dispatch, SetStateAction, useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';

import { Box, Button, Grid, IconButton, Input, Typography } from '@mui/material';

import CloseIcon from '@mui/icons-material/Close';

import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import uploadFile from '../firebase/uploadFile';
import deleteFile from '../firebase/deleteFile';


interface Props {
  setFile: (file : File | null) => void,
  fileType: string,
  reference?: {
    fileRef: string,
    setFileRef: Dispatch<SetStateAction<string>>
  },
  setProgress?: Dispatch<SetStateAction<number>>
}


export default function UploadZone({ setFile, fileType, reference, setProgress }: Props) {

  const accept = fileType === 'video' ? ['video/mp4'] : ['image/png' , 'image/jpeg']

  const [uploadingCancellation, setUploadingCancellation] = useState<boolean>(false)

  const onDrop = useCallback((acceptedFiles: Array<File>) => {
    const file = new FileReader;

    file.onload = function() {
      setPreview(file.result);
    }

    file.readAsDataURL(acceptedFiles[0])
    setFile(acceptedFiles[0])

    if (reference !== undefined) {

      if (reference.fileRef !== "") {
        deleteFile(reference.fileRef)
      }

      const fileObj = {
        file: acceptedFiles[0],
        setFileRef: reference.setFileRef,
        directory: fileType + 's',
        setProgress: setProgress,
        cancel: {uploadingCancellation: uploadingCancellation, setUploadingCancellation: setUploadingCancellation}
      }
      
      uploadFile(fileObj)
    }

  }, [])

  const { acceptedFiles, getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: accept,
  });

  const [preview, setPreview] = useState<string | ArrayBuffer | null>(null);

  return (
    <Box>
      <Grid 
        container
        direction="column"
        alignItems="center"
        justifyContent="center"
        sx={{ height: 200, display: 'flex' }}
        className='border-4 border-dashed'
      >
        <CloudUploadIcon fontSize='large' />
        <Box {...getRootProps()}>
          <input {...getInputProps()} />
          {
            isDragActive ?
            <Typography>Drop the files here ...</Typography> :
            <Typography>Drag 'n' drop some files here, or click to select files</Typography>
          }
        </Box>
      </Grid>

      {preview && (
        <Box sx={{width: 'auto', height: 350, marginTop: 2}}>
          <div className='relative'>
            {fileType === 'video'
              ?  <video src={preview as string} autoPlay loop controls muted className="w-full h-full" /> 
              :  <img src={preview as string} alt="" className="w-full h-full" />
            }
            <IconButton
              className='absolute top-0 right-0 bg-red-700 text-white p-2 rounded hover:bg-red-800 m-2'
              onClick={() => {
                setUploadingCancellation(true)
                setPreview(null)
              }}
            >
              <CloseIcon />
            </IconButton>
          </div>
        </Box>
      )}
    </Box>

  )
}