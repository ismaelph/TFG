export interface LoginRequest {
    username:       string,
    password:       string
}

export interface LoginResponse {
    token:          string,
    user:{
        username:   string,  
        email:      string,
        id:         number,
        empresa:    number | null
    },
    roles:          string[] | null,
}

export interface RegisterRequest {
    username:       string,
    email:          string,
    password:       string,
    roles:          string[] | null,
}

export interface RegisterResponse {
    accessToken:    string,
    user:{
        email:      string,
        id:         number,
        empresa:    number | null
    },
    roles:          string[] | null,
}