import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {HomeRoutingModule} from './home-routing.module';
import {SidebarComponent} from "../../components/sidebar/sidebar.component";
import {HomeComponent} from "../../components/home/home.component";
import {HeaderComponent} from "../../components/header/header.component";


@NgModule({
  declarations: [
    SidebarComponent,
    HomeComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
  ]
})
export class HomeModule { }
