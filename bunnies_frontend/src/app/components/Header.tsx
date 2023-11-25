import * as React from 'react';

import { styled, alpha, useTheme } from '@mui/material/styles';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Badge from '@mui/material/Badge';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import NotificationsIcon from '@mui/icons-material/Notifications';
import MoreIcon from '@mui/icons-material/MoreVert';
import SwipeableTemporaryDrawer from './SwipeableTemporaryDrawer';
import { LightMode, DarkMode } from '@mui/icons-material';
import Logo from './Logo';
import Upload from './Upload';
import SearchBox from './SearchBox';
import { Link } from '@mui/material';
import Dictaphone from './Dictaphone/Dictaphone';


interface Props {
    searchHandler: React.ChangeEventHandler<HTMLInputElement>
    ColorModeContext: React.Context<{
        toggleColorMode: () => void;
    }>
    text?: {
        searchText?: string,
        setSearchText: React.Dispatch<React.SetStateAction<string | undefined>>
    }
}


export default function Header({searchHandler, ColorModeContext, text} : Props) {
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const [mobileMoreAnchorEl, setMobileMoreAnchorEl] =
    React.useState<null | HTMLElement>(null);

    const isMenuOpen = Boolean(anchorEl);
    const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

    const theme = useTheme();
    const colorMode = React.useContext(ColorModeContext);

    const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMobileMenuClose = () => {
        setMobileMoreAnchorEl(null);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
        handleMobileMenuClose();
    };

    const handleMobileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
        setMobileMoreAnchorEl(event.currentTarget);
    };

    const menuId = 'primary-search-account-menu';
    const renderMenu = (
        <Menu
            anchorEl={anchorEl}
            anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            id={menuId}
            keepMounted
            transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            open={isMenuOpen}
            onClose={handleMenuClose}
        >
            <Link href='/sign-in' style={{ textDecoration: 'none' }}>
                <MenuItem onClick={handleMenuClose}>Sign in</MenuItem>
            </Link>
            <Link href='/sign-up' style={{ textDecoration: 'none' }}>
                <MenuItem onClick={handleMenuClose}>Sign up</MenuItem>
            </Link>
        </Menu>
    );

    const mobileMenuId = 'primary-search-account-menu-mobile';
    const renderMobileMenu = (
        <Menu
            anchorEl={mobileMoreAnchorEl}
            anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            id={mobileMenuId}
            keepMounted
            transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            open={isMobileMenuOpen}
            onClose={handleMobileMenuClose}
        >
            <MenuItem>
                <Upload />
                <p>Upload video</p>
            </MenuItem>
            {/* <MenuItem>
                <IconButton
                    size="large"
                    aria-label="show 17 new notifications"
                    color="inherit"
                >
                    <Badge badgeContent={17} color="error">
                        <NotificationsIcon />
                    </Badge>
                </IconButton>
                <p>Notifications</p>
            </MenuItem> */}
            <MenuItem onClick={colorMode.toggleColorMode}>
                <IconButton
                    color="inherit"
                    size="large"
                    style={{ backgroundColor: 'transparent' }}
                >
                    {theme.palette.mode === 'dark'
                        ? <LightMode className="text-yellow-400"/>
                        : <DarkMode className="text-darkThemeIconColor"/>
                    }
                </IconButton>
                <p>Theme</p>
            </MenuItem>
            <MenuItem onClick={handleProfileMenuOpen}>
                <IconButton
                    size="large"
                    aria-label="account of current user"
                    aria-controls="primary-search-account-menu"
                    aria-haspopup="true"
                    color="inherit"
                    style={{ backgroundColor: 'transparent' }}
                >
                    <AccountCircle />
                </IconButton>
                <p>Profile</p>
            </MenuItem>
        </Menu>
    );

    return (
        <Box sx={{ flexGrow: 1, height: 50 }}>
            <AppBar sx={{ bgcolor: 'background.default' }} elevation={0} style={{position: 'fixed'}}>
                <Toolbar sx={{ bgcolor: 'background.default' }}>
                    <SwipeableTemporaryDrawer />
                    <Logo />

                    <SearchBox onChange={searchHandler} text={text} />
                    <Dictaphone setDictaphoneInput={text?.setSearchText} />

                    

                    <Box sx={{ flexGrow: 1 }} />
                    <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
                        <Upload />
                        {/* <IconButton
                            size="large"
                            aria-label="show 17 new notifications"
                            color="inherit"
                        >
                        <Badge badgeContent={17} color="error">
                            <NotificationsIcon />
                        </Badge>
                        </IconButton> */}
                        <IconButton
                            onClick={colorMode.toggleColorMode}
                            color="inherit"
                            size="large"
                        >
                                {theme.palette.mode === 'dark'
                                ? <LightMode className="hover:text-yellow-400"/>
                                : <DarkMode className="hover:text-darkThemeIconColor"/>
                                }
                        </IconButton>
                        <IconButton
                            size="large"
                            edge="end"
                            aria-label="account of current user"
                            aria-controls={menuId}
                            aria-haspopup="true"
                            onClick={handleProfileMenuOpen}
                            color="inherit"
                        >
                            <AccountCircle />
                        </IconButton>
                    </Box>
                    <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
                        <IconButton
                            size="large"
                            aria-label="show more"
                            aria-controls={mobileMenuId}
                            aria-haspopup="true"
                            onClick={handleMobileMenuOpen}
                            color="inherit"
                        >
                            <MoreIcon />
                        </IconButton>
                    </Box>
                </Toolbar>
            </AppBar>
            {renderMobileMenu}
            {renderMenu}
        </Box>
    );
}