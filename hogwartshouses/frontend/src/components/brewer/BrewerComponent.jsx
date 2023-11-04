import React, { useState } from 'react';
import PotionDetailsComponent from "./PotionDetailsComponent";
import IngredientAdderComponent from "./IngredientAdderComponent";
import ButtonsHolderComponent from "./ButtonsHolderComponent";
import RecipesComponent from "./RecipesComponent";

function BrewerComponent() {

    const [recipesData, setRecipesData] = useState([]);

    console.log("rendered BrewerComponent");

    const fetchRecipesData = () => {
        fetchData()  // Call the function to fetch data
            .then((data) => {
                console.log("data returned from fetch");
                console.log(data);
                setRecipesData(data);  // Update the state with the fetched data
            });
    };

    return (
        <>
            <PotionDetailsComponent></PotionDetailsComponent>
            <IngredientAdderComponent></IngredientAdderComponent>
            <ButtonsHolderComponent onHelpClick={fetchRecipesData}></ButtonsHolderComponent>
            <RecipesComponent data={recipesData}></RecipesComponent>
        </>
    );
}

function fetchData() {
    return fetch('http://localhost:8080/potions/2/help')
        .then((response) => response.json())
        .catch((error) => {
            console.error('Error fetching data:', error);
        });
}

export default BrewerComponent;