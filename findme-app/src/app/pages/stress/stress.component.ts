import { Component, OnInit } from '@angular/core';
import { StressTestService } from '../../services/stress.service';
import { jwtDecode } from 'jwt-decode';
import { CommonModule } from '@angular/common';
import {
  NgApexchartsModule,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexStroke,
  ApexTitleSubtitle,
  ApexYAxis,
  ApexLegend
} from 'ng-apexcharts';
import { Router } from '@angular/router';

@Component({
  selector: 'app-stress',
  templateUrl: './stress.component.html',
  styleUrls: ['./stress.component.scss'],
  standalone: true,
  imports: [CommonModule, NgApexchartsModule]
})
export class StressComponent implements OnInit {
  userId = 0;
  results: { users: number; time: number }[] = [];
  loading = false;

  readonly keyPoints = [1, 5, 10, 50, 100, 500, 1000];

  public chartOptions: {
    series: ApexAxisChartSeries;
    chart: ApexChart;
    xaxis: ApexXAxis;
    yaxis: ApexYAxis;
    dataLabels: ApexDataLabels;
    stroke: ApexStroke;
    title: ApexTitleSubtitle;
    legend: ApexLegend;
  };

  constructor(private stressService: StressTestService, private router: Router) {
    this.chartOptions = {
      series: [{
        name: "Время (с)",
        data: []
      }],
      chart: {
        height: 350,
        type: "line",
        zoom: { enabled: false }
      },
      dataLabels: { enabled: false },
      stroke: { curve: "smooth" },
      title: { text: "Время от количества пользователей", align: "left" },
      xaxis: {
        categories: [],
        title: { text: 'Количество пользователей' }
      },
      yaxis: { title: { text: "Время (с)" } },
      legend: { position: "top" }
    };
  }

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decoded: any = jwtDecode(token);
        this.userId = decoded?.user_id || 0;
      } catch (error) {
        console.error('Ошибка декодирования JWT:', error);
      }
    }
  }

  get filteredResults() {
    return this.results.filter(r => this.keyPoints.includes(r.users));
  }

  runTest(): void {
    this.results = [];
    this.loading = true;

    this.chartOptions = {
      ...this.chartOptions,
      series: [{
        name: "Время (с)",
        data: []
      }],
      xaxis: {
        categories: [],
        title: { text: 'Количество пользователей' }
      }
    };

    this.stressService.runStressTest(this.userId, (count, time) => {
      this.results.push({ users: count, time: +(time / 1000).toFixed(3) });

      this.chartOptions = {
        ...this.chartOptions,
        series: [{
          name: "Время (с)",
          data: this.results.map(r => r.time)
        }],
        xaxis: {
          categories: this.results.map(r => r.users.toString()),
          title: { text: 'Количество пользователей' }
        }
      };
    }).subscribe({
      complete: () => this.loading = false,
      error: err => {
        console.error('Ошибка в стресс-тесте', err);
        this.loading = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/profile']);
  }
}
