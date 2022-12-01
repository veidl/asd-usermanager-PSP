export interface AuthenticationDto {
    authToken: string;
    refreshToken: string;
    expiresAt: string;
    userName: string;
}
