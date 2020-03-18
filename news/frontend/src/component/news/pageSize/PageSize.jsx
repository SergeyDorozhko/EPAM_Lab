import React from "react";
import Select from "react-select";

const PageSize = (props) => {
    const selectedValue = props.perPage;
    const options = [
        { value: 5, label: 5 },
        { value: 10, label: 10 },
        { value: 15, label: 15 }
    ];
    return (
        <Select
            value={selectedValue}
            onChange={selectedValue => props.onChange(selectedValue)}
            options={options}
        />
    )
}

export default PageSize;