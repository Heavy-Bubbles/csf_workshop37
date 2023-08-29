import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  http = inject(HttpClient)

  getUrl(id: string) {
    return firstValueFrom(
      this.http.get<any>(`/file/${id}`)
    )
  }

  upload(file: File){
    const form = new FormData()
    form.set("file", file)

    return firstValueFrom(
      this.http.post<any>('/upload', form)
    )
  }
}
