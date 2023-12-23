import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'; // Use BrowserRouter
import Layout from './Layout';
import Home from './Home';
import UserRegistration from './UserRegistration';
import UserLogIn from './UserLogIn';

const App = () => {
    return (
        <UserLogIn/>
    );
};

export default App;
