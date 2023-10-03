import Image from "next/image";
import { DarkMode, LightMode, Notifications, Search, Settings } from "@mui/icons-material";
import { IconButton, useTheme } from "@mui/material";
import SearchBox from "./SearchBox";
import { useState } from "react";
import UserIcon from "./UserIcon";
import SwipeableTemporaryDrawer from "./SwipeableTemporaryDrawer";
import React from "react";


interface Props {
    inputHandler? : React.ChangeEventHandler<HTMLInputElement>
    ColorModeContext: React.Context<{
        toggleColorMode: () => void;
    }>
}


export default function Header({inputHandler = undefined, ColorModeContext} : Props) {

    const [lightThemeIcon, setlightThemeIcon] = useState<boolean>(true);

    const theme = useTheme();
    const colorMode = React.useContext(ColorModeContext);
    
    function clickedThemeMode() {
        let elementMain: HTMLElement | null = document.getElementById('main');
        let elementRecommendedList: HTMLElement | null = document.getElementById('recommendedList');
        
        setlightThemeIcon(!lightThemeIcon);
        elementMain?.classList.toggle("light_mode_main");
        elementRecommendedList?.classList.toggle("light_mode_second");
    }

    return (
        <div className="flex items-center w-full h-[80px] py-4 px-8 sm:px-4">

            <SwipeableTemporaryDrawer />

            {/* <Image
                src=""
                alt=""
                className="md:w-[40px] md:h-[40px] sm:w-[30px] sm:h-[30px] lg:ml-[30px]"
                id="logo"
            /> */}

            <SearchBox boxId="searchBox" inputId="searchInput" onChange={inputHandler} />

            <div className="flex items-center justify-evenly h-[40px] lg:w-[25%] ml-auto" id="profileContainer">
                <IconButton>
                    <Settings className="text-gray-400 hover:text-textColor"/>
                </IconButton>

                <IconButton>
                    <Notifications className="text-gray-400 hover:text-textColor"/>
                </IconButton>

                <IconButton sx={{ ml: 1 }} onClick={colorMode.toggleColorMode} color="inherit">
                    {theme.palette.mode === 'dark'
                        ? <LightMode className="text-gray-400 hover:text-yellow-400"/>
                        : <DarkMode className="text-gray-400 hover:text-darkThemeIconColor"/>
                    }
                </IconButton>
                
                <UserIcon />
                
            </div>
        </div>
    )
}