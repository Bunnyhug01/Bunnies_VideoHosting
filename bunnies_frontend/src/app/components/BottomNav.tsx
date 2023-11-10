import React from 'react'

import Link from 'next/link';

import { Paper, BottomNavigation, BottomNavigationAction } from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HistoryIcon from '@mui/icons-material/History';


export default function BottomNav() {
    const [value, setValue] = React.useState(0);
  
    return (
        <Paper sx={{ position: 'fixed', bottom: 0, left: 0, right: 0, display: { lg: 'none', md: 'none' } }} elevation={3}>
            <BottomNavigation
            showLabels
            value={value}
            onChange={(event, newValue) => {
                setValue(newValue);
            }}
            >
                <Link href='/'>
                    <BottomNavigationAction label="Home" icon={<HomeIcon />} />
                </Link>

                <Link href='/favorites'>
                    <BottomNavigationAction label="Favorites" icon={<FavoriteIcon />} />
                </Link>
                
                <Link href='/history'>
                    <BottomNavigationAction label="History" icon={<HistoryIcon />} />
                </Link>
                
            </BottomNavigation>
        </Paper>
    )
}