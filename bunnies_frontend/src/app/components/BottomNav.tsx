import React from 'react'

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
            <BottomNavigationAction label="Home" icon={<HomeIcon />} />
            <BottomNavigationAction label="Favorites" icon={<FavoriteIcon />} />
            <BottomNavigationAction label="History" icon={<HistoryIcon />} />
            </BottomNavigation>
        </Paper>
    )
}