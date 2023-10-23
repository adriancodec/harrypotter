import React from 'react';
import PotionDetailsComponent from "./PotionDetailsComponent";
import IngredientAdderComponent from "./IngredientAdderComponent";
import ButtonsHolderComponent from "./ButtonsHolderComponent";

function BrewerComponent() {
    return (
        <>
            <PotionDetailsComponent></PotionDetailsComponent>
            <IngredientAdderComponent></IngredientAdderComponent>
            <ButtonsHolderComponent></ButtonsHolderComponent>
        </>
    );
}

export default BrewerComponent;