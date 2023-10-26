import { Box, Typography } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import { deepPurple } from '@mui/material/colors';
import { Video } from '../api/videos';
import { getOne } from '../api/users';
import { useState, useEffect } from 'react';


interface Props {
    video : Video
}


export default function UserIcon({ video } : Props) {

    const [user, setUser] = useState<string>("")

    useEffect(() => {
        getOne(video.owner).then((user) => {
            setUser(user.username)
        })
    }, [])

    return (
        <Box className="flex items-center">

            <Avatar
                sx={{ bgcolor: deepPurple[500] }}
                alt="Remy Sharp"
                src=""
                className="w-[40px] h-[40px] rounded-full object-cover min-w-[40px]"
            >
                {user[0]}
            </Avatar>

            <Box sx={{ flexDirection: 'column' }}>
                <Typography sx={{color: 'text.primary', fontSize: 16}} className="font-bold ml-2">
                    {user}
                </Typography>
                {/* <Typography sx={{color: 'text.secondary', fontSize: 12}} className="font-bold block ml-2">User</Typography> */}
            </Box>
        </Box>
    )
}