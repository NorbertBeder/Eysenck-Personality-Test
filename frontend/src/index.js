import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css'
import {BrowserRouter as Router} from 'react-router-dom';
import {GoogleOAuthProvider} from "@react-oauth/google"
import { AuthProvider } from './components/AuthContext'; 


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <GoogleOAuthProvider clientId="329704426909-u2ist0pcssn36qnmqhd2hmk704gpn79o.apps.googleusercontent.com">
    <Router>
      <AuthProvider> {/* Wrap with AuthProvider */}
        <App/>
      </AuthProvider>
    </Router>
  </GoogleOAuthProvider>,
);


