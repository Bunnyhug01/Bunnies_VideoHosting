import { ThumbUp, ThumbDown } from "@mui/icons-material";
import { IconButton } from "@mui/material";
import UserIcon from "./UserIcon";


export default function VideoInfo() {
  return (
    <div className="flex items-center w-full h-[80px] py-4 px-8 sm:px-4">

        <UserIcon />

        <div className="lg:mx-4">
          <p className="text-[14px] font-bold text-gray-500">100,000</p>
        </div>

        <div className="lg:mx-4">
          <p className="text-[14px] font-bold text-gray-500">02.09.2023</p>
        </div>

        <IconButton className="lg:mx-4">
            <ThumbUp className="text-gray-400 hover:text-textColor"/>
            <p className='text-[14px] font-bold text-gray-500 ml-4'>10</p>
        </IconButton>

        <IconButton className="lg:mx-4">
            <ThumbDown className="text-gray-400 hover:text-textColor"/>
            <p className='text-[14px] font-bold text-gray-500 ml-4'>5</p>
        </IconButton>
    </div>
  )
}