import React from 'react';
import IngredientComponent from "./IngredientComponent";

function OneRecipeComponent({itemData}) {
    return (
        <>
            <h1>{itemData.name}</h1>
            <h2>{itemData.student.id}</h2>
            <label>Ingredients</label>
            {itemData.ingredients.map((item, index) => (
                <IngredientComponent key={index} ingredient={item} ></IngredientComponent>
            ))}
        </>
    );
}

export default OneRecipeComponent;