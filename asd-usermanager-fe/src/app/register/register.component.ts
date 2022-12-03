import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {AuthService} from "../core/service/auth.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormControl, FormGroup, Validators} from "@angular/forms";

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

    registerForm: FormGroup = new FormGroup({
        userName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(32)]),
        firstName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
        lastName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
        password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(255)]),
    });

    constructor(private authService: AuthService, private router: Router,
                private matSnackbar: MatSnackBar) {
    }

    ngOnInit() {
    }

    handleRegister() {
        this.authService.registerCall({
            userName: this.registerForm.get('userName').value,
            firstName: this.registerForm.get('firstName').value,
            lastName: this.registerForm.get('lastName').value,
            password: this.registerForm.get('password').value
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

    log(): void {
        console.log(this.registerForm.get("userName"))
    }

}
