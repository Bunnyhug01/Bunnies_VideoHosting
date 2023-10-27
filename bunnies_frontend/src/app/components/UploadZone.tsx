import { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';

import { Box, Button, Grid, Input, Typography } from '@mui/material';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';


interface Props {
  setFile: (file : File | null) => void 
  fileType: string
}


export default function UploadZone({ setFile, fileType }: Props) {

  const accept = fileType === 'video' ? ['video/mp4'] : ['image/png' , 'image/jpeg']

  const onDrop = useCallback((acceptedFiles: Array<File>) => {
    const file = new FileReader;

    file.onload = function() {
      setPreview(file.result);
    }

    file.readAsDataURL(acceptedFiles[0])
    setFile(acceptedFiles[0])
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
        <Box sx={{width: 'auto', height: 300, marginTop: 2}}>
          {fileType === 'video'
           ? <video src={preview as string} autoPlay loop controls muted className="w-full h-full" />
           : <img src={preview as string} alt="" width={200} height={200} />
          }
        </Box>
      )}
    </Box>

  )
}