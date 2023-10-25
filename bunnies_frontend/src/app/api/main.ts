

const API_URL = "http://localhost:3030"
// const API_URL = "http://192.168.43.231:3030"

export interface UsernamePasswordDTO {
    username: string,
    password: string
}

export type JWTUpdateCallBack = () => Promise<UsernamePasswordDTO>

let jwtUpdateCallBack: JWTUpdateCallBack

export async function sfetch(url: string, init?: RequestInit): Promise<Response> {
    if(localStorage.getItem("jwt") === null || localStorage.getItem("jwt") === undefined)
        await updateJWT()
    if(init === undefined)
        init = {}
    if(init.headers === undefined)
        init.headers = {}
    init.headers["Authorization"] = `Bearer ${localStorage.getItem("jwt")}`
    return fetch(`${API_URL}${url}`, init)
        .then(async resp => {
            if(resp.status != 401)
                return resp
            await updateJWT()
            return await fetch(`${API_URL}${url}`, init)
        })
}

export function setJWTUpdateCallBack(func: JWTUpdateCallBack) {
    jwtUpdateCallBack = func
}

async function updateJWT() {
    try {
        const token = await fetch(`${API_URL}/auth/refreshtoken`, {
            method: "POST",
            credentials: 'same-origin'
        }).then(resp => resp.json()).then(json => json["access"])
        localStorage.setItem("jwt", token)
        return
    }catch(e) {
    }

    const token = await fetch(`${API_URL}/auth/base/signin`, {
        method: "POST",
        body: JSON.stringify(await jwtUpdateCallBack()),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(json => json["access"])

    localStorage.setItem("jwt", token)
}

setJWTUpdateCallBack(async () => {
    console.log("authentication")
    return { username: "Arseny", password: "1234" }
})