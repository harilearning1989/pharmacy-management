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

  loading = false;
  errorMessage: string | null = null;

  loginForm = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(5)]]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  submit(): void {
    this.tokenService.clearToken();
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = null;
    this.router.navigate(['/home']);
    /*this.authService.login(this.loginForm.value).subscribe({
      next: res => {
        this.tokenService.saveToken(res.token);
        this.router.navigate(['/dashboard']);
      },
      error: err => {
        this.loading = false;

        if (err.status === 401) {
          this.errorMessage = 'Invalid username or password';
        } else {
          this.errorMessage = 'Login failed. Please try again later.';
        }
      }
    });*/
  }

  // Clean helper for template
  get f() {
    return this.loginForm.controls;
  }

}
