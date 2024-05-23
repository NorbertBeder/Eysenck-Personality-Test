import React, { useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";
import { GoogleLogin } from '@react-oauth/google';
import  AuthContext from "../components/AuthContext"

import axios from 'axios';

import "../css/Home.css"

function Home() {
  const navigate = useNavigate();

  const { login, handleLogin, handleLogout } = useContext(AuthContext); 
  const [birthYear, setBirthYear] = useState('');
  const [gender, setGender] = useState('');
  const [email, setEmail] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [hasOldResults, setHasOldResults] = useState('');


  useEffect(() => {
    if (login) {
      setEmail(login.email);
      checkOldResults(login.email);
    }
  }, [login]);

  function handleCallbackResponse(response) {
    const userObject = jwtDecode(response.credential);
    handleLogin(userObject)
    setEmail(userObject.email);
    checkOldResults(userObject.email);
  }

  const checkOldResults = async (email) => {
    try {
      const response = await axios.get(`https://eysenck-personality-test.com/api/v1/checkUser?email=${email}`);
      setHasOldResults(response.data);
    } catch (error) {
      console.error("Error checking old results:", error);
    }
  };

  const handleStartTest = () => {
    if (birthYear === '') {
      setErrorMessage('Kérjük, adja meg születési évét.');
      return;
    }
    if (gender === '') {
      setErrorMessage('Kérjük, adja meg nemét.');
      return;
    }
    if (email === '') {
      setErrorMessage('Kérjük, jelentkezzen be a Google-fiókjával.');
      return;
    }

    navigate('/questions', {
      state: {
        user: { name: login.name, picture: login.picture, gender, birthYear, email }
      }
    });
  };

   const handleNavigateToResults = () =>{
    navigate('/oldResult', {
      state: {email: email}
    })
   }

   return (
    <div className="home-container">
      <h1>Eysenck féle személyiség tipológia teszt</h1>
      <p>
        Néhány kijelentés következik, amelyek a magatartására,
        érzelmeire, viselkedésére vonatkoznak. Ha a kijelentés illik Önre (jellemzi Önt), akkor adjon
        Igen, ha nem, akkor Nem jelzést.
        Mivel az első közvetlen benyomás alapján célszerű
        döntenie, ezért dolgozzon gyorsan, ne fontolgassa sokáig válaszát! Az egész kérdőív kitöltése ne
        tartson sokáig. Nem intelligencia vagy képesség tesztről van szó, tehát nincsen helyes vagy
        helytelen válasz, illetve minden teszt csak pillanatnyi érzelmi állapotot mér.
      </p>
      <div className='divFlex'>
        {!login && (
          <label className='firstLabel'>
            Bejelentkezés:
            <div className='signIn'>
              <GoogleLogin onSuccess={handleCallbackResponse} onError={() => { console.log("Login failed") }} />
            </div>
          </label>
        )}
        {login && (
          <div className="loginInfo">
            <img className="loginPic" src={login.picture} alt="Profile" />
            <p className="loginName">{login.name}</p>
            {hasOldResults && (
              <button className="oldResults" onClick={handleNavigateToResults}>Régebbi eredmények</button>
            )}
          </div>
        )}
        <p id='chooseP'>Kérem válassza ki:</p>
        <select className='dateSelect' value={birthYear} onChange={(e) => setBirthYear(parseInt(e.target.value))}>
          <option value="" hidden>Születési év</option>
          {[...Array(90)].map((_, index) => (
            <option key={2012 - index} value={2012 - index}>{2012 - index}</option>
          ))}
        </select>
        <select className='genderSelect' value={gender} onChange={(e) => setGender(e.target.value)}>
          <option value="" hidden>Nem</option>
          <option value="MALE">Férfi</option>
          <option value="FEMALE">Nő</option>
          <option value="OTHER">Más</option>
        </select>
        <div>
          <p className="error-message">{errorMessage}</p>
        </div>
        <button onClick={handleStartTest}>Személyiség teszt kezdése</button>
        {login && (
          <button className="logoutButton" onClick={handleLogout}>Kijelentkezés</button>
        )}
      </div>
    </div>
  );
}

export default Home;