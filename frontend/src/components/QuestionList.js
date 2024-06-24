import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import '../css/QuestionList.css';
import { SERVER_URL } from './constant';

function QuestionList() {
  const navigate = useNavigate();
  const location = useLocation();
  const user = location.state?.user;
  
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [categoryMap, setCategoryMap] = useState({});
  const [currentPage, setCurrentPage] = useState(0);
  
  useEffect(() => {
    loadQuestions();
    setTimeout(() => {
      window.scrollTo(0, 0);
      }, 0);
    }, []);

    
  const loadQuestions = async () => {
    try {
        const response = await axios.get(SERVER_URL + "/questions", {
          withCredentials: true
        });
        if (response.request.responseURL !== SERVER_URL + "/questions") {
          document.location = response.request.responseURL;
      }
      const initialAnswers = {};
      response.data.forEach(question => {
        initialAnswers[question.id] = null;
      });
      setQuestions(response.data);
      setAnswers(initialAnswers);
    } catch (error) {
      console.error("Error loading questions:", error);
    }
  };


  const isAllAnsweredOnPage = () => {
    const startIndex = currentPage * 5;
    const endIndex = Math.min(startIndex + 5, questions.length);
    for (let i = startIndex; i < endIndex; i++) {
      if (answers[questions[i].id] === null) {
        return false;
      }
    }
    return true;
  };

  const handleGetResults = async () => {
    const resultDTO = {
      user: { gender: user.gender, birthYear: user.birthYear, email: user.email },
      answers: { ...answers }
    };
    try {
      await axios.post(SERVER_URL + "/result", resultDTO, {
        withCredentials: true
        })
        .then(response => {
          setCategoryMap(response.data);
          const data = { message: response.data, email: user.email };
          navigate('/result', { state: data });
        });
    } catch (error) {
      console.error("Error getting results:", error);
    }
  };

  const handleNextPage = () => {
    setCurrentPage(prevPage => prevPage + 1);
    setTimeout(() => {
      window.scrollTo(0, 0);
    }, 0);
    };

  const startIndex = currentPage * 5;
  const endIndex = Math.min(startIndex + 5, questions.length);
  const currentQuestions = questions.slice(startIndex, endIndex);
  const isLastPage = endIndex === questions.length;

  const handleRadioChange = (questionId, answer) => {
    setAnswers(prevAnswers => ({
      ...prevAnswers,
      [questionId]: answer
    }));

  };

  return ( 
    <div className="question-list">
      <ul>
        {currentQuestions.map((question, index) => (
          <li key={question.id} className={`question-container ${answers[questions[index + startIndex -1]?.id] === null ? 'disabled' : ''}`}>
            <p className="question-text">{question.id}. {question.text}</p>
            <div className="answer-options">
              <label className="answer-label">
                <input
                  type="radio"
                  name={`answer_${question.id}`}
                  className="radioButton"
                  onChange={() => handleRadioChange(question.id, "YES")}
                  disabled={answers[questions[index - 1]?.id] === null}
                />
                Igen
              </label>
              <label className="answer-label">
                <input
                  type="radio"
                  name={`answer_${question.id}`}
                  className="radioButton"
                  onChange={() => handleRadioChange(question.id, "NO")}
                  disabled={answers[questions[index - 1]?.id] === null}
                />
                Nem
              </label>
            </div>
          </li>
        ))}
      </ul>
      {!isLastPage && (
        <button className="next-button" disabled={!isAllAnsweredOnPage()} onClick={handleNextPage}>
          Következő
        </button>
      )}
      {isLastPage && (
        <button className="submit-button" disabled={!isAllAnsweredOnPage()} onClick={handleGetResults}>
          Eredmény
        </button>
      )}
    </div>
  );
}

export default QuestionList;
