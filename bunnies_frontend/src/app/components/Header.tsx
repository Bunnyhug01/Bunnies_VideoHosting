import Image from "next/image";
import { DarkMode, LightMode, Notifications, Search, Settings } from "@mui/icons-material";
import { IconButton } from "@mui/material";
import SearchBox from "./SearchBox";
import { useState } from "react";
import UserIcon from "./UserIcon";


interface Props {
    inputHandler? : React.ChangeEventHandler<HTMLInputElement>
}


export default function Header({inputHandler = undefined} : Props) {

    const [lightThemeIcon, setlightThemeIcon] = useState<boolean>(true);
    
    function clickedThemeMode() {
        let elementMain: HTMLElement | null = document.getElementById('main');
        let elementRecommendedList: HTMLElement | null = document.getElementById('recommendedList');
        
        setlightThemeIcon(!lightThemeIcon);
        elementMain?.classList.toggle("light_mode_main");
        elementRecommendedList?.classList.toggle("light_mode_second");
    }

    return (
        <div className="flex items-center w-full h-[80px] py-4 px-8 sm:px-4">

            {/* <Image
                src=""
                alt=""
                className="md:w-[40px] md:h-[40px] sm:w-[30px] sm:h-[30px] lg:ml-[30px]"
                id="logo"
            /> */}

            <SearchBox boxId="searchBox" inputId="searchInput" onChange={inputHandler} />

            <div className="flex items-center justify-evenly h-[40px] lg:w-[25%] ml-auto" id="profileContainer">
                <IconButton className="lg:mx-4">
                    <Settings className="text-gray-400 hover:text-textColor"/>
                </IconButton>

                <IconButton className="lg:mx-4">
                    <Notifications className="text-gray-400 hover:text-textColor"/>
                </IconButton>

                <IconButton onClick={clickedThemeMode} className="lg:mx-4">
                    {lightThemeIcon
                     ? <LightMode className="text-gray-400 hover:text-yellow-400"/>
                     : <DarkMode className="text-gray-400 hover:text-darkThemeIconColor"/>
                    }
                </IconButton>
                
                <UserIcon />
                
            </div>
        </div>
    )
}