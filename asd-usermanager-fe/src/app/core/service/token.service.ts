import {Injectable} from '@angular/core';
import {AuthenticationDto} from '../../login/model/authentication-dto';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  key = 'token';

  constructor() {
  }

  saveToken(authenticationDto: AuthenticationDto) {
    localStorage.setItem(this.key, JSON.stringify(authenticationDto));
  }

  removeToken() {
    localStorage.removeItem(this.key);
  }

  getToken(): AuthenticationDto {
    return JSON.parse(localStorage.getItem(this.key));
  }
}
