import { Component, OnDestroy, OnInit } from '@angular/core';
import { DashBoardDto, IDashBoardDto } from 'src/app/models/DashboardDto';
import { StudentService } from 'src/app/services/student.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit, OnDestroy {
  public dashBoardDtos: IDashBoardDto[] = [];

  public registeredDto: IDashBoardDto = new DashBoardDto();
  public placedDto: IDashBoardDto = new DashBoardDto();
  public inTrainingDto: IDashBoardDto = new DashBoardDto();


  public totalCount: number=0; 
  constructor(private studentService: StudentService) { }
  ngOnInit(): void {
    this.getAllDashboardDtos();

  }
  ngOnDestroy(): void {

  }

  getAllDashboardDtos() {
    return this.studentService.getAllDashboardDtos().subscribe({
      next: (resp) => {
        
        this.dashBoardDtos = resp;
        
        console.log(JSON.stringify(this.dashBoardDtos));
        resp.map((dto) => {
          this.totalCount = this.totalCount+ dto.stdStatusCount
          if (dto.stdStatusName == 'I') {
            this.inTrainingDto = dto;

          }
          if (dto.stdStatusName == 'R') {
            this.registeredDto = dto;


          }
          if (dto.stdStatusName == 'P') {
            this.placedDto = dto;

          }
        });
      },
      error: (err) => {
        console.error(err);
      },

    });


  }
}
