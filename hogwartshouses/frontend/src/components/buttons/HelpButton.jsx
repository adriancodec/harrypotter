import React from 'react';

function HelpButton({onHelpClick}) {

    console.log("rendered help button");
    console.log(onHelpClick);

    return (
        <>
            <button onClick={onHelpClick}>Help</button>
        </>
    );
}

export default HelpButton;