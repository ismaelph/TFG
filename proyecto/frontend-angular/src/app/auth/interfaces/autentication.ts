export interface LoginRequest {
    email:          string,
    password:       string
}

export interface LoginResponse {
    token:          string,
    user:{
        name:       string,
        username:   string,  
        email:      string,
        id:         number
    },
    roles:          string[] | null,
}

export interface RegisterRequest {
    name:           string,
    username:       string,
    email:          string,
    password:       string
}

export interface RegisterResponse {
    accessToken:    string,
    user:{
        email:      string,
        id:         number
    }
}