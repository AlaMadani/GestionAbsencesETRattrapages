// app/interceptors/auth.interceptor.ts
import { Injectable } from '@angular/core';
import { HttpInterceptorFn, HttpRequest, HttpHandlerFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  // automatically attach credentials to every request
  const cloned = req.clone({ withCredentials: true });
  return next(cloned);
};
