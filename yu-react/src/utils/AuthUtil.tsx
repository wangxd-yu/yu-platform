const TOKEN_KEY = 'oauth_token'

export function getOauthToken() {
    const oauthToken = JSON.parse(localStorage.getItem(TOKEN_KEY) as string);
    return oauthToken;
}

export function isTokenEfective() {
    const oauthToken = getOauthToken();
    if (oauthToken && oauthToken.expiration > new Date().getTime()) {
        return true
    } else {
        return false
    }
}