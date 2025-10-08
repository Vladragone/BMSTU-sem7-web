import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getFaqs } from "../../services/faqService";
import type { Faq } from "../../services/faqService";
import "./FaqPage.css";

const FaqPage: React.FC = () => {
  const [faqs, setFaqs] = useState<Faq[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    getFaqs()
      .then(setFaqs)
      .catch((err) => console.error("Ошибка загрузки FAQ:", err));
  }, []);

  return (
    <div className="faq-wrapper">
      <h2 className="faq-title">Часто задаваемые вопросы</h2>

      <div className="faq-list">
        {faqs.length > 0 ? (
          faqs.map((faq) => (
            <details key={faq.id} className="faq-item">
              <summary className="faq-question">{faq.question}</summary>
              <p className="faq-answer">{faq.answer}</p>
            </details>
          ))
        ) : (
          <p className="faq-empty">Нет доступных вопросов</p>
        )}
      </div>

      <button className="back-btn" onClick={() => navigate("/")}>
        Назад
      </button>
    </div>
  );
};

export default FaqPage;
