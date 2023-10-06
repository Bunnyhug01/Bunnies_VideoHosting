import Avatar from '@mui/material/Avatar';
import { deepPurple } from '@mui/material/colors';


export default function UserIcon() {
    return (
        <div className="flex items-center">

            <Avatar
                sx={{ bgcolor: deepPurple[500] }}
                alt="Remy Sharp"
                src=""
                className="w-[40px] h-[40px] rounded-full object-cover min-w-[40px]"
            >
                B
            </Avatar>

            <p className="text-[16px] font-bold ml-2">
                Bunnyhug <span className="block text-[12px] text-gray-500">User</span>
            </p>
        </div>
    )
}