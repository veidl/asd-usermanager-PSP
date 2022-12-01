import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginDto} from '../model/login-dto';
import {Observable} from 'rxjs';
import {AuthenticationDto} from '../model/authentication-dto';
import {RegisterDto} from "../model/register-dto";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    url = 'http://localhost:8080/';

    constructor(private http: HttpClient) {
    }

    loginCall(loginDto: LoginDto): Observable<AuthenticationDto> {
        return this.http.post<AuthenticationDto>(this.url + 'auth/login', loginDto);
    }

    logoutCall(refreshToken: string): Observable<void> {
        return this.http.post<void>(this.url + `logout/${refreshToken}`, {});
    }

    registerCall(registerDto: RegisterDto): Observable<void> {
        return this.http.post<void>(this.url + `auth/signup`, registerDto);
    }
}
