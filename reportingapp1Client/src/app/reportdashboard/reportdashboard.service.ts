
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IReportdashboard } from './ireportdashboard';
import { GenericApiService } from '../../../projects/fast-code-core/src/public_api';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportdashboardService extends GenericApiService<IReportdashboard> { 
  constructor(private httpclient: HttpClient) { 
    super(httpclient, { apiUrl: environment.apiUrl }, "reportdashboard");
  }
  
  
}
