export interface AuthenticationDto {
    authToken: string;
    refreshToken: string;
    expiresAt: Date;
    userName: string;
}
