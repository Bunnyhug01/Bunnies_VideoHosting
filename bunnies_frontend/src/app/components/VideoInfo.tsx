import { ThumbUp, ThumbDown } from "@mui/icons-material";
import { Accordion, AccordionDetails, AccordionSummary, Box, IconButton, Typography } from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import UserIcon from "./UserIcon";


export default function VideoInfo() {
  return (
    <Box>
      <Box className="flex items-center md:w-[65%] sm:w-[100%] h-[80px] py-4 px-8 sm:px-4">

        <Box>
          <Box className="inline-block">
            <UserIcon />
          </Box>
          
          <Box className="inline-block">
            <IconButton className="lg:ml-2">
                <ThumbUp className="text-gray-400 hover:text-textColor"/>
            </IconButton>
            <p className='inline-block text-[14px] font-bold text-gray-500 ml-2'>10</p>

            <IconButton className="ml-2">
                <ThumbDown className="text-gray-400 hover:text-textColor"/>
            </IconButton>
            <p className='inline-block text-[14px] font-bold text-gray-500 ml-2'>5</p>
          </Box>
        </Box>


      </Box>
      <Box className='md:w-[65%] sm:w-[100%]'>
        <Accordion>
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
              <p className="text-[14px] font-bold">100,000 Views</p>
            </Box>

            <Box className="mx-4">
              <p className="text-[14px] font-bold">02.09.2023</p>
            </Box>
          </AccordionSummary>
          <AccordionDetails>
            <Typography>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
              malesuada lacus ex, sit amet blandit leo lobortis eget.
            </Typography>
          </AccordionDetails>
        </Accordion>
        </Box>
    </Box>
  )
}