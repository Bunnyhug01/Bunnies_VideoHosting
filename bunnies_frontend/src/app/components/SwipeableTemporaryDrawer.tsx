import React from "react";

import Link from 'next/link';

import { Box, Divider, IconButton, List, ListItem, ListItemButton, ListItemIcon, ListItemText, SwipeableDrawer, Toolbar, useTheme } from "@mui/material";
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import MenuIcon from '@mui/icons-material/Menu';
import HomeIcon from '@mui/icons-material/Home';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HistoryIcon from '@mui/icons-material/History';
import Logo from "./Logo";


interface Props {
  language: {
    langDictionary: any,
    lang: string
  }
}


type Anchor = 'top' | 'left' | 'bottom' | 'right';


export default function SwipeableTemporaryDrawer({ language }: Props) {
  const [state, setState] = React.useState({
    top: false,
    left: false,
    bottom: false,
    right: false,
  });

  const toggleDrawer =
    (anchor: Anchor, open: boolean) =>
    (event: React.KeyboardEvent | React.MouseEvent) => {
      if (
        event &&
        event.type === 'keydown' &&
        ((event as React.KeyboardEvent).key === 'Tab' ||
          (event as React.KeyboardEvent).key === 'Shift')
      ) {
        return;
      }

      setState({ ...state, [anchor]: open });
    };

  const list = (anchor: Anchor) => (
    <Box
      sx={{ width: anchor === 'top' || anchor === 'bottom' ? 'auto' : 250 }}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <Toolbar>
        <Logo drawer={true} />
      </Toolbar>

      <Divider />
      <List>
        
        <Link href={`/${language.lang}/`}>
          <ListItem key={'Home'} disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <HomeIcon />
              </ListItemIcon>
              <ListItemText primary={language.langDictionary['home']} />
            </ListItemButton>
          </ListItem>
        </Link>

        <Link href={`/${language.lang}/favorites`}>
          <ListItem key={'Favorites'} disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <FavoriteIcon />
              </ListItemIcon>
              <ListItemText primary={language.langDictionary['favorites']} />
            </ListItemButton>
          </ListItem>
        </Link>

        <Link href={`/${language.lang}/history`}>
          <ListItem key={'History'} disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <HistoryIcon />
              </ListItemIcon>
              <ListItemText primary={language.langDictionary['history']} />
            </ListItemButton>
          </ListItem>
        </Link>

      </List>
      <Divider />

    </Box>
  );

  return (
    <div>
    {(['left'] as const).map((anchor) => (
      <React.Fragment key={anchor}>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={toggleDrawer(anchor, true)}
            edge="start"
            sx={{color:'#b1b1b1'}}
          >
            <MenuIcon />
          </IconButton>
          <SwipeableDrawer
            PaperProps={{
              sx: {
                backgroundColor: "background.default",
              },
            }}
            anchor={anchor}
            open={state[anchor]}
            onClose={toggleDrawer(anchor, false)}
            onOpen={toggleDrawer(anchor, true)}
          >
            {list(anchor)}
          </SwipeableDrawer>
        </React.Fragment>
      ))}
    </div>
  );
}