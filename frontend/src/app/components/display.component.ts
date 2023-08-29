import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UploadService } from '../upload.service';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit{

  fileName = ""
  fileId = ""
  fileUrl = ""

  activatedRoute = inject(ActivatedRoute)
  uploadSvc = inject(UploadService)

  ngOnInit(): void {

    this.fileName = this.activatedRoute.snapshot.queryParams['name']
    this.fileId = this.activatedRoute.snapshot.params['id']
    this.uploadSvc.getUrl(this.fileId)
      .then(result => {
        this.fileUrl = result['url']
        console.info('>>> ', this.fileUrl)
      })
    
  }

}
