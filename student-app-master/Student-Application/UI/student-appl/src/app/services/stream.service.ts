import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Stream } from '../models/Stream';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { StreamApi } from '../api/api-paths';

@Injectable({
  providedIn: 'root',
})
export class StreamService {
  constructor(private http: HttpClient) {}

  //post
  saveStream(stream: Stream): Observable<Stream> {
    return this.http.post<Stream>(
      `${environment.baseUrl}${StreamApi.SAVE}`,
      stream
    );
  }

  //getall
  getAllStreams(): Observable<Stream[]> {
    return this.http.get<Stream[]>(`${environment.baseUrl}${StreamApi.ALL}`);
  }

  //edit
  updateStream(stream: Stream): Observable<Stream> {
    return this.http.put<Stream>(
      `${environment.baseUrl}${StreamApi.UPDATE}`,
      stream
    );
  }

  //delete
  deleteStream(streamId: number): Observable<void> {
    return this.http.delete<void>(
      `${environment.baseUrl}${StreamApi.DELETE}/${streamId}`
    );
  }
}
