import React from "react";
import SearchIcon from "./../../../resources/search.svg";

const CustomSearchBar = props => {
  let input = "";

  const handleSearch = () => {
    props.onSearch(input.value);
  };

  return (
    <div className="row p-3">
      <div className="input-group md-form form-sm form-1 pl-0">
        <div className="input-group-prepend">
          <span className="input-group-text purple lighten-3" id="basic-text1">
            <i className="text-white" aria-hidden="true" href={SearchIcon}></i>
            <img src={SearchIcon} alt="..." className="searchIconResize" />
          </span>
          <input
            id={props.id}
            type="text"
            placeholder="Search"
            aria-label="Search"
            className="form-control"
            style={{ backgroundColor: "#fff" }}
            ref={n => (input = n)}
            onChange={handleSearch}
          />
        </div>
      </div>
    </div>
  );
};

export default CustomSearchBar;
