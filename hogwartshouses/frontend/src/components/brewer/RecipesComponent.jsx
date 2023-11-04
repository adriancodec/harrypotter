import React from 'react';
import OneRecipeComponent from "./OneRecipeComponent";

function RecipesComponent({ data }) {

    console.log("rendered recipesComponent");
    console.log(data);


    return (
        <>
            <h2>Recipes</h2>
            {data.map((item, index) => (
                <OneRecipeComponent key={index} itemData={item} ></OneRecipeComponent>
            ))}
        </>
    );
}

export default RecipesComponent;