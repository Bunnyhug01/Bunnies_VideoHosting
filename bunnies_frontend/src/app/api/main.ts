

const API_URL = "http://localhost:3030"
// const API_URL = "http://192.168.43.231:3030"

export interface UsernamePasswordDTO {
    username: string,
    password: string
}

export async function sfetch(url: string, init?: RequestInit): Promise<Response> {
    if(localStorage.getItem("jwt") === null || localStorage.getItem("jwt") === undefined)
        await updateJWT()
    if(init === undefined)
        init = {}
    if(init.headers === undefined)
        init.headers = {}
    init.credentials = "include"
    init.headers.Authorization = `Bearer ${localStorage.getItem("jwt")}`
    let response = await fetch(`${API_URL}${url}`, init)
    if(response.status === 401) {
        await updateJWT()
        response = await fetch(`${API_URL}${url}`, init)
    }
    if(response.ok)
        return response
    return Promise.reject(response)
}

async function updateJWT() {
    return fetch(`${API_URL}/auth/refreshtoken`, {
        method: "POST",
        credentials: 'include'
    }).then(resp => {
        if(resp.status == 401) {
            window.location.replace("/en/sign-in");
        }
        return resp.json()
    }).then(json => json["access"]).then(token => localStorage.setItem("jwt", token))
}

interface UserSingUpRequest extends UsernamePasswordDTO {
}

interface JWTResponse {
    type: string,
    access: string
}

export async function signup(request: UserSingUpRequest) {
    return fetch(`${API_URL}/auth/base/signup`, {
        method: "POST",
        body: JSON.stringify(request),
        headers: {
            "Content-Type": "application/json",
        }
    }).then(response => {
        if(response.ok)
            return response.json()
        return Promise.reject(response)
    }).then(json => json.access).then(token => localStorage.setItem("jwt", token))
}

export async function signin(request: UserSingUpRequest) {
    return fetch(`${API_URL}/auth/base/signin`, {
        method: "POST",
        body: JSON.stringify(request),
        headers: {
            "Content-Type": "application/json",
        }
    }).then(response => {
        if(response.ok)
            return response.json()
        return Promise.reject(response)
    }).then(json => json.access).then(token => localStorage.setItem("jwt", token))
}

export async function logout() {
    return fetch(`${API_URL}/auth/logout`, {
        method: "POST",
        credentials: 'include'
    })
}
