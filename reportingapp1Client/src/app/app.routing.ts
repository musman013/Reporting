
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'projects/fast-code-core/src/public_api';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ErrorPageComponent  } from './error-page/error-page.component';
import { LoginComponent } from './login/index';
import { AuthGuard } from './core/auth-guard';
import { EntityHistoryComponent } from "./entity-history/entity-history.component";

import { RolepermissionListComponent, RolepermissionDetailsComponent, RolepermissionNewComponent } from './rolepermission/index';
import { RoleListComponent, RoleDetailsComponent, RoleNewComponent } from './role/index';
import { UserpermissionListComponent, UserpermissionDetailsComponent, UserpermissionNewComponent } from './userpermission/index';
import { ReportListComponent, ReportDetailsComponent, ReportNewComponent } from './report/index';
import { ReportdashboardListComponent, ReportdashboardDetailsComponent, ReportdashboardNewComponent } from './reportdashboard/index';
import { PermissionListComponent, PermissionDetailsComponent, PermissionNewComponent } from './permission/index';
import { UserroleListComponent, UserroleDetailsComponent, UserroleNewComponent } from './userrole/index';
import { UserListComponent, UserDetailsComponent, UserNewComponent } from './user/index';
import { DashboardListComponent, DashboardDetailsComponent, DashboardNewComponent } from './dashboard/index';

const routes: Routes = [
	
	{ path: 'dashboard',  component: DashboardComponent ,canActivate: [ AuthGuard ]  },
	{ path: 'login', component: LoginComponent },
	{ path: 'login/:returnUrl', component: LoginComponent },
	{ path: 'rolepermission', component: RolepermissionListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'rolepermission/new', component: RolepermissionNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'rolepermission/:id', component: RolepermissionDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'role', component: RoleListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'role/new', component: RoleNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'role/:id', component: RoleDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'userpermission', component: UserpermissionListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'userpermission/new', component: UserpermissionNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'userpermission/:id', component: UserpermissionDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'report', component: ReportListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'report/new', component: ReportNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'report/:id', component: ReportDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'reportdashboard', component: ReportdashboardListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'reportdashboard/new', component: ReportdashboardNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'reportdashboard/:id', component: ReportdashboardDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'permission', component: PermissionListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'permission/new', component: PermissionNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'permission/:id', component: PermissionDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'userrole', component: UserroleListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'userrole/new', component: UserroleNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'userrole/:id', component: UserroleDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'user', component: UserListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'user/new', component: UserNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'user/:id', component: UserDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'dashboard', component: DashboardListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ]},
	{ path: 'dashboard/new', component: DashboardNewComponent, canActivate: [ AuthGuard ] },
	{ path: 'dashboard/:id', component: DashboardDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  { path: "entityHistory", component: EntityHistoryComponent,canActivate: [ AuthGuard ]},
  { path: '', component: HomeComponent },
  //{ path: '', redirectTo: '/', pathMatch: 'full' },
  { path: '**', component:ErrorPageComponent},
	
];

export const routingModule: ModuleWithProviders = RouterModule.forRoot(routes);