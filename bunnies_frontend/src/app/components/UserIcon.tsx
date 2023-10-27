import { Box, Typography } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import { deepPurple } from '@mui/material/colors';
import { Video } from '../api/videos';
import { getOne } from '../api/users';
import { useState, useEffect } from 'react';
import { UserIdInfo, UserInfo, UserLogo, UserName } from './user/user';


interface Props {
    userId: number
}


export default function UserIcon({ userId } : Props) {
    return (
        <Box className="flex items-center">
            <UserIdInfo id={userId}>
                <UserLogo/>
                
                <Box sx={{ flexDirection: 'column' }}>
                    <Typography sx={{color: 'text.primary', fontSize: 16}} className="font-bold ml-2">
                        <UserName/>
                    </Typography>
                    <Typography sx={{color: 'text.secondary', fontSize: 12}} className="font-bold block ml-2">User</Typography>
                </Box>
            </UserIdInfo>
        </Box>
    )
}