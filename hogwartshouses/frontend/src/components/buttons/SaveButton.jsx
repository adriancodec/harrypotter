import React from 'react';

function SaveButton({onClickHandler}) {
    return (
        <>
            <button onClick={onClickHandler}>Save</button>
        </>
    );
}

export default SaveButton;