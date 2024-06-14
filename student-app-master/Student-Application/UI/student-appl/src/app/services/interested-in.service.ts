import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { InterestedIn } from '../models/InterestedIn';
import { environment } from 'src/environments/environment';
import { interestedInApi } from '../api/api-paths';
@Injectable({
  providedIn: 'root',
})
export class InterestedInService {
  constructor(private http: HttpClient) {}

  //post
  saveInterestedIn(interestedIn: InterestedIn): Observable<InterestedIn> {
    return this.http.post<InterestedIn>(
      `${environment.baseUrl}${interestedInApi.SAVE}`,
      interestedIn
    );
  }

  //getall
  getAllInterestedIn(): Observable<InterestedIn[]> {
    return this.http.get<InterestedIn[]>(
      `${environment.baseUrl}${interestedInApi.ALL}`
    );
  }

  //edit
  updateInterestedIn(interestedIn: InterestedIn): Observable<InterestedIn> {
    return this.http.put<InterestedIn>(
      `${environment.baseUrl}${interestedInApi.UPDATE}`,
      interestedIn
    );
  }

  //delete
  deleteInterestedIn(interestedInId: number): Observable<void> {
    return this.http.delete<void>(
      `${environment.baseUrl}${interestedInApi.DELETE}/${interestedInId}`
    );
  }
}
