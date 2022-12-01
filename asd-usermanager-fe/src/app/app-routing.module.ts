import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {DetailPageComponent} from './detail-page/detail-page.component';
import {LoginComponent} from './login/login.component';


const routes: Routes = [
    {
        path: 'detail',
        component: DetailPageComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
