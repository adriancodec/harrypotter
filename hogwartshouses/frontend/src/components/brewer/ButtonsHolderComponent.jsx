import React from 'react';
import SaveButton from "../buttons/SaveButton";
import HelpButton from "../buttons/HelpButton";

function ButtonsHolderComponent() {
    return (
        <>
            <SaveButton>Add ingredient</SaveButton>
            <HelpButton>Show similar recipes</HelpButton>
        </>
    );
}

export default ButtonsHolderComponent;