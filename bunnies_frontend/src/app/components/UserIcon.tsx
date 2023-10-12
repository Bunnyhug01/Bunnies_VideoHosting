import { Box, Typography } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import { deepPurple } from '@mui/material/colors';


export default function UserIcon() {
    return (
        <Box className="flex items-center">

            <Avatar
                sx={{ bgcolor: deepPurple[500] }}
                alt="Remy Sharp"
                src=""
                className="w-[40px] h-[40px] rounded-full object-cover min-w-[40px]"
            >
                B
            </Avatar>

            <Box sx={{ flexDirection: 'column' }}>
                <Typography sx={{color: 'text.primary', fontSize: 16}} className="font-bold ml-2">
                    Bunnyhug
                </Typography>
                <Typography sx={{color: 'text.additional', fontSize: 12}} className="font-bold block ml-2">User</Typography>
            </Box>
        </Box>
    )
}