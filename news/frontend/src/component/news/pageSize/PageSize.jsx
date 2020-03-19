import React from "react";
import Select from "react-select";


const customStyles = {
    option: (provided, state) => ({
      ...provided,
      borderBottom: '1px dotted pink',
      color: state.isSelected ? 'red' : 'blue',
      fontSize: 12,
      padding: 4,
    }),
    // control: () => ({
    //   // none of react-select's styles are passed to <Control />
    //   width: 200,
    // }),
    singleValue: (provided, state) => {
      const opacity = state.isDisabled ? 0.5 : 1;
      const transition = 'opacity 300ms';
  
      return { ...provided, opacity, transition };
    }
  }


const PageSize = (props) => {
    const selectedValue = props.perPage;
    const options = [
        { value: 5, label: 5 },
        { value: 10, label: 10 },
        { value: 15, label: 15 }
    ];
    return (
        <Select
            styles={customStyles}
            value={selectedValue}
            onChange={selectedValue => props.onChange(selectedValue)}
            options={options}
        />
    )
}

export default PageSize;