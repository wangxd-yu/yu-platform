const TOKEN_KEY = 'auth_token'

export function getAuthToken() {
    const authToken = JSON.parse(localStorage.getItem(TOKEN_KEY) as string);
    return authToken;
}

export function isTokenEfective() {
    const authToken = getAuthToken();
    if (authToken && authToken.expiration * 1000 > new Date().getTime()) {
        return true
    } else {
        return false
    }
}