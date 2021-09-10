
export enum YuServe {
    Auth,
    System
}

export const yuServePrefixMap = new Map([
    [YuServe.Auth, '/api'],
    [YuServe.System, '/api']
])

export function yuServePrefix(serve: YuServe): any {
    return yuServePrefixMap.get(serve);
};

export function yuUrlAuth(url: string) {
    return `${yuServePrefixMap.get(YuServe.Auth)}${url}`;
}

export function yuUrlSystem(url: string) {
    return `${yuServePrefixMap.get(YuServe.System)}${url}`;
}