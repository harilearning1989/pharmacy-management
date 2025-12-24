import {Injectable} from '@angular/core';
import {HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {TokenService} from "../services/token.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    console.log('AuthInterceptor intercept');
    const token = this.tokenService.getToken();

    if (token) {
      req = req.clone({
        setHeaders: {Authorization: `Bearer ${token}`}
      });
    }

    return next.handle(req);
  }
}

