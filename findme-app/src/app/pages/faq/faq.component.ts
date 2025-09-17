import { Component, OnInit } from '@angular/core';
import { FaqService, Faq } from '../../services/faq.service';
import { MatExpansionModule } from '@angular/material/expansion';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.scss'],
  imports: [FormsModule, CommonModule, MatExpansionModule, HttpClientModule]
})
export class FaqComponent implements OnInit {
  faqs: Faq[] = [];
  role: string = '';
  userId: number | null = null;

  newQuestion: string = '';
  newAnswer: string = '';

  constructor(private faqService: FaqService, private router: Router) {}

  ngOnInit(): void {
    const jwtToken = localStorage.getItem('token');
    if (jwtToken) {
      try {
        const decoded: any = jwtDecode(jwtToken);
        this.role = decoded?.role || '';
        this.userId = decoded?.user_id || null;
      } catch (error) {
        console.error('Ошибка декодирования JWT:', error);
      }
    }

    this.faqService.getFaqs().subscribe((data) => {
      this.faqs = data;
    });
  }

  addFaq(): void {
    const trimmedQuestion = this.newQuestion.trim();
    const trimmedAnswer = this.newAnswer.trim();

    if (!trimmedQuestion || !trimmedAnswer || this.userId === null) return;

    const newFaq: Omit<Faq, 'id'> = {
      question: trimmedQuestion,
      answer: trimmedAnswer,
      userId: this.userId
    };

    this.faqService.addFaq(newFaq).subscribe((createdFaq) => {
      this.faqs.push(createdFaq);
      this.newQuestion = '';
      this.newAnswer = '';
    });
  }

  onBack(): void {
    this.router.navigate(['']);
  }
}
