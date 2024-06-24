import React, { useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";
import { GoogleLogin } from '@react-oauth/google';
import  AuthContext from "../components/AuthContext"
import { SERVER_URL } from "./constant";
import { GOOGLE_URL } from "./constant";

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
  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    setErrorMessage("")

    if (login) {
      setEmail(login.email);
      checkOldResults(login.email);
    }
  }, [login]);

  useEffect(() => {
    if (hasOldResults) {
      setBirthYear(hasOldResults.birthYear);
      setGender(hasOldResults.gender);
      setEmail(hasOldResults.email);
    }
  }, [hasOldResults]);


  useEffect(() => {
    const fetchUserInfo = async () => {
        try {
            const response = await axios.get(SERVER_URL + '/userInfo', {
                withCredentials: true
            });
            console.log(response.data)
            setUserInfo(response.data);
        } catch (error) {
            console.error('Error fetching user info:', error);
        }
    };

    if (userInfo === null || userInfo == "") {
        fetchUserInfo();
    }else{
      handleLogin(userInfo)
    }
  }, [userInfo]);


  function handleCallbackResponse(response) {
    document.location = GOOGLE_URL;
  }

  const checkOldResults = async (email) => {
    const requestBody = {
      email: email
    };
    try {
      const response = await axios.post(SERVER_URL + '/checkUser', requestBody, {
        withCredentials: true
      });
      if (response.request.responseURL !== SERVER_URL + '/checkUser') {
          document.location = response.request.responseURL;
      }
      setHasOldResults(response.data);
    } catch (error) {
      console.error("Error checking old results:", error);
    }
  };

  const handleStartTest = () => {
    if (login === null){
      setErrorMessage('Kérem jelentkezzen be.');
      return;
    }

    if (birthYear === '') {
      setErrorMessage('Kérem adja meg születési évét.');
      return;
    }
    if (gender === '') {
      setErrorMessage('Kérem adja meg nemét.');
      return;
    } 
    if (email === '') {
      setErrorMessage('Kérem jelentkezzen be a Google-fiókjával.');
      return;
    }
    navigate('/questions', {
      state: {
        user: { name: login.name, picture: login.picture, gender, birthYear, email }
      }
    });
  };

   const handleNavigateToResults = async () =>{
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
              <button className="oldResults" onClick={handleCallbackResponse}>Login with Google</button>
            </div>

          </label>
          
        )}
        {login && (
          <div className="loginInfo">
            <img className="loginPic" src={login.picture} alt="Profile" />
            <p className="loginName">{login.name}</p>
            {hasOldResults ? (
              <button className="oldResults" onClick={handleNavigateToResults}>Régebbi eredmények</button>
            ) : (
              <>
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
              </>
            )}
          </div>
        )}
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