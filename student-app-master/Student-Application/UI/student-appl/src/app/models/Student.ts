export interface IStudent {
    stdId: number;
    stdName: String;
    yop: String;
    streamId: number;
    specializationId: number;
    qualPercentage: String;
    contactNo: String;
    email: String;
    referedBy: String;
    address: String;
    stdStatusId: String;
    amountPaid:number;
    interestedInId:number;
    dateOfJoin:string;

}

export class Student implements IStudent {

    stdId: number = 0;
    stdName: String = '';
    yop: String = '';
    streamId: number = 0;
    specializationId: number = 0;
    qualPercentage: String = '';
    contactNo: String = '';
    email: String = '';
    referedBy: String ='';
    address: String = '';
    stdStatusId: String = '';
    amountPaid:number=0;
    interestedInId:number=0;
    dateOfJoin:string='';
}