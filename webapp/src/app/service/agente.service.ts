import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import {Resposta} from "../interface/resposta.interface";

@Injectable({
  providedIn: 'root'
})
export class AgenteService {
  SERVER_URL: string = 'http://localhost:8080/api/agente/v1';
  constructor(private http: HttpClient) {}

  sendFile(file: File) : Observable<HttpEvent<Resposta>> {
    const formData = new FormData();
    formData.append("file", file);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'multipart/form-data',
      },
      )
    };
    return this.http
      .post<Resposta>(this.SERVER_URL, formData, {
        reportProgress: true,
        observe: 'events'
      });
  }

}
