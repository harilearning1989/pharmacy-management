import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, throwError} from 'rxjs';
import {TokenService} from "../services/token.service";
import {Router} from "@angular/router";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(
    private tokenService: TokenService,
    private router: Router
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {

        // Token expired / invalid
        /*
        if (error.status === 401) {
          this.tokenService.clearToken();
          this.router.navigate(['/auth/login']);
        }
          */

        // Server down / network error
        if (error.status === 0) {
          console.error('Server unreachable');
        }

        return throwError(() => error);
      })
    );
  }
}

