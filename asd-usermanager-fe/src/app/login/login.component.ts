import {Component, OnInit} from '@angular/core';
import {AuthService} from '../core/service/auth.service';
import {TokenService} from '../core/service/token.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    snackbarDurationInSeconds = 5;
    userName: string;
    password: string;

    errorMsg = '';

    constructor(private loginService: AuthService, private tokenService: TokenService,
                private matSnackbar: MatSnackBar, private router: Router) {
    }

    ngOnInit() {
    }

    handleLogin() {
        this.loginService.loginCall({userName: this.userName, password: this.password})
            .subscribe(response => {
                    this.tokenService.saveToken(response);
                    this.router.navigate(['detail']);
                },
                error => {
                    if (error.status === 400 && error.error) {
                        this.matSnackbar.open(
                            error.error.message ? error.error.message : 'Could not login!',
                            'OK', {duration: this.snackbarDurationInSeconds * 1000});
                    }
                });
    }
}
