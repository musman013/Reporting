
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IReport } from './ireport';
import { GenericApiService } from '../../../projects/fast-code-core/src/public_api';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService extends GenericApiService<IReport> { 
  constructor(private httpclient: HttpClient) { 
    super(httpclient, { apiUrl: environment.apiUrl }, "report");
  }
  
  
}
