import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IUser } from 'src/app/models/User';
import { Userservice } from 'src/app/services/userservice.service';
import { pageSizeOptions } from 'src/app/utils/util';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent {
  public users: IUser[] = [];
  public name:string='';

  public page: number = 0;
  public size: number = 10;
  public totalPages: number = 0;
  public first: boolean = true;
  public last: boolean = false;
  public pageSizes: number[] = pageSizeOptions;

  private destroy$ = new Subject<void>();
  public count: number=0;
 public flag: boolean=false;
  isSerchingByStdName: boolean=false;
  // isSerchingByUserName: boolean;

  constructor(private router: Router, private userService: Userservice) {}
  ngOnInit(): void {
    this.getallUsers();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  editUser(user: IUser) {
    this.router.navigate(['dashboard/edituser'], {
      state: { user: user },
    });
  }

  getallUsers() {
    this.userService
      .getAllUsers(this.page,this.size)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
         
          console.log(JSON.stringify(resp));
          this.users = resp.records;
          this.totalPages = resp.totalPages;
          this.first = resp.first;
          this.last = resp.last;
          this.count=resp.count;
          console.log(this.page);
          
          if(this.count>10)
          {
            console.log("true");
            this.flag=true;
          }
          else{
            console.log("false");
            this.flag=false;
          }
        },
        error: (err) => {
          console.error(err);
        },
      });
  }


  addUser() {
    this.router.navigate(['dashboard/adduser']);
  }

  removeUser(userId: number) {
    this.userService
      .removeUser(userId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          console.log(JSON.stringify(resp));

          Swal.fire({
            position: 'center',
            icon: 'success',
            text: 'User Deleted Successfully...',
            showConfirmButton: false,
            timer: 2000,
          });
          setTimeout(() => {
            this.getallUsers();
          },2000)
        },
        error: (err) => {
          console.error(err);
          this.getallUsers();
        },
      });
  }

  

  getUsersByName() {
    // this.isSerchingByRefferedStdName = false;
     this.isSerchingByStdName = true;
    this.userService
      .searchByUserName(this.name,this.page, this.size)
      .subscribe(
        (resp: any) => {
          console.log(resp);
          this.users = resp.records;
          console.log(this.users);
          this.totalPages = resp.totalPages;
          this.first = resp.first;
          this.last = resp.last;
          this.count=resp.count;
          console.log(this.page);
          
          if(this.count>10)
          {
            console.log("true");
            this.flag=true;
          }
          else{
            console.log("false");
            this.flag=false;
          }
          
          
          // this.page = 0;
        },
        (err: any) => {
          console.error(err);
          this.getallUsers();
          //  //alert("dat not found")
        }
      );
  }
  userName(userName: any, page: number, size: number) {
    throw new Error('Method not implemented.');
  }

  printSize(event: any) {
    this.size = event.target.value;
    this.page = 0;
    if (this.isSerchingByStdName) {
      this.getUsersByName();
    }
    else{
    this.getallUsers();
    }
  }
  goToPage(pageNumber: number) {
    this.page = pageNumber;
    if (this.isSerchingByStdName) {
      this.getUsersByName();
    }
    else{
    this.getallUsers();
    }
  }
  goToNextOrPreviousr(direction: string) {
    // this.page = direction === 'forward' ? this.page + 1 : this.page - 1;
    if (direction === 'forward' && !this.last) {
      this.page += 1;
      console.log(this.page)
    } else if (direction === 'backward' && !this.first) {
      console.log(this.page)
      this.page -= 1; 
      console.log(this.page)
    }
    if (this.isSerchingByStdName) {
      this.getUsersByName();
    }
    else{
    this.getallUsers();
    }


    this.userService.searchByUserName(this.name, 1, 10).subscribe(
      (resp: any) => {
        console.log(resp);
        this.users = resp;
      },
      (err: any) => {
        console.error(err);
        this.getallUsers();
        //  //alert("dat not found")
      }
    );
  }






 
  // goToNextOrPreviousr(direction: string) {
  //   // this.page = direction === 'forward' ? this.page + 1 : this.page - 1;

  //   if (direction === 'forward' && !this.last) {
  //     this.page += 1;
  //     console.log(this.page)
  //   } else if (direction === 'backward' && !this.first) {
  //     console.log(this.page)
  //     this.page -= 1; 
  //     console.log(this.page)
  //   }
  //   if (this.isSerchingByStdName) {
  //     this.getStudentsBystdName();
  //   } else if (this.isSerchingByRefferedStdName) {
  //     this.getStudentsByReferName();
  //   } else {
  //     this.getallstudents();
  //   }
  //   this.studentservice.getStudentsByStdName(this.studentName, 1, 10).subscribe(
  //     (resp: any) => {
  //       console.log(resp);
  //       this.students = resp;
  //     },
  //     (err: any) => {
  //       console.error(err);
  //       this.getallstudents();
  //       //  //alert("dat not found")
  //     }
  //   );
  // }
  
}
