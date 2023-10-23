import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import MainComponent from "./MainComponent";
import BrewerComponent from "../brewer/BrewerComponent";

function RouterComponent() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainComponent />} />
                <Route path="/brewer" element={<BrewerComponent />} />
            </Routes>
        </Router>
    );
}

export default RouterComponent;