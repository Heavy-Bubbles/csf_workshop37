import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UploadService } from '../upload.service';
import { Params, Router } from '@angular/router';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit{

  fb = inject(FormBuilder)
  uploadSvc = inject(UploadService)
  router = inject(Router)

  uploadForm!: FormGroup;

  fileName = ""

  @ViewChild('file')
  file!: ElementRef

  ngOnInit(): void {
    this.uploadForm = this.createForm()
  }

  createForm(){
    return this.fb.group(
      { file: this.fb.control('') }
    )
  }

  process(){
    const formFile = this.file.nativeElement.files[0] as File
    this.fileName = formFile.name

    this.uploadSvc.upload(formFile)
      .then(result => {
        const id = result['id']
        const queryParams: Params = {
          name: this.fileName
        }
        this.router.navigate(["/view", id], { queryParams })
      })
  }

}
