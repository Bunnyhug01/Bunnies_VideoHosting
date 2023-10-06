import { Search } from "@mui/icons-material";
import { Box, IconButton, InputBase } from "@mui/material";


interface Props {
    boxId? : string,
    inputId? : string,
    onChange? : React.ChangeEventHandler<HTMLInputElement>
}


export default function SearchBox({boxId = undefined, inputId = undefined, onChange = undefined} : Props) {

    return (
        <Box
            sx={{bgcolor: 'background.additional'}}
            className="ml-20 h-[40px] px-2 md:flex items-center rounded-md overflow-hidden sm:hidden"
            id={boxId}
        >
            <InputBase 
                id={inputId}
                placeholder="Search..."
                inputProps={{ 'aria-label': 'search google maps' }}
                className="w-full h-full bg-transparent outline-none
                border-none text-textColor placeholder-gray-400 px-2"
                onChange={onChange}
            />
            <IconButton type="button" sx={{ p: '10px' }} aria-label="search">
                <Search />
            </IconButton>
        </Box>
    )
}