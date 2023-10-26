
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
    }, [])

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
            sx={{ bgcolor: deepPurple[500] }}
            alt="Remy Sharp"
            src={user.logoUrl}
            className="w-[40px] h-[40px] rounded-full object-cover min-w-[40px]"
        >
            {user.username[0]}
        </Avatar>
    )
}