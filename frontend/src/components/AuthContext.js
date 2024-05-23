import React, { createContext, useState, useEffect } from 'react';

const AuthContext = createContext();

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
      picture: userObject.picture,
      name: userObject.name
    });
  };

  const handleLogout = () => {
    setLogin(null);
  };

  return (
    <AuthContext.Provider value={{ login, handleLogin, handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
