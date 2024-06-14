export interface IPageResponse<T> {
  pageNo: number;
  pageSize: number;
  last: boolean;
  first: boolean;
  totalPages: number;
  totalRecords: number;
  records: T;
  count: number;
}

export class PageResponse<T> implements IPageResponse<T> {
  constructor(
    public pageNo: number,
    public pageSize: number,
    public last: boolean,
    public first: boolean,
    public totalPages: number,
    public totalRecords: number,
    public records: T,
    public count: number
  ) {}
}