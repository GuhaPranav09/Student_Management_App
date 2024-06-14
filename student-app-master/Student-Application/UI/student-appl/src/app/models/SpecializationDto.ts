export interface ISpecializationDto {
    specializationId: number,
    specializationName: String,
    streamId: number,
    cmnStatusId: String
    streamName: string
}
export class SpecilizationDto implements ISpecializationDto {
    specializationId: number = 0;
    specializationName: String = '';
    streamId: number = 0;
    cmnStatusId: String = '';
    streamName: string = '';
}