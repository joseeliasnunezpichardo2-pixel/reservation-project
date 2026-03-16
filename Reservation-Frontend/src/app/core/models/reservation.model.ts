export type ReservationStatus = 'ACTIVE' | 'CANCELED';

export interface Reservation {
  id: number;
  clientName: string;
  reservationDate: string; //ISO format (YYYY-MM-DD)
  reservationTime: string; //ISO format (HH:MM)
  serviceName: string;
  status: ReservationStatus;
}
