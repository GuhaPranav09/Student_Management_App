export interface ISpecialization{



    specializationName: String;
    cmnStatusId: String;
    specializationId: number;
    streamId: number;
    
}

export class Specialization implements ISpecialization {

    specializationName: String = '';
    cmnStatusId: String = '';
    specializationId: number = 0;
    streamId: number = 0;
}