import { Accordion, AccordionSummary, Box, Typography, AccordionDetails } from '@mui/material'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Video } from '../api/videos';


interface Props {
  video:Video
}


export default function VideoDescription({ video } : Props) {
  return (
    <Accordion
      sx={{bgcolor: 'background.additional'}}
      elevation={0}
      className="lg:block md:block sm:hidden"
    >
      <AccordionSummary
        sx={{pointerEvents: ""}}
        expandIcon={
          <ExpandMoreIcon
            sx={{pointerEvents: "auto"}}
          />
        }
        aria-controls="panel2a-content"
        id="panel2a-header"
      >
        <Box className="mx-4">
          <Typography className="text-[14px] font-bold">{video?.views} Views</Typography>
        </Box>

        <Box className="mx-4">
          <Typography className="text-[14px] font-bold">02.09.2023</Typography>
        </Box>
      </AccordionSummary>
      <AccordionDetails>
        <Typography>
          {video.detail}
        </Typography>
      </AccordionDetails>
    </Accordion>
  )
}
