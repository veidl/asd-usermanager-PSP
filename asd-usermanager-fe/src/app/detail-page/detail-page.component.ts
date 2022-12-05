import {Component, OnInit} from '@angular/core';
import {TokenService} from '../core/service/token.service';
import {AuthenticationDto} from '../core/model/authentication-dto';
import {AuthService} from '../core/service/auth.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
    selector: 'app-detail-page',
    templateUrl: './detail-page.component.html',
    styleUrls: ['./detail-page.component.scss']
})
export class DetailPageComponent implements OnInit {

    snackbarDurationInSeconds = 5;
    tokenDetails: AuthenticationDto;

    oldPw: string;
    newPw: string;
    newPwWdh: string;

    constructor(private tokenService: TokenService, private authService: AuthService,
                private router: Router, private matSnackbar: MatSnackBar) {
        this.getTokenDetails();
    }

    ngOnInit() {
    }

    getTokenDetails() {
        return this.tokenDetails = this.tokenService.getToken();
    }

    handleLogout() {
        if (!this.tokenDetails || !this.tokenDetails.refreshToken) {
            this.matSnackbar.open(
                'You are not logged in!',
                'OK', {duration: this.snackbarDurationInSeconds * 1000});
            return;
        }
        this.authService.logoutCall(this.tokenDetails.refreshToken)
            .subscribe(() => {
                this.tokenService.removeToken();
                this.router.navigate(['login']);
            }, (error) => {
                this.matSnackbar.open(
                    error.error.message ? error.error.message : 'Could not perform logout!',
                    'OK', {duration: this.snackbarDurationInSeconds * 1000});
            });
    }

    submitNewPassword() {
        this.authService.changePassword(this.oldPw, this.newPw, this.newPwWdh)
            .subscribe(() => {
                this.matSnackbar.open(
                    'Password changed!',
                    'OK', {duration: this.snackbarDurationInSeconds * 1000});
            }, (error) => {
                this.matSnackbar.open(
                    error.error.message ? error.error.message : 'Could not change password!',
                    'OK', {duration: this.snackbarDurationInSeconds * 1000});
            })
    }

}
