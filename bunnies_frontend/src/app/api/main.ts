

const API_URL = "http://localhost:3030"
// const API_URL = "http://192.168.43.231:3030"

export interface UsernamePasswordDTO {
    username: string,
    password: string
}
export type JWTUpdateCallBack = () => Promise<UsernamePasswordDTO>

let jwtUpdateCallBack: JWTUpdateCallBack

export async function sfetch(url: string, init?: RequestInit): Promise<Response> {
    //if(localStorage.getItem("jwt") === null || localStorage.getItem("jwt") === undefined)
        await updateJWT()
    if(init === undefined)
        init = {}
    if(init.headers === undefined)
        init.headers = {}
    init.headers["Authorization"] = `Bearer ${localStorage.getItem("jwt")}`
    return fetch(`${API_URL}${url}`, init)
        .then(async resp => {
            console.log(resp)
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
    const token = await fetch(`${API_URL}/auth/base/login`, {
        method: "POST",
        body: JSON.stringify(await jwtUpdateCallBack()),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(json => json["access_token"])
    console.log("new jwt")
    localStorage.setItem("jwt", token)
}

setJWTUpdateCallBack(async () => {
    return { username: "Arseny", password: "1234" }
})