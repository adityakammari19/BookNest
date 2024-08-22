import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8765/api/users';

  constructor(private http: HttpClient) {}

  getUserIdfromLocalStorage(): number {
    const storedUserId = localStorage.getItem('userId');
    return Number(storedUserId);
  }

  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${userId}`);
  }

  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/username/${username}`);
  }

  getUserAddresses(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/user/${userId}/addresses`);
  }

  addUserAddress(userId: number, address: any): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/user/${userId}/addresses`,
      address
    );
  }

  updateUser(userId: number, updatedUser: any): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${userId}`, updatedUser);
  }
}
