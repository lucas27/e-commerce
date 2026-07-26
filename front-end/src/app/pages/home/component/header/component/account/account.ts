import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LucideCircleUser } from '@lucide/angular';
import axios from 'axios';
import { AccountService } from './service/accountService';

@Component({
  selector: 'app-account',
  imports: [LucideCircleUser, RouterLink],
  templateUrl: './account.html',
  styleUrl: './account.css',
})
export class Account {
  public isLogged:boolean = false;
  private service = inject(AccountService);
  private Call$ = this.service.needCall$;

  private ref = inject(ChangeDetectorRef); 

  ngOnInit(){
    if(this.Call$.getValue()) {
      this.validate();
    }
    // console.log(this.Call$.getValue())
    // this.validate();
  }

  public async validate() {
    try {
      const response = await axios.get("http://localhost:8080/Auth/validate", {
        withCredentials: true
      })

      if(response.status === 200) {
        this.isLogged = true;
      }
      
      // console.log(response);
      
    }catch(error) {
      if(axios.isAxiosError(error)){
        console.error(error.response?.data);
        // console.log(error.response?.data);
      }
    } finally {
      this.ref.detectChanges();
    }
    // console.log(this.isLogged);
  }

  public async logout() {
   const res =  await axios.post("http://localhost:8080/Auth/logout", {} ,{
      withCredentials: true
    })
    if(res.status === 200) {
      this.isLogged = false;
      this.ref.detectChanges();
    }
    // console.log(res)
  }
}
