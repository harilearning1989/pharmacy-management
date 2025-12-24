import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  ngOnInit(): void {
  }

  registerForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: ['', Validators.required],
    roles: [['ROLE_USER']]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {}

  toggleRole(role: string, checked: boolean): void {
    const roles = this.registerForm.value.roles || [];

    if (checked && !roles.includes(role)) {
      roles.push(role);
    }

    if (!checked) {
      const index = roles.indexOf(role);
      if (index > -1) roles.splice(index, 1);
    }

    this.registerForm.patchValue({ roles });
  }

  submit(): void {
    if (this.registerForm.invalid) return;

    const payload = {
      ...this.registerForm.value,
      phoneNumber: Number(this.registerForm.value.phoneNumber)
    };

    this.authService.register(payload).subscribe({
      next: () => alert('Registration successful'),
      error: () => alert('Registration failed')
    });
  }

  onRoleChange(event: Event, role: string): void {
    const input = event.target as HTMLInputElement; // TypeScript casting in TS, not template
    const checked = input.checked;

    const roles = this.registerForm.value.roles || [];

    if (checked && !roles.includes(role)) {
      roles.push(role);
    } else if (!checked && roles.includes(role)) {
      const index = roles.indexOf(role);
      roles.splice(index, 1);
    }

    this.registerForm.patchValue({ roles });
  }


}
