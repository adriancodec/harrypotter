import React from 'react';
import HeaderComponent from "./HeaderComponent";
import ContentComponent from "./ContentComponent";
import FooterComponent from "./FooterComponent";

function MainComponent() {
    return (
        <div>
            <HeaderComponent />
            <ContentComponent />
            <FooterComponent />
        </div>
    );
}

export default MainComponent;