import React, { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { AuthContext } from "../components/AuthContext";

const PrivateRoute = ({ children }) => {
  const { login } = useContext(AuthContext);

  if (!login) {
    return <Navigate to="/" replace />;
  }

  return children;
};

export default PrivateRoute;
