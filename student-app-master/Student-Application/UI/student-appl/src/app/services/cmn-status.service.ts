import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CmnStatus } from '../models/CmnStatus';
import { environment } from 'src/environments/environment';
import { CmnStatusApi } from '../api/api-paths';
@Injectable({
  providedIn: 'root',
})
export class CmnStatusService {
  constructor(private http: HttpClient) {}

  //post
  saveCmnStatus(cmnStatus: CmnStatus): Observable<CmnStatus> {
    return this.http.post<CmnStatus>(
      `${environment.baseUrl}${CmnStatusApi.SAVE}`,
      cmnStatus
    );
  }

  //getall
  getAllCmnStatus(): Observable<CmnStatus[]> {
    return this.http.get<CmnStatus[]>(
      `${environment.baseUrl}${CmnStatusApi.ALL}`
    );
  }

  //edit
  updateCmnStatus(cmnStatus: CmnStatus): Observable<CmnStatus> {
    return this.http.put<CmnStatus>(
      `${environment.baseUrl}${CmnStatusApi.UPDATE}`,
      cmnStatus
    );
  }

  //delete
  deleteCmnStatus(cmnStatusId: String): Observable<void> {
    return this.http.delete<void>(
      `${environment.baseUrl}${CmnStatusApi.DELETE}/${cmnStatusId}`
    );
  }
}
