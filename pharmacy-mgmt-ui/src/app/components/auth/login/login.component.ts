import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {TokenService} from "../../../services/token.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  ngOnInit(): void {
  }

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  submit() {
    this.auth.login(this.loginForm.value).subscribe({
      next: res => {
        console.log('Login Successfully')
        this.tokenService.saveToken(res.token);
        this.router.navigate(['/dashboard']);
      }
    });
  }

}
