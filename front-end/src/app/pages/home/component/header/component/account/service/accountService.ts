import { Service } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Service()
export class AccountService {
    public needCall$ = new BehaviorSubject<boolean>(true);
}