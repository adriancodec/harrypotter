import React from 'react';

function IngredientComponent({ingredient}) {
    return (
        <>
            <label className={"ingredients"}>{ingredient.name}</label>
        </>
    );
}

export default IngredientComponent;