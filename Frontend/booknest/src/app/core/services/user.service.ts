import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8765/api/users';
 
  constructor(private http: HttpClient) { }
 
  register(user: User): Observable<User> {
return this.http.post<User>(`${this.baseUrl}/register`, user);
  }
 
  login(email: string, password: string): Observable<User> {
return this.http.post<User>(`${this.baseUrl}/login`, { email, password });
  }
 
  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${userId}`);
  }
}
