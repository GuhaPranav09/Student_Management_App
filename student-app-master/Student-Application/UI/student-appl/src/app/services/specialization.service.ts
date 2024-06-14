import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Specialization } from '../models/Specialization';
import { environment } from 'src/environments/environment';
import { SpecializationApi } from '../api/api-paths';
import { SpecilizationDto } from '../models/SpecializationDto';
@Injectable({
  providedIn: 'root',
})
export class SpecializationService {
  constructor(private http: HttpClient) {}

  //post
  saveSpecialization(
    specialization: Specialization
  ): Observable<Specialization> {
    console.log('save');
    return this.http.post<Specialization>(
      `${environment.baseUrl}${SpecializationApi.SAVE}`,
      specialization
    );
  }

  //getall
  getAllSpecializations(): Observable<SpecilizationDto[]> {
    return this.http.get<SpecilizationDto[]>(
      `${environment.baseUrl}${SpecializationApi.ALLDTO}`
    );
  }

  getAllSpecializationsByStreamId(
    streamId: number
  ): Observable<SpecilizationDto[]> {
    return this.http.get<SpecilizationDto[]>(
      `${environment.baseUrl}${SpecializationApi.ALLDTO}/${streamId}`
    );
  }
  //edit
  updateSpecialization(
    specialization: Specialization
  ): Observable<Specialization> {
    return this.http.put<Specialization>(
      `${environment.baseUrl}${SpecializationApi.UPDATE}`,
      specialization
    );
  }

  //delete
  deleteSpecialization(specializationId: number): Observable<void> {
    return this.http.delete<void>(
      `${environment.baseUrl}${SpecializationApi.DELETE}/${specializationId}`
    );
  }
}
