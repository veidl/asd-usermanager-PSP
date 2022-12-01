import {Component, OnInit} from '@angular/core';
import {AuthService} from "../core/service/auth.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    snackbarDurationInSeconds = 5;

    userName: string;
    firstName: string;
    lastName: string;
    password: string;

    constructor(private authService: AuthService, private router: Router,
                private matSnackbar: MatSnackBar) {
    }

    ngOnInit() {
    }

    handleRegister() {
        this.authService.registerCall({
            userName: this.userName,
            firstName: this.firstName,
            lastName: this.lastName,
            password: this.password
        })
            .subscribe(() => {
                this.handleLoginRoute();
            }, () => {
                this.matSnackbar.open(
                    'Could not perform register!',
                    'OK', {duration: this.snackbarDurationInSeconds * 1000});

            })
    }

    handleLoginRoute() {
        this.router.navigate(['login']);
    }

}
