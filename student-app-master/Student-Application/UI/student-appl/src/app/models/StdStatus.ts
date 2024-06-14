export interface IStdStatus{
     stdStatusId: String;
     stdStatusName: String;
     cmnStatus:String;
}

export class StdStatus implements IStdStatus {

     stdStatusId: String = '';
     stdStatusName: String = '';
     cmnStatus:String='';
}