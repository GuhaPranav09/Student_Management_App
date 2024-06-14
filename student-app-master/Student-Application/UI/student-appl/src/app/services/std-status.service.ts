import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { StdStatus } from '../models/StdStatus';
import { environment } from 'src/environments/environment';
import { StdStatusApi, StudentApi } from '../api/api-paths';

@Injectable({
  providedIn: 'root',
})
export class StdStatusService {
  constructor(private http: HttpClient) {}

  //post
  saveStdStatus(stdStatus: StdStatus): Observable<StdStatus> {
    return this.http.post<StdStatus>(
      `${environment.baseUrl}${StdStatusApi.SAVE}`,
      stdStatus
    );
  }

  //getall
  getAllStdStatus(): Observable<StdStatus[]> {
    return this.http.get<StdStatus[]>(
      `${environment.baseUrl}${StdStatusApi.ALL}`
    );
  }

  //edit
  updateStdStatus(stdStatus: StdStatus): Observable<StdStatus> {
    return this.http.put<StdStatus>(
      `${environment.baseUrl}${StdStatusApi.UPDATE}`,
      stdStatus
    );
  }

  //delete
  deleteStdStatus(stdStatusId: String): Observable<void> {
    console.log('deleted serviece');

    return this.http.delete<void>(
      `${environment.baseUrl}${StdStatusApi.DELETE}/${stdStatusId}`
    );
  }
}
