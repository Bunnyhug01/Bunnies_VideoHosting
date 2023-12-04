
import { User, getOne } from "@/app/api/users";
import Avatar from "@mui/material/Avatar";
import { deepPurple } from "@mui/material/colors";
import React, { useContext, useEffect, useState } from "react";

export const UserContext = React.createContext<User | undefined>(undefined)

export function UserInfo({ user, children }: { user: User, children: React.ReactNode }) {
    return (
        <UserContext.Provider value={user}>
            {children}
        </UserContext.Provider>
    )
}
export function UserIdInfo({ id, children }: { id: number, children: React.ReactNode }) {
    const [user, setUser] = useState<User>()

    useEffect(() => {
        getOne(id).then((user) => {
            setUser(user)
        })
    }, [id])

    return (
        <>
        {
            user !== null && user !== undefined ?
            <UserInfo user={user}>
                {children}
            </UserInfo>
            : null
        }
        </>
    )
}

export function UserName({}) {
    const user = useContext(UserContext)!!
    return (<>{user.username}</>)
}

export function UserSubscribers({}) {
    const user = useContext(UserContext)!!
    return (<>{user.subscribers.length}</>)
}

export function UserLogo({}) {
    const user = useContext(UserContext)!!
    return (
        <Avatar
            sx={{ bgcolor: deepPurple[500]}}
            alt="Remy Sharp"
            src={user.logoUrl}
            className="
                lg:w-[40px] lg:h-[40px]
                md:w-[40px] md:h-[40px]
                sm:w-[30px] sm:h-[30px]
                rounded-full object-cover
                lg:min-w-[40px]
                md:min-w-[40px]
                sm:min-w-[30px]
            "
        >
            {user.username[0]}
        </Avatar>
    )
}

export function MyLogo({}) {
    const user = useContext(UserContext)!!
    return (
        <Avatar
            sx={{ bgcolor: deepPurple[500]}}
            alt="Remy Sharp"
            src={user.logoUrl}
            className="
                lg:w-[24px] lg:h-[24px]
                md:w-[24px] md:h-[24px]
                sm:w-[24px] sm:h-[24px]
                rounded-full object-cover
                lg:min-w-[24px]
                md:min-w-[24px]
                sm:min-w-[24px]
            "
        >
            {user.username[0]}
        </Avatar>
    )
}