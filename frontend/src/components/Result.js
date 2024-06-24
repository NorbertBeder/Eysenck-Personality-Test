import React, { useState, useEffect} from 'react';
import { useLocation } from 'react-router-dom';
import '../css/Result.css';
import axios from 'axios';
import { SERVER_URL } from './constant';

const Result = () => {
  const location = useLocation();
  const newAnswers = location.state?.message;
  const email = location.state?.email;

  const [selectedDate, setSelectedDate] = useState('');
  const [answers, setAnswers] = useState([]);
  const [dates, setDates] = useState([]);
  const [selectedAnswers, setSelectedAnswers] = useState(Object.values(newAnswers));

  useEffect(() => {
    fetchResults();
    setTimeout(() => {
      window.scrollTo(0, 0);
    }, 0);
  }, []);

  const fetchResults = async () => {
    const requestBody = {
      email: email
    };
    try {
        const response = await axios.post(SERVER_URL + '/dates', requestBody, {
          withCredentials: true
        });
      const data = response.data;
      const dateKeys = Object.keys(data).sort().reverse();

      setDates(dateKeys);
      setAnswers(data)
    } catch (error) {
      console.error("Error fetching response dates:", error);
    }
  };

  const handleDateChange = (e) => {
    const selectedDate = e.target.value;
    setSelectedDate(selectedDate);

    if (selectedDate === '') {
      setSelectedAnswers(Object.values(newAnswers));
    }else{
      const selectedAnswers = answers[selectedDate];
      setSelectedAnswers(selectedAnswers);
    }
  };

  const traits = [
    { name: "Extrovertizmus", max: 32 },
    { name: "Őszinteség", max: 16 },
    { name: "Neuroticizmus", max: 38 },
    { name: "Rigiditás", max: 30 }
  ];

  return (
    <div className="result">
      <h1>Eredmény</h1>
      <select className="date-select" value={selectedDate} onChange={handleDateChange}>
        <option value="">Mostani kitöltés</option>
        {dates.map((date, index) => (
          <option key={index} value={date}>{date}</option>
        ))}
      </select>
      {selectedAnswers.length > 0 && (
      <div>
        {traits.map((trait, index) => (
          <div key={index} className="result-trait">
            <p className='trait'>{trait.name}</p>
            <div className="progress">
              <div
                className="progress-bar"
                role="progressbar"
                style={{ width: `${(selectedAnswers[index] / trait.max) * 100}%` }}
              ></div>
            </div>
            <p className='score'>{selectedAnswers[index]} / {trait.max}</p>
          </div>
        ))}
      </div>
    )}
      {!selectedAnswers.length && selectedDate && (
        <div>
          <h3>No data available for the selected date.</h3>
        </div>
      )}
      <div>
        <h3> Extroverzió- Intraverzió</h3>
        <h5> Introvertált (0) Közepes (16) Extrovertált(32)</h5> 
        <p> Minél magasabb a kapott pontszám, annál extrovertáltabb a vizsgált személy.  </p>  

        <h3> Őszinteség</h3>
        <h5> Őszinte (0) Közepes (8) Konform (16)</h5> 
        <p> Vizsgálja a vizsgált személy őszinteségét a felvevőhöz, mennyire adott konzekvens válaszokat az
           adott személy. Minél magasabb a kapott pontszám annál inkább akart a vizsgált személy 
           megfelelni a helyzetnek és a teszt illetve saját elvárásainak, annál kevésbé volt őszinte. </p>  

        <h3> Neurocitás</h3>
        <h5> Érzelmileg stabil (0) Közepes (19) érzelmileg labilis (38)</h5> 
        <p> Minél magasabb a pontszám annál labilisabb érzelmileg a vizsgált személy.</p> 

        <h3> Rigiditás</h3>
        <h5> Rugalmas (0) Közepes (15) Merev (30)</h5> 
        <p> A vizsgált személy rugalmasságát, konformoknak, elvárásoknak való megfelelését vizsgálja,
           minél magasabb a pontszám annál kevésbé rugalmas az adott személyiség. </p>  
      </div>
    </div>
    
  );
};

export default Result;