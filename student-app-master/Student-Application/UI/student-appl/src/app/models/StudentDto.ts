export interface IStudentDto {
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
  amountPaid: number;
  streamName: String;
  streamStatusId: string;
  specializationName: string;
  specializationStatusId: string;
  stdStatusName: string;
  interestedInId:number;
  dateOfJoin:string;
}

export class StudentDto implements IStudentDto {
  stdId: number = 0;
  stdName: String = '';
  yop: String = '';
  streamId: number = 0;
  specializationId: number = 0;
  qualPercentage: String = '';
  contactNo: String = '';
  email: String = '';
  referedBy: String = '';
  address: String = '';
  stdStatusId: String = '';
  amountPaid: number = 0;
  streamName: String = '';
  streamStatusId: string = '';
  specializationName: string = '';
  specializationStatusId: string = '';
   stdStatusName: string = '';
  interestedInId:number=0;
  dateOfJoin:string='';
}
