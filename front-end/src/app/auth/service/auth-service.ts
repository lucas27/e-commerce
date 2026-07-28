import { inject, Service } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';

@Service()
export class AuthService {
    // public needCall$ = new BehaviorSubject<boolean>(true);
    private router = inject(Router);
    
    public async login(body: object):Promise<void> {
        const response = await axios.post("http://localhost:8080/Auth/sign-in", body, {
        withCredentials: true
      });

      if(response.status === 200) {
        sessionStorage.setItem('isCall', 'true')
        this.router.navigate(['/'])
      }
    }

    public async Register(body: object):Promise<void> {
        const response = await axios.post('http://localhost:8080/Auth/sign-up', body);
      
      if(response.status === 201) {
        sessionStorage.setItem('isCall', 'true')
        this.router.navigate(['/login']);
      }
    }

    public async isValid():Promise<boolean> {
    try {
        const response = await axios.get("http://localhost:8080/Auth/validate", {
            withCredentials: true
        })
    
        if(response.status === 200) {
            return true;
        }
    }catch(error) {
      if(axios.isAxiosError(error)){
        console.error(error.response?.data);
      }
    } 
    return false;
  }

  public async logout():Promise<boolean> {
    try {
        const res =  await axios.post("http://localhost:8080/Auth/logout", {} ,{
           withCredentials: true
         })
     
         if(res.status === 200) {
             return true;
         }
         return false;
        
    }catch (err) {
        console.error(err);
        return true;
    }
  }
}
