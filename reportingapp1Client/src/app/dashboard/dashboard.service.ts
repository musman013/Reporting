
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IDashboard } from './idashboard';
import { GenericApiService } from '../../../projects/fast-code-core/src/public_api';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService extends GenericApiService<IDashboard> { 
  constructor(private httpclient: HttpClient) { 
    super(httpclient, { apiUrl: environment.apiUrl }, "dashboard");
  }
  
  
}
