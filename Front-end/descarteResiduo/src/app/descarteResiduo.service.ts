import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DescarteResiduo } from './descarteResiduo';

@Injectable({
  providedIn: 'root'
})
export class DescarteResiduoService {

  private apiUrl = 'http://localhost:8080/descarteResiduo';

  constructor(private http: HttpClient) { }

  pointRead(): Observable<DescarteResiduo[]> {
    return this.http.get<DescarteResiduo[]>(this.apiUrl);
  }

  pointCreate(descarte: DescarteResiduo): Observable<DescarteResiduo> {
    return this.http.post<DescarteResiduo>(this.apiUrl, descarte);
  }

  pointUpdate(id: number, descarte: DescarteResiduo): Observable<DescarteResiduo> {
    return this.http.put<DescarteResiduo>(`${this.apiUrl}/${id}`, descarte);
  }

  pointDelete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  pointSearch(params: any): Observable<DescarteResiduo[]> {
    let httpParams = new HttpParams();

    if (params.nome) {
      httpParams = httpParams.set('nome', params.nome);
    }
    if (params.endereco) {
      httpParams = httpParams.set('endereco', params.endereco);
    }
    if (params.zona) {
      httpParams = httpParams.set('zona', params.zona);
    }
    if (params.contatos) {
      httpParams = httpParams.set('contatos', params.contatos);
    }
    if (params.horarioExpediente) {
      httpParams = httpParams.set('horarioExpediente', params.horarioExpediente);
    }

    return this.http.get<DescarteResiduo[]>(`${this.apiUrl}/search`, { params: httpParams });
  }

}
