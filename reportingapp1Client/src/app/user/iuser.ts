export interface IUser {  

	accessFailedCount?: number;
	authenticationSource?: string;
	emailAddress: string;
	emailConfirmationCode?: string;
	firstName: string;
	id: number;
	isActive?: boolean;
	isEmailConfirmed?: boolean;
	isLockoutEnabled?: boolean;
	isPhoneNumberConfirmed?: string;
	lastLoginTime?: Date;
	lastName: string;
	lockoutEndDateUtc?: Date;
	password: string;
	passwordResetCode?: string;
	passwordTokenExpiration?: Date;
	phoneNumber?: string;
	profilePictureId?: number;
	twoFactorEnabled?: boolean;
	userName: string;
}
