import { Component, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
import { Router, Event } from '@angular/router';
import { MatSidenav, MatSidenavContent } from '@angular/material';
import { AuthenticationService } from 'src/app/core/authentication.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { 
	AuthEntities,
	Entities,


	} from './entities';

import { FastCodeCoreTranslateUiService, Globals } from 'projects/fast-code-core/src/public_api';

@Component({
	selector: 'app-main-nav',
	templateUrl: './main-nav.component.html',
	styleUrls: ['./main-nav.component.scss']
})
export class MainNavComponent {	
	@ViewChild("drawer", { static: false }) drawer: MatSidenav;
	@ViewChild("navContent", { static: false }) navContent: MatSidenavContent;
	
	appName: string = 'reportingapp1';
	selectedLanguage: string;
	entityList = Entities;

	hasTaskAppPermission: boolean = false;
	hasAdminAppPermission: boolean = false;

	isSmallDevice$: Observable<boolean>;
	isMediumDevice$: Observable<boolean>;
	isCurrentRootRoute: boolean = true;
	
	permissions = {};
	authEntityList = AuthEntities;
	allEntities: string[] = [
		...AuthEntities,
		...Entities,


	];
	
	constructor(
		private router: Router,
		public translate: TranslateService,
		public Global: Globals,
    private fastCodeCoreTranslateUiService: FastCodeCoreTranslateUiService,


		public authenticationService: AuthenticationService,
		public globalPermissionService: GlobalPermissionService,
	) {

		this.isSmallDevice$ = Global.isSmallDevice$;
		this.isMediumDevice$ = Global.isMediumDevice$;

		this.router.events.subscribe((event: Event) => {
			this.isCurrentRootRoute = (this.router.url == '/') ? true : false;
		});
		
		this.selectedLanguage = localStorage.getItem('selectedLanguage');
		this.authenticationService.permissionsChange.subscribe(() => {
			this.setPermissions();
		});
		this.setPermissions();
	}

	switchLanguage(language: string) {
	  if(this.translate.translations[language]){
      this.translate.use(language);
    }else{
      this.translate.use(language).subscribe(() => {
        this.fastCodeCoreTranslateUiService.init(language);


      });
    }
    localStorage.setItem('selectedLanguage', language);
    this.selectedLanguage = language;
	}
	
	setPermissions(){
		this.allEntities.forEach(entity => {
			this.permissions[entity] = this.globalPermissionService.hasPermissionOnEntity(entity,"READ");
		});
		this.permissions['ENTITYHISTORY'] = this.globalPermissionService.hasPermission('ENTITYHISTORY');
	}
	
	login() {
		this.router.navigate(['/login'], { queryParams: { returnUrl: 'dashboard' } });
  }
  
  logout() {
		this.authenticationService.logout();
		this.router.navigate(['/']);
	}
	
}