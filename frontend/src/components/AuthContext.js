import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';
import { SERVER_URL } from "./constant";


export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [login, setLogin] = useState(null);

  useEffect(() => {
    const savedLogin = JSON.parse(localStorage.getItem('login'));
    if (savedLogin) {
      setLogin(savedLogin);
    }
  }, []);

  useEffect(() => {
    if (login) {
      localStorage.setItem('login', JSON.stringify(login));
    } else {
      localStorage.removeItem('login');
    }
  }, [login]);


  const handleLogin = (userObject) => {
    setLogin({
      email: userObject.email,
      picture: userObject.profilePictureUrl,
      name: userObject.name
    });
  };

  const handleLogout = async () => {
    setLogin(null);

    try {
      await axios.get(SERVER_URL + '/logout', { 
        withCredentials: true 
      });

      localStorage.removeItem('login');
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  return (
    <AuthContext.Provider value={{ login, handleLogin, handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
