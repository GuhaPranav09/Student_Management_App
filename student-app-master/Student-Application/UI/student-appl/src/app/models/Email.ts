export interface IEmail {
    toAddress: String;
    ccAddress: String;
    emailsubject: string;
    emailBody: string
}

export class Email implements IEmail {
    toAddress: String = '';
    ccAddress: String = '';
    emailsubject: string = '';
    emailBody: string = ''

}
