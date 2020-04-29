
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LayoutModule } from '@angular/cdk/layout';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/index';
import { ErrorPageComponent  } from './error-page/error-page.component';
/** core components and filters for authorization and authentication **/

import { AuthenticationService } from './core/authentication.service';
import { AuthGuard } from './core/auth-guard';
import { JwtInterceptor } from './core/jwt-interceptor';
import { JwtErrorInterceptor } from './core/jwt-error-interceptor';
import { GlobalPermissionService } from './core/global-permission.service';
import { LoginComponent } from './login/index';

/** end of core components and filters for authorization and authentication **/

import { EntityHistoryComponent } from "./entity-history/entity-history.component";

import {
  MatButtonModule, MatToolbarModule, MatSidenavModule,
  MatIconModule, MatListModule, MatRadioModule, MatTableModule,
  MatCardModule, MatTabsModule, MatInputModule, MatDialogModule,
  MatSelectModule, MatCheckboxModule, MatAutocompleteModule,
  MatDatepickerModule, MatNativeDateModule, MatMenuModule, MatSortModule,
  MatPaginatorModule, MatProgressSpinnerModule, MatSnackBarModule
} from '@angular/material';
import {MatChipsModule} from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';

import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { routingModule } from './app.routing';
import { FastCodeCoreModule } from 'projects/fast-code-core/src/public_api';

/** common components and filters **/

import { MainNavComponent } from './common/components/main-nav/main-nav.component';
import { BottomTabNavComponent } from './common/components/bottom-tab-nav/bottom-tab-nav.component';

/** end of common components and filters **/

import { environment } from '../environments/environment';

import { RolepermissionListComponent, RolepermissionDetailsComponent, RolepermissionNewComponent } from './rolepermission/index';
import { RoleListComponent, RoleDetailsComponent, RoleNewComponent } from './role/index';
import { UserpermissionListComponent, UserpermissionDetailsComponent, UserpermissionNewComponent } from './userpermission/index';
import { ReportListComponent, ReportDetailsComponent, ReportNewComponent } from './report/index';
import { ReportdashboardListComponent, ReportdashboardDetailsComponent, ReportdashboardNewComponent } from './reportdashboard/index';
import { PermissionListComponent, PermissionDetailsComponent, PermissionNewComponent } from './permission/index';
import { UserroleListComponent, UserroleDetailsComponent, UserroleNewComponent } from './userrole/index';
import { UserListComponent, UserDetailsComponent, UserNewComponent } from './user/index';
import { DashboardListComponent, DashboardDetailsComponent, DashboardNewComponent } from './dashboard/index';

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}

@NgModule({
	declarations: [
		ErrorPageComponent,
		HomeComponent,
		DashboardComponent,
		LoginComponent,
		AppComponent,
		MainNavComponent,
		BottomTabNavComponent,
		RolepermissionListComponent,
		RolepermissionDetailsComponent,
		RolepermissionNewComponent,
		RoleListComponent,
		RoleDetailsComponent,
		RoleNewComponent,
		UserpermissionListComponent,
		UserpermissionDetailsComponent,
		UserpermissionNewComponent,
		ReportListComponent,
		ReportDetailsComponent,
		ReportNewComponent,
		ReportdashboardListComponent,
		ReportdashboardDetailsComponent,
		ReportdashboardNewComponent,
		PermissionListComponent,
		PermissionDetailsComponent,
		PermissionNewComponent,
		UserroleListComponent,
		UserroleDetailsComponent,
		UserroleNewComponent,
		UserListComponent,
		UserDetailsComponent,
		UserNewComponent,
		DashboardListComponent,
		DashboardDetailsComponent,
		DashboardNewComponent,
		EntityHistoryComponent,
  ],
  imports: [
    BrowserModule,
    routingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatTabsModule,
    MatIconModule,
    MatListModule,
    MatExpansionModule,
    MatRadioModule,
    MatTableModule,
    MatCardModule,
    MatSelectModule,
    MatCheckboxModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatMenuModule,
    MatSortModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatChipsModule,
    NgxMaterialTimepickerModule,
    FastCodeCoreModule.forRoot({
      apiUrl: environment.apiUrl
    }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),

  ],
  providers: [
		AuthenticationService,
		GlobalPermissionService,
		{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
		{ provide: HTTP_INTERCEPTORS, useClass: JwtErrorInterceptor, multi: true },
		AuthGuard,
	],
  bootstrap: [AppComponent],
  entryComponents: [
  ]
})
export class AppModule {
}
