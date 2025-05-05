import { Component } from '@angular/core';
import { ResSliderComponent } from "../res-slider/res-slider.component";
import { Router } from 'express';
import { RouterOutlet } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-responsable-dashbord',
  imports: [ResSliderComponent,RouterOutlet],
  templateUrl: './responsable-dashbord.component.html',
  styleUrl: './responsable-dashbord.component.css'
})
export class ResponsableDashbordComponent {

}
