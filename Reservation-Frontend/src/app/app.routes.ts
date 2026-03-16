import { Routes } from '@angular/router';
import { ReservationsPageComponent } from './features/reservations/pages/reservations-page/reservations-page.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'reservations',
    pathMatch: 'full',
  },
  {
    path: 'reservations',
    component: ReservationsPageComponent,
  },
];
