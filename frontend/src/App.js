import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import Navbar from "./components/Navbar";
import QuestionList from './components/QuestionList';
import Result from "./components/Result";
import Home from './components/Home';
import OldResult from './components/OldResult'
import PrivateRoute from "./components/PrivateRoute";

import { Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div className="App">
          <Navbar/>
            <Routes>   
              <Route path="/" element={<Home/>}/>  
              <Route exact path="/questions" element={<PrivateRoute> <QuestionList/> </PrivateRoute>} />
              <Route path="/result" element={<PrivateRoute> <Result/> </PrivateRoute>}/>
              <Route path="/oldResult" element={<PrivateRoute> <OldResult/> </PrivateRoute>}/>
            </Routes>
    </div>
  );
}

export default App;
