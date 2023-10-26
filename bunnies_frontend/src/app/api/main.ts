

const API_URL = "http://localhost:3030"
// const API_URL = "http://192.168.43.231:3030"

export interface UsernamePasswordDTO {
    username: string,
    password: string
}

export type JWTUpdateCallBack = () => Promise<UsernamePasswordDTO>

let jwtUpdateCallBack: JWTUpdateCallBack

export async function sfetch(url: string, init?: RequestInit): Promise<Response> {
    if(init === undefined)
        init = {}
    if(init.headers === undefined)
        init.headers = {}
    init.credentials = "include"
    init.headers["Authorization"] = `Bearer ${localStorage.getItem("jwt")}`
    try {
        return await fetch(`${API_URL}${url}`, init)
    }catch(e) {
        console.log(e)
    }
    await updateJWT()
    return fetch(`${API_URL}${url}`, init)
}

export function setJWTUpdateCallBack(func: JWTUpdateCallBack) {
    jwtUpdateCallBack = func
}

async function updateJWT() {
    return fetch(`${API_URL}/auth/refreshtoken`, {
        method: "POST",
        credentials: 'include'
    }).then(async resp => {
        if(resp.status == 401) {
            return fetch(`${API_URL}/auth/base/signin`, {
                method: "POST",
                body: JSON.stringify(await jwtUpdateCallBack()),
                headers: {
                    "Content-Type": "application/json"
                },
                credentials: 'include'
            }).then(resp => resp.json())   
        }
        return resp.json()
    }).then(json => json["access"])     .then(token => localStorage.setItem("jwt", token))
}

setJWTUpdateCallBack(async () => {
    console.log("authentication")
    return { username: "Arseny", password: "1234" }
})