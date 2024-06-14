export interface IStream {
    streamId: number;
    streamName: String;
    cmnStatusId: String;
}

export class Stream implements IStream {

    streamId: number = 0;
    streamName: String = '';
    cmnStatusId: String = '';
}