import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css'
import {BrowserRouter as Router} from 'react-router-dom';
import { AuthProvider } from './components/AuthContext'; 


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Router>
      <AuthProvider>  
        <App/>
      </AuthProvider>
    </Router>
);


