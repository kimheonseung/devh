import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'css/theme-common.css';
import 'css/theme-dark.css';
import Home from "Home";
import dotenv from 'dotenv';
import LoginPage from "pages/login/LoginPage";
import PrivateRoute from "PrivateRoute";

dotenv.config();

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path='/login' component={LoginPage}/>
        <PrivateRoute exact path='/' component={Home}/>
      </Switch>
    </Router>
  );
}

export default App;
