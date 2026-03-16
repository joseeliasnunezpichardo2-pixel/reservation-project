import { ChangeDetectionStrategy, Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ReservationService } from '../../../../core/services/reservation.service';
import { Reservation } from '../../../../core/models/reservation.model';

@Component({
  selector: 'app-reservations-page',
  standalone: true,
  templateUrl: './reservations-page.component.html',
  styleUrls: ['./reservations-page.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, ReactiveFormsModule],
})
export class ReservationsPageComponent implements OnInit {
  private readonly reservationService = inject(ReservationService);
  private readonly formBuilder = inject(FormBuilder);

  readonly reservations = signal<Reservation[]>([]);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);
  readonly toastMessage = signal<string | null>(null);

  readonly reservationForm: FormGroup = this.formBuilder.group({
    nombreCliente: ['', Validators.required],
    fecha: ['', Validators.required],
    hora: ['', Validators.required],
    servicio: ['', Validators.required],
  });

  readonly availableServices: string[] = [
    'Asesoría en arquitectura de software',
    'Revisión de infraestructura en la nube',
    'Optimización de rendimiento de aplicaciones',
    'Diseño de pipelines CI/CD',
    'Evaluación de seguridad y cumplimiento',
  ];

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.loading.set(true);
    this.error.set(null);

    this.reservationService.getAll().subscribe({
      next: (data) => {
        this.reservations.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load reservations. Please try again.');
        this.loading.set(false);
      },
    });
  }

  cancelReservation(id: number): void {
    this.loading.set(true);
    this.error.set(null);

    this.reservationService.cancel(id).subscribe({
      next: () => {
        this.reservations.update((current) =>
          current.filter((reservation) => reservation.id !== id)
        );
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to cancel reservation. Please try again.');
        this.loading.set(false);
      },
    });
  }

  submitReservation(): void {
    if (this.reservationForm.invalid) {
      this.reservationForm.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.error.set(null);
    this.toastMessage.set(null);

    const formValue = this.reservationForm.value;

    const newReservation: Reservation = {
      id: 0,
      clientName: formValue.nombreCliente,
      reservationDate: formValue.fecha,
      reservationTime: formValue.hora,
      serviceName: formValue.servicio,
      status: 'ACTIVE',
    };

    this.reservationService.create(newReservation).subscribe({
      next: (created) => {
        this.reservations.update((current) => [...current, created]);
        this.reservationForm.reset();
        this.loading.set(false);
      },
      error: () => {
        this.toastMessage.set('Ocurrió un error al guardar la reserva. Inténtalo de nuevo.');
        this.loading.set(false);
      },
    });
  }
}