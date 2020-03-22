import React from "react";
import SearchIcon from "./../../../resources/search.svg";

const CustomSearchBar = props => {
  let input = "";

  const handleSearch = () => {
    props.onSearch(input.value);
    props.setSearchValue(input.value);
  };

  return (
    <div className="row p-3 m-0">
      <div className="input-group md-form form-sm form-1 pl-0">
        <div className="input-group-prepend">
          <span
            className="input-group-text lighten-3"
            id="basic-text1"
            style={{ backgroundColor: "#343a40", color: "#fff" }}
          >
            <img
              src={SearchIcon}
              alt="..."
              className="searchIconResize invert"
            />
          </span>
          <input
            id={props.id}
            type="text"
            placeholder="Search"
            aria-label="Search"
            className="form-control"
            style={{ backgroundColor: "#343a40", color: "#fff" }}
            ref={n => (input = n)}
            onChange={handleSearch}
          />
        </div>
      </div>
    </div>
  );
};

export default CustomSearchBar;
