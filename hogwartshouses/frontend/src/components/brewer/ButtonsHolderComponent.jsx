import React from 'react';
import SaveButton from "../buttons/SaveButton";
import HelpButton from "../buttons/HelpButton";

function ButtonsHolderComponent({ onHelpClick }) {

    console.log("rendered buttons holder");
    console.log(onHelpClick);

    return (
        <>
            <SaveButton>Add ingredient</SaveButton>
            <HelpButton onHelpClick={onHelpClick}>Show similar recipes</HelpButton>
        </>
    );
}

export default ButtonsHolderComponent;