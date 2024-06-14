export interface IInterestedIn{



    interestedInName: String;
    cmnStatusId: String;
    interestedInId: number;
    
}

export class InterestedIn implements IInterestedIn {

    interestedInName: String = '';
    cmnStatusId: String = '';
    interestedInId: number = 0;
     
}