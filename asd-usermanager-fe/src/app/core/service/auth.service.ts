import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginDto} from '../model/login-dto';
import {Observable} from 'rxjs';
import {AuthenticationDto} from '../model/authentication-dto';
import {RegisterDto} from "../model/register-dto";
import {TokenService} from "./token.service";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    url = 'http://localhost:8080/';

    constructor(private http: HttpClient, private tokenService: TokenService) {
    }

    loginCall(loginDto: LoginDto): Observable<AuthenticationDto> {
        return this.http.post<AuthenticationDto>(this.url + 'auth/login', loginDto, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    logoutCall(refreshToken: string): Observable<void> {
        return this.http.post<void>(this.url + `logout/${refreshToken}`, {}, {
            headers: {
                'Authorization': 'Bearer ' + this.tokenService.getToken().authToken
            }
        });
    }

    registerCall(registerDto: RegisterDto): Observable<void> {
        return this.http.post<void>(this.url + `auth/signup`, registerDto);
    }

    checkBackendHealth(): Observable<void> {
        return this.http.get<void>(this.url + 'actuator/health');
    }

    changePassword(oldPw: string, newPw: string, newPwWdh: string) {
        return this.http.post<void>(this.url + `password`, {
            currentPassword: oldPw,
            newPassword: newPw,
            verifiedPassword: newPwWdh
        }, {
            headers: {
                'Authorization': 'Bearer ' + this.tokenService.getToken().authToken
            }
        });
    }
}
