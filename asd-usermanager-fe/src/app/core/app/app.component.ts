import {Component} from '@angular/core';
import {Subscription, timer} from "rxjs";
import {tap} from "rxjs/operators";
import {AuthService} from "../service/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'asd-usermanager-fe';

  backendCheck$: Subscription;
  backendAvailable: boolean = true;

  constructor(private authService: AuthService) {
    this.backendCheck$ = timer(0, 5000)
      .pipe(
        tap(() => {
          this.authService.checkBackendHealth()
            .subscribe(() => this.backendAvailable = true,
              () => this.backendAvailable = false)
        }),
        // takeWhile(() => this.backendAvailable)
      ).subscribe(console.log)
  }
}
