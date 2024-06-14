export interface IDashBoardDto {
  stdStatusName: string;
  stdStatusCount: number;
}

export class DashBoardDto implements IDashBoardDto {
  stdStatusName = '';
  stdStatusCount = 0;
}
