import React, {useContext} from 'react';
import { Link } from 'react-router-dom';
import AuthContext from '../components/AuthContext';

export default function Navbar() {
  const { login } = useContext(AuthContext);

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg-primary" style={{ height: '100px' }}>
        <Link to="/" className="navbar-brand" style={{ marginLeft: '8px', fontWeight: 'bolder'}}>Eysenck Personality Test</Link>
        <div className="ms-auto d-flex align-items-center">
          {login && (
            <h3 className="text-light me-3" style={{fontWeight: 'bold'}}>{login.name}</h3>
          )}
          <Link to="/" className="btn btn-light" style={{marginRight: '5px'}}>Kezdő oldal</Link>
        </div>
      </nav>
    </div>
  );
}